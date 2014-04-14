package node.hopper.graph;

import node.hopper.node.Node;
import node.hopper.node.NodePair;
import node.hopper.rules.Rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-03-25.
 */
public class DistanceGraph implements IntegerAggregation
{
  private static Logger logger = Logger.getLogger("DistanceGraph");

  private final Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
  private Map<NodePair, IntegerAggregate> distances = new HashMap<NodePair, IntegerAggregate>();
  private boolean active = false;

  private Integer maxTarget = 0;
  private Integer maxStart = 0;
  private Integer depth = 100000;
  private Rule rule;

  private final HashSet<IntegerAggregateListener> listeners = new HashSet<IntegerAggregateListener>();

  public DistanceGraph(int maxTarget, int maxStart, Rule rule)
  {
    setRule(rule);
    setSize(maxTarget, maxStart);
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
    for (int x = 0; x < maxTarget; x++)
    {
      for (int y = 0; y < maxStart; y++)
      {
        populateDistanceBetween(getNode(y), getNode(x));
      }
      logger.fine("Paths ending at " + x + " done");
    }
    setActive(false);
  }

  private IntegerAggregate populateDistanceBetween(Node start, Node finish)
  {
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

      IntegerAggregate distanceCheck = getDistance(currentNode, finish);
      if (!current.equals(target) && (history.contains(next) || (distanceCheck != null && distanceCheck.isNonterminating())))
      {
        setDistance(start, finish, IntegerAggregate.NONTERMINATING);
        logger.finest("Loop detected / depth exceeded");
        break;
      } else if (distanceCheck != null && !distanceCheck.isNonterminating())
      {
        setDistance(start, finish, new IntegerAggregate(i + distanceCheck.getValue()));
        logger.finest("Previous target found (" + current + " -> " + target + ", " + distanceCheck + ") + " + i);
        break;
      } else
      {
        setDistance(start, currentNode, new IntegerAggregate(i));
        logger.finest("Setting (" + start.getId() + " -> " + currentNode.getId() + ") to " + i);
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
    for (IntegerAggregateListener listener : listeners)
      listener.aggregateChanged(start.getId(), finish.getId(), distance, this);
    return distances.put(NodePair.get(start, finish), distance);
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
  public boolean isActive()
  {
    return active;
  }

  @Override
  public void addListener(IntegerAggregateListener listener)
  {
    listeners.add(listener);
  }

  @Override
  public void removeListener(IntegerAggregateListener listener)
  {
    listeners.remove(listener);
  }

  public void setActive(boolean active)
  {
    this.active = active;
    for (IntegerAggregateListener listener : listeners)
      listener.activityChanged(active, this);
  }
}
