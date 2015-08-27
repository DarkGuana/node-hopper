package node.hopper.graph.viewer.swing;

import node.hopper.graph.IntegerAggregate;
import node.hopper.graph.IntegerAggregation;
import node.hopper.graph.viewer.AggregatePositionListener;
import node.hopper.graph.viewer.AggregatePositioner;
import node.hopper.graph.viewer.IntegerColorConverter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Dark Guana on 2014-04-02.
 */
public class IntegerAggregationStatusPanel extends JPanel implements AggregatePositionListener
{
  private static final String HOP_SEPARATOR = " -> ";

  private IntegerColorConverter colorConverter;
  private IntegerAggregation    dataSource;

  private JPanel startNodePanel;
  private JLabel startNodeLabel;
  private JLabel startNodeValueLabel;

  private JPanel finalNodePanel;
  private JLabel finalNodeLabel;
  private JLabel finalNodeValueLabel;

  private JPanel hopCountPanel;
  private JLabel hopCountLabel;
  private JLabel hopCountValueLabel;

  private JSplitPane  reportPane;
  private JScrollPane reportHopScrollPane;
  private JList       reportHopList;
  private Component   reportScale;

  public IntegerAggregationStatusPanel(IntegerColorConverter colorConverter)
  {
    super();
    this.colorConverter = colorConverter;
    buildLayout();
  }

  private void buildLayout()
  {
    // Set the layout to for the parent panel
    BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
    setLayout(layout);

    // Add the sub components
    add(getStartNodePanel());
    add(getFinalNodePanel());
    add(getHopCountPanel());
    add(getReportPane());

    // Arrange them
    getStartNodePanel().setAlignmentX(0);
    getFinalNodePanel().setAlignmentX(0);
    getHopCountPanel().setAlignmentX(0);
    getReportPane().setAlignmentX(0);
  }

  private JPanel getStartNodePanel()
  {
    if (startNodePanel == null)
    {
      startNodePanel = new JPanel();
      BoxLayout layout = new BoxLayout(startNodePanel, BoxLayout.LINE_AXIS);
      startNodePanel.setLayout(layout);

      startNodePanel.add(getStartNodeLabel());
      startNodePanel.add(getStartNodeValueLabel());
    }
    return startNodePanel;
  }

  private JLabel getStartNodeLabel()
  {
    if (startNodeLabel == null)
      startNodeLabel = new JLabel("Start Node:");
    return startNodeLabel;
  }

  private JLabel getStartNodeValueLabel()
  {
    if (startNodeValueLabel == null)
      startNodeValueLabel = new JLabel("Not Set");
    return startNodeValueLabel;
  }

  private JPanel getFinalNodePanel()
  {
    if (finalNodePanel == null)
    {
      finalNodePanel = new JPanel();
      BoxLayout layout = new BoxLayout(finalNodePanel, BoxLayout.LINE_AXIS);
      finalNodePanel.setLayout(layout);

      finalNodePanel.add(getFinalNodeLabel());
      finalNodePanel.add(getFinalNodeValueLabel());
    }
    return finalNodePanel;
  }

  private JLabel getFinalNodeLabel()
  {
    if (finalNodeLabel == null)
      finalNodeLabel = new JLabel("Final Node:");

    return finalNodeLabel;
  }

  private JLabel getFinalNodeValueLabel()
  {
    if (finalNodeValueLabel == null)
      finalNodeValueLabel = new JLabel("Not Set");
    return finalNodeValueLabel;
  }

  private JPanel getHopCountPanel()
  {
    if (hopCountPanel == null)
    {
      hopCountPanel = new JPanel();
      BoxLayout layout = new BoxLayout(hopCountPanel, BoxLayout.LINE_AXIS);
      hopCountPanel.setLayout(layout);

      hopCountPanel.add(getHopCountLabel());
      hopCountPanel.add(getHopCountValueLabel());
    }
    return hopCountPanel;
  }

  private JLabel getHopCountLabel()
  {
    if (hopCountLabel == null)
      hopCountLabel = new JLabel("Hop Count:");
    return hopCountLabel;
  }

  private JLabel getHopCountValueLabel()
  {
    if (hopCountValueLabel == null)
      hopCountValueLabel = new JLabel("Not Set");
    return hopCountValueLabel;
  }

  private JSplitPane getReportPane()
  {
    if (reportPane == null)
    {
      reportPane = new JSplitPane();
      reportPane.setLeftComponent(getReportHopScrollPane());
      reportPane.setRightComponent(getReportScale());
    }
    return reportPane;
  }

  private JScrollPane getReportHopScrollPane()
  {
    if (reportHopScrollPane == null)
    {
      reportHopScrollPane = new JScrollPane();
      reportHopScrollPane.setViewportView(getReportHopList());
    }
    return reportHopScrollPane;
  }

  private JList getReportHopList()
  {
    if (reportHopList == null)
    {
      reportHopList = new JList(new DefaultListModel());
      ((DefaultListModel) reportHopList.getModel()).addElement("No selection");
    }
    return reportHopList;
  }

  private Component getReportScale()
  {
    if (reportScale == null)
      reportScale = new IntegerColorConversionScale(colorConverter);
    return reportScale;
  }

  public void setDataSource(IntegerAggregation dataSource)
  {
    this.dataSource = dataSource;
  }

  @Override
  public void setPosition(Integer startNode, Integer finalNode, AggregatePositioner source)
  {
    if (startNode != null)
      getStartNodeValueLabel().setText(startNode.toString());
    else
      getStartNodeValueLabel().setText("Not set");

    if (finalNode != null)
      getFinalNodeValueLabel().setText(finalNode.toString());
    else
      getFinalNodeValueLabel().setText("Not set");

    if (dataSource != null && startNode != null && finalNode != null)
    {
      IntegerAggregate aggregate = dataSource.getAggregate(startNode, finalNode);
      if (aggregate != null && !aggregate.isNonterminating())
        getHopCountValueLabel().setText(aggregate.getValue().toString());
      else if (aggregate != null && aggregate.isNonterminating())
        getHopCountValueLabel().setText("Non terminating");
      else
        getHopCountValueLabel().setText("Not set");
    }
  }

  @Override
  public void setDetailedHops(List<Integer> hopPath, AggregatePositioner source)
  {
    DefaultListModel model = ((DefaultListModel) getReportHopList().getModel());
    model.clear();

    if (hopPath == null)
      ((DefaultListModel) reportHopList.getModel()).addElement("No selection");
    else
    {
      model.addElement(hopPath.get(0) + HOP_SEPARATOR + hopPath.get(hopPath.size() - 1));
      for (Integer value : hopPath)
      {
        model.addElement(value);
      }
    }
  }
}
