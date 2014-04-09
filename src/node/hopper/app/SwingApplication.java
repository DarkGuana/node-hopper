package node.hopper.app;

import node.hopper.graph.DistanceGraph;
import node.hopper.graph.IntegerAggregation;
import node.hopper.graph.viewer.IntegerColorConverter;
import node.hopper.graph.viewer.swing.IntegerAggregateStatusPanel;
import node.hopper.graph.viewer.swing.IntegerAggregatePanel;
import node.hopper.rules.PrioritizedConditionalRule;
import node.hopper.rules.RuleLibrary;
import node.hopper.rules.simple.SimpleRuleLibrary;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dark Guana on 2014-03-29.
 */
public class SwingApplication extends JFrame
{
  private final RuleLibrary library;
  private IntegerAggregatePanel reporter;
  private IntegerAggregation display;
  private IntegerAggregateStatusPanel reporterStatus;
  private IntegerColorConverter colorConverter = new IntegerColorConverter();

  public SwingApplication(RuleLibrary library)
  {
    super("NodeHopper");
    this.library = library;

    buildLayout();

    getReporter().addListener(getReporterStatus());
  }

  private void buildLayout()
  {
    JScrollPane scrollPane = new JScrollPane(getReporter());
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    getContentPane().add(getReporterStatus(), BorderLayout.EAST);
  }

  public void setDisplay(IntegerAggregation display)
  {
    this.display = display;
    getReporter().setDataSource(display);
    getReporterStatus().setDataSource(display);
  }

  public IntegerAggregation getDisplay()
  {
    return display;
  }

  private static DistanceGraph getTestSystem(RuleLibrary library)
  {
    int width = 3000;
    int length = 3000;

    PrioritizedConditionalRule rule = library.getNewPCRule();
    rule.addNewConditional(library.combine(library.getLessThanTarget(), library.getMultiply(100)));
    rule.addNewConditional(library.combine(library.combine(library.getDividableBy(99), library.getMoreThanTarget()), library.getDivide(99)));
    rule.addNewConditional(library.combine(library.getMoreThan(0), library.getSubtract(1)));

    DistanceGraph dg = new DistanceGraph(width, length, rule);
    return dg;
  }

  public static void main(String[] args)
  {
    SwingApplication app = new SwingApplication(new SimpleRuleLibrary());
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    DistanceGraph graph = getTestSystem(app.library);
    app.setDisplay(graph);

    app.pack();
    app.setVisible(true);

    graph.populateAllDistances();
    app.pack();
  }

  private IntegerAggregatePanel getReporter()
  {
    if (reporter == null)
      reporter = new IntegerAggregatePanel(colorConverter);
    return reporter;
  }

  private IntegerAggregateStatusPanel getReporterStatus()
  {
    if (reporterStatus == null)
      reporterStatus = new IntegerAggregateStatusPanel(colorConverter);
    return reporterStatus;
  }
}
