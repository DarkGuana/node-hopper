package node.hopper.app;

import node.hopper.graph.DistanceGraph;
import node.hopper.graph.IntegerAggregation;
import node.hopper.graph.viewer.color.IntegerColorLibrary;
import node.hopper.graph.viewer.color.swing.IntegerColorConverterControls;
import node.hopper.graph.viewer.swing.IntegerAggregationPanel;
import node.hopper.graph.viewer.swing.IntegerAggregationStatusPanel;
import node.hopper.rules.PrioritizedConditionalRule;
import node.hopper.rules.RuleLibrary;
import node.hopper.rules.simple.SimpleRuleLibrary;
import node.hopper.rules.swing.PanelLibrary;
import node.hopper.rules.swing.RulesPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dark Guana on 2014-03-29.
 */
public class SwingApplication
{
  private final RuleLibrary library;

  private final JFrame appFrame;

  private IntegerAggregationPanel reporter;
  private IntegerAggregation display;
  private IntegerAggregationStatusPanel reporterStatus;
  private IntegerColorLibrary colorConverter = new IntegerColorLibrary();
  private RulesPanel rulesDisplay = new RulesPanel(new PanelLibrary());
  private JScrollPane reporterScrollPane;
  private JPanel controlPanel;
  private IntegerColorConverterControls colorControls;

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
    appFrame.getContentPane().add(getReporterScrollPane(), BorderLayout.CENTER);
    appFrame.getContentPane().add(getReporterStatus(), BorderLayout.EAST);
    appFrame.getContentPane().add(getControlPanel(), BorderLayout.SOUTH);

    controlPanel.add(getRulesDisplay());
    controlPanel.add(getColorControls());
  }

  public void setDisplay(IntegerAggregation display)
  {
    this.display = display;
    getReporter().setDataSource(display);
    getReporterStatus().setDataSource(display);
    getRulesDisplay().setRule(display.getRule());
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
    rule.addNewConditional(library.combine(library.getLessThanTarget(), library.getMultiply(2)));
//    rule.addNewConditional(
//        library
//            .combine(library.combine(library.getDividableBy(301), library.getMoreThanTarget()), library.getDivide(301)));
    rule.addNewConditional(library.combine(library.getMoreThanTarget(), library.getSubtract(1)));

    return new DistanceGraph(width, length, DistanceGraph.PopulationMethod.FINISH_TO_START, rule);
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

  public RulesPanel getRulesDisplay()
  {
    return rulesDisplay;
  }

  public JScrollPane getReporterScrollPane()
  {
    if (reporterScrollPane == null)
      reporterScrollPane = new JScrollPane(getReporter());
    return reporterScrollPane;
  }

  public JPanel getControlPanel()
  {
    if(controlPanel == null)
    {
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
    }
    return controlPanel;
  }

  public IntegerColorConverterControls getColorControls()
  {
    if(colorControls == null)
      colorControls = new IntegerColorConverterControls(colorConverter);
    return colorControls;
  }
}
