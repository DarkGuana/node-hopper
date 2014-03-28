package node.hopper.rules.simple;

import node.hopper.rules.ConditionalRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark Guana on 2014-03-22.
 */
public class PrioritizedConditionalRuleList implements ConditionalRule
{
  private List<ConditionalRule> rules;

  public PrioritizedConditionalRuleList()
  {
    rules = new ArrayList<ConditionalRule>(0);
  }

  public void addNewConditional(ConditionalRule rule)
  {
    rules.add(rule);
  }

  @Override
  public Integer getNextValue(Integer current, Integer target)
  {
    for (ConditionalRule rule : rules)
    {
      if (rule.isApplicable(current, target))
        return rule.getNextValue(current, target);
    }
    return null;
  }

  @Override
  public String getDescription()
  {
    StringBuilder description = new StringBuilder();
    description.append("ConditionalRuleList");
    if (!rules.isEmpty())
      description.append("\n{");
    for (ConditionalRule rule : rules)
    {
      //depends on sub-rules being one line, could refactor to include an indent amount, but lets not go nuts just yet.
      description.append("\n  ").append(rule.getDescription());
    }
    if (!rules.isEmpty())
      description.append("\n}");
    return description.toString();
  }

  @Override
  public boolean isApplicable(Integer check, Integer target)
  {
    for (ConditionalRule rule : rules)
    {
      if (rule.isApplicable(check, target))
        return true;
    }
    return false;
  }

  @Override
  public String toString()
  {
    return getDescription();
  }
}
