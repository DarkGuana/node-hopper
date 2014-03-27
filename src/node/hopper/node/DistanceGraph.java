package node.hopper.node;

import node.hopper.rules.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dark Guana on 2014-03-25.
 */
public class DistanceGraph
{
  private static final Integer NON_TERMINATING = new Integer(-1);
  private final Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();

  private Map<NodePair, Integer> distances = new HashMap<NodePair, Integer>();

  private Integer width = 0;
  private Integer length = 0;
  private Integer depth = 1000;
  private Rule rule;

  public DistanceGraph(int width, int length, Rule rule)
  {
    setRule(rule);
    setSize(width, length);
  }

  public void populateAllDistances()
  {
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < length; y++)
      {
        populateDistanceBetween(getNode(y), getNode(x));
      }
    }
  }

  private Integer populateDistanceBetween(Node start, Node finish)
  {
    Integer current = start.getId();
    Integer target = finish.getId();

    System.out.println("Figuring "+current +" -> "+target+"...");

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
        System.out.println("Loop detected / depth exceeded");
        break;
      } else if (distanceCheck != null)
      {
        setDistance(start, finish, i + distanceCheck);
        System.out.println("Previous target found (" + current + " -> " + target + ", " + distanceCheck + ") + " + i);
        break;
      } else
      {
        setDistance(start, currentNode, i);
        System.out.println("Setting (" + start.getId() + " -> " + currentNode.getId() + ") to " + i);
      }

      System.out.println(current + " -> " + next);
      history.add(current);
      current = next;
    }
    System.out.println("done");
    return getDistance(start, finish);
  }

  private Integer getDistance(Node start, Node finish)
  {
    return distances.get(NodePair.get(start, finish));
  }

  public Integer getDistanceBetween(Integer start, Integer finish)
  {
    if (start > length || finish > width)
      throw new IndexOutOfBoundsException("Unable to find value for " + start + " -> " + finish);
    return getDistance(getNode(start), getNode(finish));
  }

  private Integer setDistance(Node start, Node finish, Integer distance)
  {
    return distances.put(NodePair.get(start, finish), distance);
  }

  private void setSize(int width, int length)
  {
    this.width = width;
    this.length = length;
  }

  public Node getNode(Integer id)
  {
    if (!nodeMap.containsKey(id))
      nodeMap.put(id, new Node(id));
    return nodeMap.get(id);
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

  public static void main(String[] args)
  {
    int width = 101;
    int length = 101;

    PrioritizedConditionalRuleList rule = new PrioritizedConditionalRuleList();
    rule.addNewConditional(new SimpleConditionalRule(RuleLibrary.getLessThan(), RuleLibrary.getMultiply(10)));
    rule.addNewConditional(new SimpleConditionalRule(new MultipleConditional(RuleLibrary.getDividableBy(2), RuleLibrary.getMoreThan()), RuleLibrary.getDivide(2)));
    rule.addNewConditional(new SimpleConditionalRule(RuleLibrary.getMoreThan(0), RuleLibrary.getSubtract(1)));

    DistanceGraph dg = new DistanceGraph(width, length, rule);
    long start = System.currentTimeMillis();
    dg.populateAllDistances();
    long end = System.currentTimeMillis();

    StringBuilder reporter = new StringBuilder();
    for (int x = 0; x < width; x++)
    {
      reporter.append('\t').append(x);
    }
    reporter.append('\n');
    for (int y = 0; y < length; y++)
    {
      reporter.append(y);
      for (int x = 0; x < width; x++)
      {
        String displayValue = dg.getDistanceBetween(y,x).toString(); // dg.getDistanceBetween(y,x) < 0 ? "#" : " ";
        reporter.append('\t').append(displayValue);
      }
      reporter.append('\n');
    }

    System.out.println(reporter.toString());
  }

}
