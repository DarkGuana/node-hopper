package node.hopper.graph.viewer.swing;

import node.hopper.graph.IntegerAggregate;
import node.hopper.graph.IntegerAggregation;
import node.hopper.graph.viewer.AggregatePositionListener;
import node.hopper.graph.viewer.IntegerColorConverter;

import javax.swing.*;

/**
 * Created by Dark Guana on 2014-04-02.
 */
public class IntegerAggregateStatusPanel extends JPanel implements AggregatePositionListener
{
  private IntegerAggregation dataSource;

  private JPanel startNodePanel;
  private JLabel startNodeLabel;
  private JLabel startNodeValueLabel;

  private JPanel finalNodePanel;
  private JLabel finalNodeLabel;
  private JLabel finalNodeValueLabel;

  private JPanel hopCountPanel;
  private JLabel hopCountLabel;
  private JLabel hopCountValueLabel;

  private JSplitPane reportPane;
  private JList reportHopList;
  private IntegerColorConversionScale reportScale;

  public IntegerAggregateStatusPanel()
  {
    super();
    buildLayout();
  }

  public void setColorConverter(IntegerColorConverter colorConverter)
  {
    getReportScale().setConverter(colorConverter);
  }

  public void setDataSource(IntegerAggregation dataSource)
  {
    this.dataSource = dataSource;
  }

  private void buildLayout()
  {
    // Set the layout to for the parent panel
    BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    setLayout(layout);

    // Add the sub components
    add(getStartNodePanel());
    add(getFinalNodePanel());
    add(getHopCountPanel());
    add(getReportPane());
  }

  private JPanel getStartNodePanel()
  {
    if (startNodePanel == null)
    {
      startNodePanel = new JPanel();
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
      reportPane.setLeftComponent(getReportHopList());
      reportPane.setRightComponent(getReportScale());
    }
    return reportPane;
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

  private IntegerColorConversionScale getReportScale()
  {
    if (reportScale == null)
      reportScale = new IntegerColorConversionScale();
    return reportScale;
  }

  @Override
  public void setPosition(Integer start, Integer target)
  {
    if (start != null)
      getStartNodeValueLabel().setText(start.toString());
    else
      getStartNodeValueLabel().setText("Not set");

    if (target != null)
      getFinalNodeValueLabel().setText(target.toString());
    else
      getFinalNodeValueLabel().setText("Not set");

    if (dataSource != null && start != null && target != null)
    {
      IntegerAggregate value = dataSource.getAggregate(start, target);
      if (value == null)
        getHopCountValueLabel().setText("Not set");
      else if (value.isNonterminating())
        getHopCountValueLabel().setText("Non-terminating");
      else
        getHopCountValueLabel().setText(value.getValue().toString());
    } else
      getHopCountValueLabel().setText("Not set");
  }
}
