package node.hopper.graph;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-04-08.
 */
public interface IntegerAggregationListener
{
  public void aggregateChanged(Integer start, Integer target, IntegerAggregate aggregateValue, IntegerAggregation source);

  public void activityChanged(Boolean active, IntegerAggregation source);
}
