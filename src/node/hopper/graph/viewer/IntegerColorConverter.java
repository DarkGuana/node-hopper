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
  private Color nonTerminating = Color.WHITE;

  private Integer maxExpectedValue = 255;
  private Integer minExpectedValue = 0;

  private Map<Integer, Color> usedColors = new HashMap<Integer, Color>();

  public Color getColor(Integer val)
  {
    if(val == null)
      return unevaluated;
    if(nonTerminatingMarker.equals(val))
      return nonTerminating;

    Color used = usedColors.get(val);
    if(used == null)
    {
      Integer truncated = Math.min(val, maxExpectedValue);
      Color fresh = new Color(truncated,truncated,truncated);
      usedColors.put(val, fresh);
      used = fresh;
    }
    return used;
  }
}
