package node.hopper.graph.viewer;

/**
 * In applications that are actively examining a particular value pair in an aggregate, this interface can be used
 * (and is encouraged for) anything that needs to keep track of what point is currently being observed.
 */
public interface AggregatePositionListener
{
  public void setPosition(Integer startNode, Integer finalNode);
}
