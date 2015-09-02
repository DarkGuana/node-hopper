package node.hopper.graph.viewer.swing;

import node.hopper.graph.IntegerAggregate;
import node.hopper.graph.IntegerAggregation;
import node.hopper.graph.IntegerAggregationListener;
import node.hopper.graph.viewer.*;
import node.hopper.graph.viewer.color.IntegerColorConverter;
import node.hopper.graph.viewer.color.IntegerColorConverterListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-03-29.
 */
public class IntegerAggregationPanel extends JPanel implements AggregatePositioner, IntegerAggregationListener, IntegerColorConverterListener
{
  private static final int DEFAULT_REPAINTING_TIME = 25;
  private IntegerAggregation    dataSource;
  private IntegerColorConverter colorPicker;

  private Image buffer;
  private Timer repaintTimer = null;

  private boolean[][] renderHit;

  private final List<AggregatePositionListener> listeners = new ArrayList<AggregatePositionListener>(0);

  public IntegerAggregationPanel(IntegerColorConverter converter)
  {
    this.colorPicker = converter;
    converter.addListener(this);
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
          reportCurrentPosition(start, end);
        }
      }
    });
    addMouseListener(new MouseListener()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        Integer start = Math.min(e.getY(), dataSource.getMaxStartNode());
        Integer end = Math.min(e.getX(), dataSource.getMaxTargetNode());

        if (dataSource != null)
        {
          reportDetailInfoPosition(start, end);
        }
      }

      @Override
      public void mousePressed(MouseEvent e)
      {
        // do nothing
      }

      @Override
      public void mouseReleased(MouseEvent e)
      {
        // do nothing
      }

      @Override
      public void mouseEntered(MouseEvent e)
      {
        // do nothing
      }

      @Override
      public void mouseExited(MouseEvent e)
      {
        // do nothing
      }
    });
  }

  private void reportDetailInfoPosition(Integer start, Integer end)
  {
    if(dataSource != null)
    {
      List<Integer> hopList = dataSource.getHops(start, end);
      for (AggregatePositionListener listener : listeners)
      {
        listener.setDetailedHops(hopList, this);
      }
    }
  }

  private void reportCurrentPosition(Integer start, Integer end)
  {
    for (AggregatePositionListener listener : listeners)
    {
      listener.setPosition(start, end, this);
    }
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
    renderHit = new boolean[dataSource.getMaxTargetNode()+1][dataSource.getMaxStartNode()+1];
    dataSource.addListener(this);
    setPreferredSize(new Dimension(dataSource.getMaxTargetNode(), dataSource.getMaxStartNode()));
    initBuffer();
    if (dataSource.isActive())
      startRepainting();
  }

  private void initBuffer()
  {
    buffer =
      new BufferedImage(dataSource.getMaxTargetNode(), dataSource.getMaxStartNode(), BufferedImage.TYPE_3BYTE_BGR);
    Graphics draw = buffer.getGraphics().create();
    for (int x = 0; x < dataSource.getMaxTargetNode(); x++)
    {
      for (int y = 0; y < dataSource.getMaxStartNode(); y++)
      {
        // y is start node, x is target node
        IntegerAggregate val = dataSource.getAggregate(y, x);
        draw.setColor(colorPicker.getColor(val));
        draw.drawLine(x, y, x, y);
      }
    }
    draw.dispose();
  }

  private void startRepainting()
  {
    repaintTimer = new Timer(DEFAULT_REPAINTING_TIME, new ActionListener()
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
  public void aggregateChanged(Integer start, Integer target, IntegerAggregate aggregateValue,
    IntegerAggregation source)
  {
    // Tragically, the non-performant part of this method appears to be the drawLine call,
    // re-getting the graphics item is negligible in comparison.
    Graphics g = buffer.getGraphics();
    g.setColor(colorPicker.getColor(aggregateValue));
    g.drawLine(target, start, target, start);
    if (!renderHit[target][start])
      renderHit[target][start] = true;
    else
      System.out.println(aggregateValue.getValue());

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

  @Override
  public void maxValueChanged(Integer maxValue, IntegerColorConverter source)
  {
    // No op, max value's not important in this context
  }

  @Override
  public void nonterminatingColorChanged(Color newColor, IntegerColorConverter source)
  {
    Runnable repaintNonterminating = new Runnable()
    {
      @Override
      public void run()
      {
        Graphics draw = buffer.getGraphics().create();
        draw.setColor(colorPicker.getNonterminatingColor());
        for (int x = 0; x < dataSource.getMaxTargetNode(); x++)
        {
          for (int y = 0; y < dataSource.getMaxStartNode(); y++)
          {
            IntegerAggregate val = dataSource.getAggregate(y, x);
            if(val != null && val.isNonterminating())
              draw.drawLine(x, y, x, y);
          }
          repaint();
        }
        draw.dispose();
        repaint();
      }
    };
    Thread repaintNonterminatingThread = new Thread(repaintNonterminating, "repaintNonterminating");
    repaintNonterminatingThread.start();
  }
}
