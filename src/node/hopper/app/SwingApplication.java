package node.hopper.app;

import node.hopper.graph.DistanceGraph;
import node.hopper.graph.IntegerAggregation;
import node.hopper.graph.viewer.IntegerColorConverter;
import node.hopper.graph.viewer.swing.IntegerAggregationPanel;
import node.hopper.graph.viewer.swing.IntegerAggregationStatusPanel;
import node.hopper.rules.PrioritizedConditionalRule;
import node.hopper.rules.RuleLibrary;
import node.hopper.rules.simple.SimpleRuleLibrary;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dark Guana on 2014-03-29.
 */
public class SwingApplication
{
  private final RuleLibrary library;

  private final JFrame appFrame;

  private IntegerAggregationPanel       reporter;
  private IntegerAggregation            display;
  private IntegerAggregationStatusPanel reporterStatus;
  private IntegerColorConverter colorConverter = new IntegerColorConverter();

  public SwingApplication(RuleLibrary library)
  {
    appFrame = new JFrame("NodeHopper");
    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.library = library;

    buildLayout();

    getReporter().addListener(getReporterStatus());
  }

  private void buildLayout()
  {
    JScrollPane scrollPane = new JScrollPane(getReporter());
    appFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    appFrame.getContentPane().add(getReporterStatus(), BorderLayout.EAST);
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
    int width = 1000;
    int length = 1000;

    PrioritizedConditionalRule rule = library.getNewPCRule();
//    rule.addNewConditional(library.combine(library.getMoreThanTarget(), library.getSubtract(1)));
//    rule.addNewConditional(library.combine(library.getLessThanTarget(), library.getMultiply(2)));
    rule.addNewConditional(library.combine(library.getLessThanTarget(), library.getMultiply(100)));
    rule.addNewConditional(
      library.combine(library.combine(library.getDividableBy(99), library.getMoreThanTarget()), library.getDivide(99)));
    rule.addNewConditional(library.combine(library.getMoreThan(0), library.getSubtract(1)));

    DistanceGraph dg = new DistanceGraph(width, length, DistanceGraph.PopulationMethod.FINISH_TO_START, rule);
    return dg;
  }

  public static void main(String[] args)
  {
    SwingApplication app = new SwingApplication(new SimpleRuleLibrary());

    DistanceGraph graph = getTestSystem(app.library);
    app.setDisplay(graph);
    app.show();

    graph.populateAllDistances();
  }

  private void show()
  {
    appFrame.pack();
    appFrame.setVisible(true);
  }

  private IntegerAggregationPanel getReporter()
  {
    if (reporter == null)
      reporter = new IntegerAggregationPanel(colorConverter);
    return reporter;
  }

  private IntegerAggregationStatusPanel getReporterStatus()
  {
    if (reporterStatus == null)
      reporterStatus = new IntegerAggregationStatusPanel(colorConverter);
    return reporterStatus;
  }
}
