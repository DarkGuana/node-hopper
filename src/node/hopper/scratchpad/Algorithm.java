package node.hopper.scratchpad;

import node.hopper.rules.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dark Guana on 2014-03-22.
 */
public class Algorithm
{
  public static void main(String[] args)
  {
    PrioritizedConditionalRuleList rule = new PrioritizedConditionalRuleList();
    rule.addNewConditional(new SimpleConditionalRule(getLessThan(), getMultiply(2)));
    rule.addNewConditional(new SimpleConditionalRule(new MultipleConditional(getDividableBy(3), getMoreThan()), getDivide(3)));
    rule.addNewConditional(new SimpleConditionalRule(getMoreThan(0), getSubtract(1)));

    Integer current = 3;
    Integer target = 90;
    Set<Integer> history = new HashSet<Integer>();
    for (int i = 0 ; i < 100 && !current.equals(target) && current != null; i++)
    {
      history.add(current);
      Integer next = rule.getNextValue(current, target);
      System.out.println(current + " -> " + next);
      current = next;
      if (history.contains(next))
      {
        System.out.println("Loop detected");
        break;
      }
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

  public static Conditional getMoreThan(final int check)
  {
    return new Conditional()
    {
      @Override
      public boolean isApplicable(Integer current, Integer target)
      {
        return current > check;
      }

      @Override
      public String getDescription()
      {
        return "val > " + check;
      }
    };
  }

  public static Conditional getDividableBy(final int div)
  {
    return new Conditional()
    {
      @Override
      public boolean isApplicable(Integer current, Integer target)
      {
        return current % div == 0;
      }

      @Override
      public String getDescription()
      {
        return "val divisible by "+div;
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

  public static Rule getSubtract(final int mod)
  {
    return new Rule()
    {
      @Override
      public Integer getNextValue(Integer current, Integer target)
      {
        return current - mod;
      }

      @Override
      public String getDescription()
      {
        return "val - " + mod;
      }
    };
  }
}
