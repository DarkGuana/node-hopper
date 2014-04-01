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

  private Map<Integer, Color> usedColors = new HashMap<Integer, Color>();

  int redMax = (int) (127*(1+Math.sin(0/(100*Math.PI))));
  int redMin = (int) (127*(1+Math.sin(0/(100*Math.PI))));

  int blueMax = (int) (127*(1+Math.cos(0/(100*Math.PI))));
  int blueMin = (int) (127*(1+Math.cos(0/(100*Math.PI))));

  public Color getColor(Integer val)
  {
    if(val == null)
      return unevaluated;
    if(nonTerminatingMarker.equals(val))
      return nonTerminating;

    Color used = usedColors.get(val);
    if(used == null)
    {
      int red = (int) (127*(1+Math.sin(val/(50*Math.PI))));
      int blue = (int) (127*(1+Math.cos(val/(50*Math.PI))));
      redMax = Math.max(redMax, red);
      redMin = Math.min(redMin, red);
      blueMax = Math.max(blueMax, blue);
      blueMin = Math.min(blueMin, blue);
      Color fresh = new Color(red,0,blue);
      usedColors.put(val, fresh);
      used = fresh;
//      System.out.println("red = " + redMin + " - "+redMax +"   blue = "+blueMin + " - "+blueMax);
    }
    return used;
  }
}
