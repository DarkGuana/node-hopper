package node.hopper.graph;

/**
 * Created by Dark Guana on 2014-03-29.
 */
public interface IntegerAggregation
{
  public Integer getMaxTargetNode();

  public Integer getMaxStartNode();

  public Integer getAggregate(Integer start, Integer target);

  public boolean isActive();

  public void addListener(IntegerAggregateListener listener);

  public void removeListener(IntegerAggregateListener listener);
}
