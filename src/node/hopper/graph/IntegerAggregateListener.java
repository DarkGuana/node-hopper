package node.hopper.graph;

/**
 * Created by Dark Guana on 2014-04-08.
 */
public interface IntegerAggregateListener
{
  public void aggregateChanged(Integer start, Integer target, IntegerAggregate aggregateValue, IntegerAggregation source);

  public void activityChanged(Boolean active, IntegerAggregation source);
}
