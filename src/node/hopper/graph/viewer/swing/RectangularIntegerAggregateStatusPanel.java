package node.hopper.graph.viewer.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dark Guana on 2014-04-02.
 */
public class RectangularIntegerAggregateStatusPanel extends JPanel
{
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
  private Component reportScale;

  public RectangularIntegerAggregateStatusPanel()
  {
    super();
    buildLayout();
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

  private Component getReportScale()
  {
    if (reportScale == null)
      reportScale = new JLabel("PlaceHolder");
    return reportScale;
  }
}
