package node.hopper.rules.swing;

import node.hopper.rules.Rule;

import javax.swing.*;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public class RulesPanel extends JPanel
{
  private final PanelLibrary library;
  private       Rule         rule;

  public RulesPanel(PanelLibrary library)
  {
    this.library = library;
  }

  public void setRule(Rule rule)
  {
    this.rule = rule;
    resetDisplay();
  }

  private void resetDisplay()
  {
    removeAll();

    if (rule != null)
      add(library.getNewRuleComponent(rule));
  }
}
