package node.hopper.graph.viewer.color;

import java.awt.*;

/**
 * (In progress) Creates a single smooth gradient up to the specified maximum value.
 */
public class SingleRainbowFade implements IntegerColorConverter
{
  double maxValue = 30000;

  @Override
  public Color getColor(int value)
  {
    int redValue = (int) (RGB_MAX_VALUE * getCyclicalValue(1, value));
    int greenValue = (int) (RGB_MAX_VALUE * getCyclicalValue(2, value));
    int blueValue = (int) (RGB_MAX_VALUE * getCyclicalValue(3, value));
    redValue = Math.min(redValue, RGB_MAX_VALUE);
    greenValue = Math.min(greenValue, RGB_MAX_VALUE);
    blueValue = Math.min(blueValue, RGB_MAX_VALUE);
    return new Color(redValue, greenValue, blueValue);
  }

  public double getCyclicalValue(double multiple, int value)
  {
    return (1 + Math.sin(multiple * 2 * Math.PI * (value / maxValue))) / 2;
  }

  public static void main(String[] args)
  {
    SingleRainbowFade fade = new SingleRainbowFade();
    System.out.println("0 - " + fade.getColor(0));
    System.out.println("30 - " + fade.getColor(30));
    System.out.println("300 - " + fade.getColor(300));
    System.out.println("3000 - " + fade.getColor(3000));
    System.out.println("30000 - " + fade.getColor(30000));
  }
}
