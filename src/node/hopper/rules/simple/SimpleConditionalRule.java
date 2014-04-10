package node.hopper.rules.simple;

import node.hopper.rules.Conditional;
import node.hopper.rules.ConditionalRule;
import node.hopper.rules.Rule;

/**
 * Created by Dark Guana on 2014-03-22.
 */
public class SimpleConditionalRule implements ConditionalRule
{
  private Conditional condition;
  private Rule rule;

  public SimpleConditionalRule(Conditional condition, Rule rule)
  {
    this.condition = condition;
    this.rule = rule;
  }

  @Override
  public boolean isApplicable(Integer check, Integer target)
  {
    return condition.isApplicable(check, target);
  }

  @Override
  public Integer getNextValue(Integer current, Integer target)
  {
    return rule.getNextValue(current, target);
  }

  @Override
  public String getDescription()
  {
    StringBuilder description = new StringBuilder();
    description.append("if( ").append(condition.getDescription()).append(" ) ");
    description.append("then { ").append(rule.getDescription()).append(" }");
    return description.toString();
  }

  @Override
  public String toString()
  {
    return getDescription();
  }
}
