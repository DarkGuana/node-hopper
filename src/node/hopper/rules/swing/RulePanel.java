package node.hopper.rules.swing;

import node.hopper.rules.Rule;

import javax.swing.*;
import java.awt.*;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public class RulePanel extends JPanel
{
  private final Rule rule;

  public RulePanel(Rule rule)
  {
    this.rule = rule;
    buildLayout();
  }

  private void buildLayout()
  {
    add(new JLabel(rule.getDescription()));
  }
}
