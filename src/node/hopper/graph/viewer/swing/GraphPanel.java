package node.hopper.graph.viewer.swing;

import node.hopper.graph.RectangularIntegerAggregation;
import node.hopper.graph.viewer.IntegerColorConverter;
import node.hopper.graph.viewer.Viewer;
import node.hopper.graph.viewer.ViewerListener;

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
public class GraphPanel extends JPanel implements Viewer
{
  private RectangularIntegerAggregation dataSource;
  private Image buffer;
  private IntegerColorConverter colorPicker;
  private Timer repaintTimer = null;
  private final List<ViewerListener> listeners = new ArrayList<ViewerListener>(0);

  public GraphPanel(IntegerColorConverter converter)
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
        Integer start = Math.min(e.getY(), dataSource.getLength());
        Integer end = Math.min(e.getX(), dataSource.getWidth());
        alertListenersToPositionChange(start, end);
      }
    });
  }

  private void alertListenersToPositionChange(Integer start, Integer end)
  {
    for (ViewerListener listener : listeners)
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

  public void setDataSource(RectangularIntegerAggregation dataSource)
  {
    this.dataSource = dataSource;
    setPreferredSize(new Dimension(dataSource.getWidth(), dataSource.getLength()));
    buffer = new BufferedImage(dataSource.getWidth(), dataSource.getLength(), BufferedImage.TYPE_3BYTE_BGR);
    updateBuffer();
    startRepainting();
  }

  private void updateBuffer()
  {
    long startUpdate = System.currentTimeMillis();

    boolean imageComplete = true;
    Graphics draw = buffer.getGraphics().create();
    for (int x = 0; x < dataSource.getWidth(); x++)
    {
      for (int y = 0; y < dataSource.getLength(); y++)
      {
        // y is start node, x is target node
        Integer val = dataSource.getValueAt(y, x);
        if (val == null)
          imageComplete = false;
        draw.setColor(colorPicker.getColor(val));
        draw.drawLine(x, y, x, y);
      }
    }
    draw.dispose();

    long endUpdate = System.currentTimeMillis();

    if (imageComplete)
      stopRepainting();
    else if (repaintTimer != null)
    {
      int delay = (int) (endUpdate - startUpdate);
      repaintTimer.setDelay(delay * 8);
    }
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
          updateBuffer();
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
  }

  @Override
  public void addListener(ViewerListener listener)
  {
    listeners.add(listener);
  }

  @Override
  public void removeListener(ViewerListener listener)
  {
    listeners.remove(listener);
  }
}
