package node.hopper.graph;

import node.hopper.rules.Rule;

import java.util.List;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-03-29.
 */
public interface IntegerAggregation
{
  public Integer getMaxTargetNode();

  public Integer getMaxStartNode();

  public IntegerAggregate getAggregate(Integer start, Integer target);

  public List<Integer> getHops(Integer start, Integer target);

  public boolean isActive();

  public void addListener(IntegerAggregationListener listener);

  public void removeListener(IntegerAggregationListener listener);

  public Rule getRule();
}
