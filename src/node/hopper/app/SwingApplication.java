package node.hopper.app;

import node.hopper.graph.DistanceGraph;
import node.hopper.graph.RectangularIntegerAggregation;
import node.hopper.graph.viewer.swing.RectangularIntegerAggregatePanel;
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
  private RectangularIntegerAggregatePanel reporter = new RectangularIntegerAggregatePanel();
  private RectangularIntegerAggregation display;

  public SwingApplication()
  {
    super("NodeHopper");
    this.library = new SimpleRuleLibrary();
    getContentPane().add(reporter, BorderLayout.CENTER);
  }

  public void setDisplay(RectangularIntegerAggregation display)
  {
    this.display = display;
    reporter.setDataSource(display);
  }

  public RectangularIntegerAggregation getDisplay()
  {
    return display;
  }

  private static DistanceGraph getTestSystem(RuleLibrary library)
  {
    int width = 501;
    int length = 501;

    PrioritizedConditionalRule rule = library.getNewPCRule();
    rule.addNewConditional(library.combine(library.getLessThanTarget(), library.getMultiply(31)));
    rule.addNewConditional(library.combine(library.combine(library.getDividableBy(10), library.getMoreThanTarget()), library.getDivide(10)));
    rule.addNewConditional(library.combine(library.getMoreThan(0), library.getSubtract(1)));

    DistanceGraph dg = new DistanceGraph(width, length, rule);
    return dg;
  }

  public static void main(String[] args)
  {
    SwingApplication app = new SwingApplication();
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    DistanceGraph graph = getTestSystem(app.library);
    app.setDisplay(graph);

    app.pack();
    app.setVisible(true);

    graph.populateAllDistances();
  }
}
