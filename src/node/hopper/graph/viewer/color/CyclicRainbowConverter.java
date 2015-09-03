package node.hopper.graph.viewer.color;

import java.awt.*;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public class CyclicRainbowConverter implements IntegerColorConverter
{
  private double redOffset = 0;
  private double greenOffset = Math.PI / 2;
  private double blueOffset = Math.PI;

  private double stepValue = 50 * Math.PI;

  @Override
  public Color getColor(int value)
  {
    int red = getColorValue(value, redOffset);
    int green = getColorValue(value, greenOffset);
    int blue = getColorValue(value, blueOffset);

    return new Color(red, green, blue);
  }

  private int getColorValue(int value, double offset)
  {
    return (int) ((RGB_MAX_VALUE/2) * (1 + Math.sin(offset + (value / stepValue))));
  }

  public double getRedOffset()
  {
    return redOffset;
  }

  public void setRedOffset(double redOffset)
  {
    this.redOffset = redOffset;
  }

  public double getGreenOffset()
  {
    return greenOffset;
  }

  public void setGreenOffset(double greenOffset)
  {
    this.greenOffset = greenOffset;
  }

  public double getBlueOffset()
  {
    return blueOffset;
  }

  public void setBlueOffset(double blueOffset)
  {
    this.blueOffset = blueOffset;
  }

  public double getStepValue()
  {
    return stepValue;
  }

  public void setStepValue(double stepValue)
  {
    this.stepValue = stepValue;
  }
}
