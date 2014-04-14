package node.hopper.rules;

import node.hopper.rules.simple.SimpleConditionalRule;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-03-22.
 */
public class SimpleConditionalRuleTest
{
  @Test
  public void creation()
  {
    Conditional condition = new Conditional()
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

    Rule rule = new Rule()
    {
      @Override
      public Integer getNextValue(Integer current, Integer target)
      {
        return current * 2;
      }

      @Override
      public String getDescription()
      {
        return "val * 2";
      }
    };

    ConditionalRule cRule = new SimpleConditionalRule(condition, rule);

    System.out.print("Testing - ");
    System.out.println(cRule.getDescription());

    Assert.assertFalse("-1 > 0", cRule.isApplicable(-1, 0));
    Assert.assertFalse("0 > 8", cRule.isApplicable(0, 8));

    Assert.assertTrue("9 > 3", cRule.isApplicable(9, 3));
    Assert.assertTrue("1 > 0", cRule.isApplicable(1, 0));

    Assert.assertEquals("Operation failed", cRule.getNextValue(4, 2), Integer.valueOf(8));
  }
}
