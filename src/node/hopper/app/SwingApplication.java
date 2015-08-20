package node.hopper.app;

import node.hopper.graph.DistanceGraph;
import node.hopper.graph.RectangularIntegerAggregation;
import node.hopper.graph.viewer.IntegerColorConverter;
import node.hopper.graph.viewer.swing.GraphPanel;
import node.hopper.graph.viewer.swing.GraphStatusPanel;
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

  private GraphPanel reporter;
  private RectangularIntegerAggregation display;
  private GraphStatusPanel reporterStatus;
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

  public void setDisplay(RectangularIntegerAggregation display)
  {
    this.display = display;
    getReporter().setDataSource(display);
  }

  public RectangularIntegerAggregation getDisplay()
  {
    return display;
  }

  private static DistanceGraph getTestSystem(RuleLibrary library)
  {
    int width = 800;
    int length = 800;

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

  private GraphPanel getReporter()
  {
    if (reporter == null)
      reporter = new GraphPanel(colorConverter);
    return reporter;
  }

  private GraphStatusPanel getReporterStatus()
  {
    if (reporterStatus == null)
      reporterStatus = new GraphStatusPanel(colorConverter);
    return reporterStatus;
  }
}
