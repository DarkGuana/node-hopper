package node.hopper.graph.viewer.color;

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
public class IntegerColorLibrary
{
  private Color unevaluated = Color.DARK_GRAY;
  private Color nonTerminating = Color.BLACK;

  private IntegerColorConverter converter = new CyclicRainbowConverter();

  private Integer minExpectedValue = 0;
  private Integer maxValue = 0;

  private final Map<Integer, Color> usedColors = new HashMap<Integer, Color>();
  private final Set<IntegerColorLibraryListener> listeners = new HashSet<IntegerColorLibraryListener>();

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
    Color fresh = converter.getColor(value);

    usedColors.put(value, fresh);

    if (value > maxValue)
    {
      maxValue = value;
      for(IntegerColorLibraryListener listener : listeners)
        listener.maxValueChanged(value, this);
    }

    return fresh;
  }

  public Integer getMaxValue()
  {
    return maxValue;
  }

  public void addListener(IntegerColorLibraryListener listener)
  {
    listeners.add(listener);
  }

  public void removeListener(IntegerColorLibraryListener listener)
  {
    listeners.remove(listener);
  }

  public Color getNonterminatingColor()
  {
    return nonTerminating;
  }

  public void setNonterminatingColor(Color nonterminatingColor)
  {
    this.nonTerminating = nonterminatingColor;
    for (IntegerColorLibraryListener listener : listeners)
    {
      listener.nonterminatingColorChanged(nonTerminating, this);
    }
  }

  public void setConverter(IntegerColorConverter converter)
  {
    this.converter = converter;
    usedColors.clear();

    for (IntegerColorLibraryListener listener : listeners)
    {
      listener.converterChanged(converter, this);
    }
  }
}
