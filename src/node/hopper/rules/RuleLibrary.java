package node.hopper.rules;

/**
 * Created by Dark Guana on 2014-03-26.
 */
public class RuleLibrary
{
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
        return "val divisible by " + div;
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
