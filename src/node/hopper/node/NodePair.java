package node.hopper.node;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-03-26.
 */
public class NodePair
{
  private Node start;
  private Node finish;

  private NodePair(Node start, Node finish)
  {
    this.start = start;
    this.finish = finish;
  }

  public Node getStart()
  {
    return start;
  }

  public Node getFinish()
  {
    return finish;
  }

  public static NodePair get(Node start, Node finish)
  {
    return new NodePair(start, finish);
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NodePair nodePair = (NodePair) o;

    if (finish != null ? !finish.equals(nodePair.finish) : nodePair.finish != null) return false;
    if (start != null ? !start.equals(nodePair.start) : nodePair.start != null) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = start != null ? start.hashCode() : 0;
    result = 31 * result + (finish != null ? finish.hashCode() : 0);
    return result;
  }

  @Override
  public String toString()
  {
    return "NodePair{" +
        "start=" + start +
        ", finish=" + finish +
        '}';
  }
}
