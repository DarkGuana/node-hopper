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
 * Created by Dark Guana on 2014-03-25.
 */
public class DistanceGraph implements IntegerAggregation
{
  private static Logger logger = Logger.getLogger("DistanceGraph");
  private static final Integer NON_TERMINATING = -1;

  private final Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
  private Map<NodePair, Integer> distances = new HashMap<NodePair, Integer>();
  private boolean active = false;

  private Integer width = 0;
  private Integer length = 0;
  private Integer depth = 100000;
  private Rule rule;

  private final HashSet<IntegerAggregateListener> listeners = new HashSet<IntegerAggregateListener>();

  public DistanceGraph(int width, int length, Rule rule)
  {
    setRule(rule);
    setSize(width, length);
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
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < length; y++)
      {
        populateDistanceBetween(getNode(y), getNode(x));
      }
      logger.fine("Paths ending at " + x + " done");
    }
    setActive(false);
  }

  private Integer populateDistanceBetween(Node start, Node finish)
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

      Integer distanceCheck = getDistance(currentNode, finish);
      if (!current.equals(target) && (history.contains(next) || (distanceCheck != null && distanceCheck.equals(NON_TERMINATING))))
      {
        setDistance(start, finish, NON_TERMINATING);
        logger.finest("Loop detected / depth exceeded");
        break;
      } else if (distanceCheck != null)
      {
        setDistance(start, finish, i + distanceCheck);
        logger.finest("Previous target found (" + current + " -> " + target + ", " + distanceCheck + ") + " + i);
        break;
      } else
      {
        setDistance(start, currentNode, i);
        logger.finest("Setting (" + start.getId() + " -> " + currentNode.getId() + ") to " + i);
      }

      logger.finest(current + " -> " + next);
      history.add(current);
      current = next;
    }
    logger.finest("done");
    return getDistance(start, finish);
  }

  private Integer getDistance(Node start, Node finish)
  {
    return distances.get(NodePair.get(start, finish));
  }

  public Integer getDistanceBetween(Integer start, Integer finish)
  {
    if (start > length || finish > width)
    {
      logger.warning("Invalid request, out of range (from: " + start + " to: " + finish);
      throw new IndexOutOfBoundsException("Unable to find value for " + start + " -> " + finish);
    }
    return getDistance(getNode(start), getNode(finish));
  }

  private Integer setDistance(Node start, Node finish, Integer distance)
  {
    for (IntegerAggregateListener listener : listeners)
      listener.aggregateChanged(start.getId(), finish.getId(), distance, this);
    return distances.put(NodePair.get(start, finish), distance);
  }

  private void setSize(int width, int length)
  {
    this.width = width;
    this.length = length;
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
    return width;
  }

  @Override
  public Integer getMaxStartNode()
  {
    return length;
  }

  @Override
  public Integer getAggregate(Integer x, Integer y)
  {
    return getDistanceBetween(y, x);
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
