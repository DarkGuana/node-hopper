package node.hopper.scratchpad;

import node.hopper.rules.Conditional;
import node.hopper.rules.PrioritizedConditionalRuleList;
import node.hopper.rules.Rule;
import node.hopper.rules.SimpleConditionalRule;

/**
 * Created by Dark Guana on 2014-03-22.
 */
public class Algorithm
{
  public static void main(String[] args)
  {
    PrioritizedConditionalRuleList rule = new PrioritizedConditionalRuleList();
    rule.addNewConditional(new SimpleConditionalRule(getLessThan(), getMultiply(2)));
    rule.addNewConditional(new SimpleConditionalRule(getMoreThan(), getDivide(3)));

    Integer current = 3;
    Integer target = 5;
    for (int i = 0 ; i < 10 && current != target; i++)
    {
      Integer next = rule.getNextValue(current, target);
      System.out.println(current + " -> " + next);
      current = next;
    }
  }

  public static Conditional getLessThan()
  {
    return new Conditional()
    {
      @Override
      public boolean isApplicable(Integer current, Integer target)
      {
        return current < target;
      }

      @Override
      public String getDescription()
      {
        return "val < target";
      }
    };
  }

  public static Conditional getMoreThan()
  {
    return new Conditional()
    {
      @Override
      public boolean isApplicable(Integer current, Integer target)
      {
        return current > target;
      }

      @Override
      public String getDescription()
      {
        return "val > target";
      }
    };
  }

  public static Rule getMultiply(final int mod)
  {
    return new Rule()
    {
      @Override
      public Integer getNextValue(Integer current, Integer target)
      {
        return current * mod;
      }

      @Override
      public String getDescription()
      {
        return "val * " + mod;
      }
    };
  }

  public static Rule getDivide(final int mod)
  {
    return new Rule()
    {
      @Override
      public Integer getNextValue(Integer current, Integer target)
      {
        return current / mod;
      }

      @Override
      public String getDescription()
      {
        return "val / " + mod;
      }
    };
  }
}
