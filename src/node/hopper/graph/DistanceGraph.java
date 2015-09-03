package node.hopper.graph;

import node.hopper.node.Node;
import node.hopper.node.NodePair;
import node.hopper.rules.Rule;

import java.util.*;
import java.util.logging.Logger;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-03-25.
 */
public class DistanceGraph implements IntegerAggregation
{
  private static Logger logger = Logger.getLogger("DistanceGraph");

  public static enum PopulationMethod
  {
    START_TO_FINISH, FINISH_TO_START, RANDOM, OUT_FROM_CENTER
  }

  private final Map<Integer, Node>              nodeMap   = new HashMap<Integer, Node>();
  private       Map<NodePair, IntegerAggregate> distances = new HashMap<NodePair, IntegerAggregate>();
  private       boolean                         active    = false;

  private Integer maxTarget = 0;
  private Integer maxStart  = 0;
  private Integer depth     = 100000;

  private PopulationMethod populationMethod = PopulationMethod.START_TO_FINISH;
  private Rule rule;

  private final HashSet<IntegerAggregationListener> listeners = new HashSet<IntegerAggregationListener>();

  public DistanceGraph(int maxTarget, int maxStart, PopulationMethod populationMethod, Rule rule)
  {
    setRule(rule);
    setSize(maxTarget, maxStart);
    setPopulationMethod(populationMethod);
  }

  public Node getNode(Integer id)
  {
    if (!nodeMap.containsKey(id))
      nodeMap.put(id, new Node(id));
    return nodeMap.get(id);
  }

  public void populateAllDistances()
  {
    setActive(true);

    logger.fine("Populating distances using " + populationMethod);
    switch(populationMethod)
    {
      default:
      case START_TO_FINISH:
        for (int y = 0; y < maxStart; y++)
        {
          for (int x = 0; x < maxTarget; x++)
          {
            populateDistanceBetween(getNode(y), getNode(x));
          }
          logger.fine("Paths ending at " + y + " done");
        }
        break;

      case FINISH_TO_START:
        for (int x = 0; x < maxTarget; x++)
        {
          for (int y = 0; y < maxStart; y++)
          {
            populateDistanceBetween(getNode(y), getNode(x));
          }
          logger.fine("Paths ending at " + x + " done");
        }
        break;

      case RANDOM:
        Random r = new Random(System.currentTimeMillis());
        List<NodePair> renderOrder = new ArrayList<NodePair>();
        for (int x = 0; x < maxTarget; x++)
        {
          for (int y = 0; y < maxStart; y++)
          {
            renderOrder.add(NodePair.get(getNode(y), getNode(x)));
          }
        }
        Collections.shuffle(renderOrder, r);
        while (!renderOrder.isEmpty())
        {
          NodePair pair = renderOrder.remove(renderOrder.size() - 1);
          populateDistanceBetween(pair.getStart(), pair.getFinish());
        }
        break;

      case OUT_FROM_CENTER:
        renderOrder = new ArrayList<NodePair>();
        Set<NodePair> rendered = new HashSet<NodePair>();
        for (int diag = 0; diag < Math.min(maxStart, maxTarget); diag++)
        {
          renderOrder.add(NodePair.get(getNode(diag), getNode(diag)));
        }
        for (int height = 0; height < maxStart; height++)
        {
          rendered.add(NodePair.get(getNode(height), getNode(-1)));
          rendered.add(NodePair.get(getNode(height), getNode(maxTarget + 1)));
        }
        for (int width = 0; width < maxTarget; width++)
        {
          rendered.add(NodePair.get(getNode(-1), getNode(width)));
          rendered.add(NodePair.get(getNode(maxStart+1), getNode(width)));
        }

        List<NodePair> subRenderOrder = new ArrayList<NodePair>();
        while (!renderOrder.isEmpty())
        {
          NodePair pair = renderOrder.remove(renderOrder.size() - 1);
          rendered.add(pair);
          populateDistanceBetween(pair.getStart(), pair.getFinish());
          NodePair north = NodePair.get(getNode(pair.getStart().getId()+1), pair.getFinish());
          NodePair south = NodePair.get(getNode(pair.getStart().getId()-1), pair.getFinish());
          NodePair west = NodePair.get(pair.getStart(), getNode(pair.getFinish().getId() + 1));
          NodePair east = NodePair.get(pair.getStart(), getNode(pair.getFinish().getId()-1));

          if(!rendered.contains(north))
            subRenderOrder.add(north);
          if(!rendered.contains(south))
            subRenderOrder.add(south);
          if(!rendered.contains(west))
            subRenderOrder.add(west);
          if(!rendered.contains(east))
            subRenderOrder.add(east);

          if(renderOrder.isEmpty())
          {
            renderOrder.addAll(subRenderOrder);
            subRenderOrder.clear();
          }
        }
        break;
    }

    setActive(false);
  }

