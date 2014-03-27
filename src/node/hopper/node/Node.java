package node.hopper.node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dark Guana on 2014-03-22.
 */
public class Node
{
  private final Integer id;
  private final Set<Node> neighbors;

  public Node(Integer id)
  {
    this.id = id;
    neighbors = new HashSet<Node>();
  }

  public Integer getId()
  {
    return id;
  }

  public void resetNeighbors()
  {
    neighbors.clear();
  }

  public void addNeighbors(Set<Node> neighbors)
  {
    this.neighbors.addAll(neighbors);
  }

  public void addNeighbor(Node neighbor)
  {
    neighbors.add(neighbor);
  }

  public Set<Node> getNeighbors()
  {
    return Collections.unmodifiableSet(neighbors);
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Node node = (Node) o;

    if (id != null ? !id.equals(node.id) : node.id != null) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString()
  {
    return "Node{" +
         id +
        '}';
  }
}
