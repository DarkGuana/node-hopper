package node.hopper.graph.viewer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dark Guana on 2014-03-30.
 */
public class IntegerColorConverter
{
  private Integer nonTerminatingMarker = -1;

  private Color unevaluated = Color.GREEN;
  private Color nonTerminating = Color.BLACK;

  private Integer minExpectedValue = 0;
  private Integer maxValue = 0;

  private Map<Integer, Color> usedColors = new HashMap<Integer, Color>();

  public Color getColor(Integer val)
  {
    if (val == null)
      return unevaluated;
    if (nonTerminatingMarker.equals(val))
      return nonTerminating;

    Color used = usedColors.get(val);
    if (used == null)
    {
      int red = (int) (127 * (1 + Math.sin(val / (50 * Math.PI))));
      int green = (int) (127 * (1 + Math.sin(Math.PI / 2 + val / (50 * Math.PI))));
      int blue = (int) (127 * (1 + Math.sin(Math.PI + val / (50 * Math.PI))));
      Color fresh = new Color(red, green, blue);
      usedColors.put(val, fresh);
      if (val > maxValue)
        maxValue = val;
      used = fresh;
    }
    return used;
  }

  public Integer getMaxValue()
  {
    return maxValue;
  }
}
