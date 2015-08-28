package node.hopper.rules.swing;

import node.hopper.rules.ConditionalRule;
import node.hopper.rules.PrioritizedConditionalRule;
import node.hopper.rules.Rule;

import java.awt.*;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public class PanelLibrary
{
  // Yes, a dependency injected map type arrangement would be better, get over yourself for the moment
  public Component getNewRuleComponent(Rule rule)
  {
    if(rule instanceof PrioritizedConditionalRule)
      return new PrioritizedConditionalRulePanel((PrioritizedConditionalRule) rule, this);
    else if(rule instanceof ConditionalRule)
      return new ConditionalRulePanel((ConditionalRule) rule, this);
    else if(rule != null)
      return new RulePanel(rule);
    return null;
  }
}
