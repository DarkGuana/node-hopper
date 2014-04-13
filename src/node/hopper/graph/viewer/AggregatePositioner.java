package node.hopper.graph.viewer;

/**
 * When you need a class to be able to report to other classes what value pair is currently being examined, this
 * interface (and a bit more behind the scenes code to alert the given listeners) is your friend.
 *
 * Or at least not your enemy.  Probably.
 */
public interface AggregatePositioner
{
  public void addListener(AggregatePositionListener listener);

  public void removeListener(AggregatePositionListener listener);
}
