package node.hopper.rules.swing;

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
    return null;
  }
}
