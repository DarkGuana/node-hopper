package node.hopper.rules.swing;

import node.hopper.rules.ConditionalRule;
import node.hopper.rules.PrioritizedConditionalRule;

import javax.swing.*;
import java.awt.*;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public class PrioritizedConditionalRulePanel extends JPanel
{
  private final PanelLibrary               panelLibrary;
  private final PrioritizedConditionalRule rule;

  public PrioritizedConditionalRulePanel(PrioritizedConditionalRule rule, PanelLibrary panelLibrary)
  {
    this.rule = rule;
    this.panelLibrary = panelLibrary;

    buildLayout();
  }

  private void buildLayout()
  {
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Rules"));

    int ruleCount = 1;
    for(ConditionalRule subRule : rule.getRules())
    {
      Component subRuleComponent = panelLibrary.getNewRuleComponent(subRule);
      add(getNewSubRulePanel(ruleCount++, subRuleComponent));
    }

    // I like BoxLayouts simplicity, but spacers are ridiculous.
    JPanel spacer = new JPanel();
    spacer.setPreferredSize(new Dimension(300, 1));
    add(spacer);
  }

  private JPanel getNewSubRulePanel(int rulePriority, Component subRuleComponent)
  {
    JPanel subPanel = new JPanel();
    subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.LINE_AXIS));

    JLabel label = new JLabel(rulePriority + ": ");
    subPanel.add(label);
    if(subRuleComponent != null)
      subPanel.add(subRuleComponent);

    return subPanel;
  }
}