  private IntegerAggregate populateDistanceBetween(Node start, Node finish)
  {
    if(distances.get(NodePair.get(start, finish)) != null)
      return distances.get(NodePair.get(start, finish));

    Integer current = start.getId();
    Integer target = finish.getId();

    logger.finer("Figuring " + current + " -> " + target + "...");

    Set<Integer> history = new HashSet<Integer>();
    for (int i = 0; i < depth && current != null; i++)
    {
      Integer next = rule.getNextValue(current, target);

      Node nextNode = getNode(next);
      Node currentNode = getNode(current);
      currentNode.addNeighbor(nextNode);

      IntegerAggregate distanceCheck = getDistance(nextNode, finish);
      if(current.equals(target))
      {
        setDistance(start, finish, new IntegerAggregate(i));
        break;
      } else if (history.contains(next) || (distanceCheck != null && distanceCheck.isNonterminating()))
      {
        setDistance(start, finish, IntegerAggregate.NONTERMINATING);
        logger.finest("Loop detected / depth exceeded");
        break;
      } else if (distanceCheck != null && !distanceCheck.isNonterminating())
      {
        setDistance(start, finish, new IntegerAggregate(i + distanceCheck.getValue() + 1));
        logger.finest("Previous target found (" + current + " -> " + target + ", " + distanceCheck + ") + " + i);
        break;
      } else if(distances.get(NodePair.get(start, currentNode)) == null)
      {
        setDistance(start, currentNode, new IntegerAggregate(i));
      }

      logger.finest(current + " -> " + next);
      history.add(current);
      current = next;
    }
    logger.finest("done");
    return getDistance(start, finish);
  }

  private IntegerAggregate getDistance(Node start, Node finish)
  {
    return distances.get(NodePair.get(start, finish));
  }

  public IntegerAggregate getDistanceBetween(Integer start, Integer finish)
  {
    if (start > maxStart || finish > maxTarget)
    {
      logger.warning("Invalid request, out of range (from: " + start + " to: " + finish);
      throw new IndexOutOfBoundsException("Unable to find value for " + start + " -> " + finish);
    }
    return getDistance(getNode(start), getNode(finish));
  }

  private IntegerAggregate setDistance(Node start, Node finish, IntegerAggregate distance)
  {
    IntegerAggregate oldValue = distances.get(NodePair.get(start, finish));
    boolean addDistance = oldValue == null || (oldValue.isNonterminating() && !distance.isNonterminating());

    if (addDistance)
      distances.put(NodePair.get(start, finish), distance);

    if (addDistance && start.getId() < getMaxStartNode() && finish.getId() < getMaxTargetNode())
    {
      for (IntegerAggregationListener listener : listeners)
        listener.aggregateChanged(start.getId(), finish.getId(), distance, this);
    }
    return oldValue;
  }

  private void setSize(int width, int length)
  {
    this.maxTarget = width;
    this.maxStart = length;
  }

  public void setRule(Rule rule)
  {
    this.rule = rule;
    resetDistances();
  }

  public void setPopulationMethod(PopulationMethod populationMethod)
  {
    this.populationMethod = populationMethod;
    resetDistances();
  }

  private void resetDistances()
  {
    distances.clear();
  }

  @Override
  public Integer getMaxTargetNode()
  {
    return maxTarget;
  }

  @Override
  public Integer getMaxStartNode()
  {
    return maxStart;
  }

  @Override
  public IntegerAggregate getAggregate(Integer start, Integer target)
  {
    return getDistanceBetween(start, target);
  }

  @Override
  public List<Integer> getHops(Integer start, Integer target)
  {
    NodePair pair = NodePair.get(getNode(start), getNode(target));
    if(!distances.containsKey(pair) || distances.get(pair).isNonterminating())
      return null;
    List<Integer> hops = new ArrayList<Integer>();
    Integer current = start;
    while (!current.equals(target))
    {
      hops.add(current);
      current = rule.getNextValue(current, target);
    }
    hops.add(target);
    return hops;
  }

  @Override
  public boolean isActive()
  {
    return active;
  }

  @Override
  public void addListener(IntegerAggregationListener listener)
  {
    listeners.add(listener);
  }

  @Override
  public void removeListener(IntegerAggregationListener listener)
  {
    listeners.remove(listener);
  }

  @Override
  public Rule getRule()
  {
    return rule;
  }

  public void setActive(boolean active)
  {
    this.active = active;
    for (IntegerAggregationListener listener : listeners)
      listener.activityChanged(active, this);
  }
}
