package node.hopper.rules.swing;

import node.hopper.rules.ConditionalRule;

import javax.swing.*;
import java.awt.*;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public class ConditionalRulePanel extends JPanel
{
  private final ConditionalRule rule;
  private final PanelLibrary    panelLibrary;

  public ConditionalRulePanel(ConditionalRule rule, PanelLibrary panelLibrary)
  {
    this.rule = rule;
    this.panelLibrary = panelLibrary;

    buildLayout();
  }

  private void buildLayout()
  {
    String title = rule.getConditional().getDescription();
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), title));
    Component ruleComponent = panelLibrary.getNewRuleComponent(rule.getRule());
    if (ruleComponent != null)
      add(ruleComponent);
  }
}
