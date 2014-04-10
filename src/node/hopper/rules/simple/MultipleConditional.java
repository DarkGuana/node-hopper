package node.hopper.rules.simple;

import node.hopper.rules.Conditional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dark Guana on 2014-03-23.
 */
public class MultipleConditional implements Conditional
{
  private List<Conditional> conditions;

  public MultipleConditional(Conditional... conditions)
  {
    this.conditions = Arrays.asList(conditions);
  }

  @Override
  public boolean isApplicable(Integer current, Integer target)
  {
    for (Conditional condition : conditions)
    {
      if (!condition.isApplicable(current, target))
        return false;
    }
    return true;
  }

  @Override
  public String getDescription()
  {
    StringBuilder description = new StringBuilder();
    for (Conditional condition : conditions)
    {
      description.append(condition.getDescription()).append(" and ");
    }
    //trim last and
    description.setLength(description.length() - 4);
    return description.toString();
  }

  @Override
  public String toString()
  {
    return getDescription();
  }
}
