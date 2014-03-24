package node.hopper.node;

import node.hopper.rules.Rule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dark Guana on 2014-03-22.
 */
//TODO - convert Node to deal just with other Nodes, and add in a better return type than int.
public class Node
{
  private static final Map<Integer, Node> commonNodeMap = new HashMap<Integer, Node>();

  private final Integer id;
  private final Map<Integer, Integer> distances;

  public Node(Integer id)
  {
    this.id = id;
    distances = new HashMap<Integer, Integer>();
    distances.put(id, 0);
  }

  public Node getNode(Integer id)
  {
    if(!commonNodeMap.containsKey(id))
      commonNodeMap.put(id, new Node(id));
    return commonNodeMap.get(id);
  }

  public void resetDistances()
  {
    distances.clear();
    distances.put(id,0);
  }

  public Integer getDistanceToNode(Integer target, Rule rule)
  {
    Integer nextValue = rule.getNextValue(id, target);
    if(nextValue != null)
    {
      Node nextNode = getNode(nextValue);
      distances.put(target, 1 + nextNode.getDistanceToNode(target, rule));
    } else
      distances.put(target, -1);
    return distances.get(target);
  }
}
