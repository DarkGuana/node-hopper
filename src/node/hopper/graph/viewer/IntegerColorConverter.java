package node.hopper.graph.viewer;

import com.sun.javaws.exceptions.InvalidArgumentException;
import node.hopper.graph.IntegerAggregate;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-03-30.
 */
public class IntegerColorConverter
{
  private Color unevaluated = Color.DARK_GRAY;
  private Color nonTerminating = Color.BLACK;

  private Integer minExpectedValue = 0;
  private Integer maxValue = 0;

  private Map<Integer, Color> usedColors = new HashMap<Integer, Color>();
  private final Set<IntegerColorConverterListener> listeners = new HashSet<IntegerColorConverterListener>();

  public Color getColor(IntegerAggregate val)
  {
    if (val == null)
      return unevaluated;
    if (val.isNonterminating())
      return nonTerminating;
    return getColor(val.getValue());
  }

  public Color getColor(Integer value)
  {
    if (value == null)
      throw new IllegalArgumentException("null values are not directly accessible");

    Color used = usedColors.get(value);

    if (used == null)
      getFreshColor(value);

    return usedColors.get(value);
  }

  private Color getFreshColor(Integer value)
  {
    int red = (int) (127 * (1 + Math.sin(value / (50 * Math.PI))));
    int green = (int) (127 * (1 + Math.sin(Math.PI / 2 + value / (50 * Math.PI))));
    int blue = (int) (127 * (1 + Math.sin(Math.PI + value / (50 * Math.PI))));
    Color fresh = new Color(red, green, blue);

    usedColors.put(value, fresh);

    if (value > maxValue)
    {
      maxValue = value;
      for(IntegerColorConverterListener listener : listeners)
        listener.maxValueChanged(value, this);
    }

    return fresh;
  }

  public Integer getMaxValue()
  {
    return maxValue;
  }

  public void addListener(IntegerColorConverterListener listener)
  {
    listeners.add(listener);
  }

  public void removeListener(IntegerColorConverterListener listener)
  {
    listeners.remove(listener);
  }
}
