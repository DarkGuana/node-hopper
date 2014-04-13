package node.hopper.graph.viewer.swing;

import node.hopper.graph.IntegerAggregateListener;
import node.hopper.graph.IntegerAggregation;
import node.hopper.graph.viewer.AggregatePositionListener;
import node.hopper.graph.viewer.AggregatePositioner;
import node.hopper.graph.viewer.IntegerColorConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark Guana on 2014-03-29.
 */
public class IntegerAggregatePanel extends JPanel implements AggregatePositioner, IntegerAggregateListener
{
  private IntegerAggregation dataSource;
  private IntegerColorConverter colorPicker;

  private Image buffer;
  private Timer repaintTimer = null;

  private final List<AggregatePositionListener> listeners = new ArrayList<AggregatePositionListener>(0);

  public IntegerAggregatePanel(IntegerColorConverter converter)
  {
    this.colorPicker = converter;
    addMouseMotionListener(new MouseMotionListener()
    {
      @Override
      public void mouseDragged(MouseEvent e)
      {
        // do nothing
      }

      @Override
      public void mouseMoved(MouseEvent e)
      {
        Integer start = Math.min(e.getY(), dataSource.getMaxStartNode());
        Integer end = Math.min(e.getX(), dataSource.getMaxTargetNode());
        if (dataSource != null)
        {
          for (AggregatePositionListener listener : listeners)
          {
            listener.setPosition(start, end);
          }
        }
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (buffer != null)
    {
      g.drawImage(buffer, 0, 0, new ImageObserver()
      {
        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
        {
          return false;
        }
      });
    }
  }

  public void setDataSource(IntegerAggregation dataSource)
  {
    this.dataSource = dataSource;
    dataSource.addListener(this);
    setPreferredSize(new Dimension(dataSource.getMaxTargetNode(), dataSource.getMaxStartNode()));
    initBuffer();
    if (dataSource.isActive())
      startRepainting();
  }

  private void initBuffer()
  {
    buffer = new BufferedImage(dataSource.getMaxTargetNode(), dataSource.getMaxStartNode(), BufferedImage.TYPE_3BYTE_BGR);
    Graphics draw = buffer.getGraphics().create();
    for (int x = 0; x < dataSource.getMaxTargetNode(); x++)
    {
      for (int y = 0; y < dataSource.getMaxStartNode(); y++)
      {
        // y is start node, x is target node
        Integer val = dataSource.getAggregate(y, x);
        draw.setColor(colorPicker.getColor(val));
        draw.drawLine(x, y, x, y);
      }
    }
    draw.dispose();
  }

  private void startRepainting()
  {
    repaintTimer = new Timer(200, new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (buffer != null)
        {
          repaint();
        }
      }
    });
    repaintTimer.setRepeats(true);
    repaintTimer.start();
  }

  private void stopRepainting()
  {
    repaintTimer.stop();
    repaintTimer = null;
    // Make sure the buffer's final state is represented
    repaint();
  }

  @Override
  public void addListener(AggregatePositionListener listener)
  {
    listeners.add(listener);
  }

  @Override
  public void removeListener(AggregatePositionListener listener)
  {
    listeners.remove(listener);
  }

  @Override
  public void aggregateChanged(Integer start, Integer target, Integer aggregateValue, IntegerAggregation source)
  {
    // Tragically, the non-performant part of this method appears to be the drawLine call,
    // re-getting the graphics item is negligible in comparison.
    Graphics g = buffer.getGraphics();
    g.setColor(colorPicker.getColor(aggregateValue));
    g.drawLine(target, start, target, start);
    g.dispose();
  }

  @Override
  public void activityChanged(Boolean active, IntegerAggregation source)
  {
    if (active)
      startRepainting();
    else
      stopRepainting();
  }
}
