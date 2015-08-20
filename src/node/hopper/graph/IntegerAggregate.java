package node.hopper.graph;

/**
 * Simple class to represent the aggregate value between two nodes (usually distance).
 */
public class IntegerAggregate
{
  public static final IntegerAggregate NONTERMINATING = new IntegerAggregate(null);

  private final Integer value;

  public IntegerAggregate(Integer value)
  {
    this.value = value;
  }

  public Boolean isNonterminating()
  {
    return value == null;
  }

  public Integer getValue()
  {
    return value;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    IntegerAggregate that = (IntegerAggregate) o;

    if (value != null ? !value.equals(that.value) : that.value != null) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    return value != null ? value.hashCode() : 0;
  }
}
