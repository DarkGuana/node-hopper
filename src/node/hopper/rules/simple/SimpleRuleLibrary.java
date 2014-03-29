package node.hopper.rules.simple;

import node.hopper.rules.*;

/**
 * Created by Dark Guana on 2014-03-26.
 */
public class SimpleRuleLibrary implements RuleLibrary
{
  @Override
  public PrioritizedConditionalRule getNewPCRule()
  {
    return new PrioritizedConditionalRuleList();
  }

  @Override
  public ConditionalRule combine(Conditional conditional, Rule rule)
  {
    return new SimpleConditionalRule(conditional, rule);
  }

  @Override
  public Conditional combine(Conditional... conditions)
  {
    return new MultipleConditional(conditions);
  }

  @Override
  public Conditional getLessThanTarget()
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

  @Override
  public Conditional getMoreThanTarget()
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

  @Override
  public Conditional getMoreThan(final int check)
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

  @Override
  public Conditional getDividableBy(final int div)
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
        return "val divisible by " + div;
      }
    };
  }

  @Override
  public Rule getMultiply(final int mod)
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

  @Override
  public Rule getDivide(final int mod)
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

  @Override
  public Rule getSubtract(final int mod)
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
