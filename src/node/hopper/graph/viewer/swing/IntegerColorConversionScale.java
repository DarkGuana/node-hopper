package node.hopper.graph.viewer.swing;

import node.hopper.graph.viewer.IntegerColorConverter;
import sun.swing.SwingUtilities2;

import javax.swing.*;
import java.awt.*;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public class IntegerColorConversionScale extends JPanel
{
  public static final Integer DEFAULT_VERTICAL_GAP = 5;
  public static final Integer DEFAULT_HORIZONTAL_GAP = 5;
  public static final Integer DEFAULT_BAR_WIDTH = 15;
  public static final Integer DEFAULT_TICK_WIDTH = 20;
  public static final Integer DEFAULT_TICK_COUNT = 20;

  private Integer verticalGap = DEFAULT_VERTICAL_GAP;
  private Integer horizontalGap = DEFAULT_HORIZONTAL_GAP;
  private Integer barWidth = DEFAULT_BAR_WIDTH;
  private Integer tickWidth = DEFAULT_TICK_WIDTH;
  private Integer tickCount = DEFAULT_TICK_COUNT;

  private IntegerColorConverter converter;

  public IntegerColorConversionScale(IntegerColorConverter converter)
  {
    this.converter = converter;
    setPreferredSize(new Dimension(200, 400));
  }

  @Override
  protected void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    Graphics2D g = (Graphics2D) graphics.create();

    Dimension size = getSize();
    int topY = verticalGap;
    int bottomY = size.height - verticalGap;
    int barX = horizontalGap;
    int tickX = horizontalGap+barWidth+tickWidth;

    // Draw color bar
    Color originalColor = g.getColor();
    for (int i = topY; i < bottomY; i++)
    {
      float farAlong = 1 - ((float) i) / bottomY;
      Color valueColor = converter.getColor((int) (farAlong * converter.getMaxValue()));
      g.setColor(valueColor);
      g.drawLine(barX, i, barX + barWidth, i);
    }
    g.setColor(originalColor);

    // Draw the vertical line of the bar
    g.drawLine(barX, topY, barX, bottomY);

    // Draw tick marks and labels
    for(int i = 0 ; i <= tickCount ; i++)
    {
      float ratio = (i/(float)tickCount);
      int y = bottomY - (int)(ratio * (bottomY - topY));
      Integer label = (int)(ratio * converter.getMaxValue());
      g.drawLine(barX, y, tickX, y);
      g.drawString(label.toString(), tickX, y);
    }
    g.dispose();
  }
}
