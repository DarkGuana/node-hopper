package node.hopper.graph;

/**
 * Created by Dark Guana on 2014-03-29.
 */
public interface RectangularIntegerAggregation
{
  Integer getWidth();

  Integer getLength();

  Integer getValueAt(Integer start, Integer finish);
}
