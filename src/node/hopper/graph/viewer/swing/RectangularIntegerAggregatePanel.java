package node.hopper.graph.viewer.swing;

import node.hopper.graph.RectangularIntegerAggregation;
import node.hopper.graph.viewer.IntegerColorConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Created by Dark Guana on 2014-03-29.
 */
public class RectangularIntegerAggregatePanel extends JPanel
{
  private IntegerColorConverter converter = new IntegerColorConverter();

  private RectangularIntegerAggregation dataSource;
  private Image buffer;
  private Boolean currentRenderComplete = true;
  private IntegerColorConverter colorPicker = new IntegerColorConverter();
  private Timer repaintTimer = null;

  public RectangularIntegerAggregatePanel()
  {
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
        Integer val = dataSource.getValueAt(x, y);
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
      repaintTimer.setDelay(delay*8);
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
}
