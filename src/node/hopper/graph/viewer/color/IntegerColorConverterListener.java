package node.hopper.graph.viewer.color;

import java.awt.*;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-04-13.
 */
public interface IntegerColorConverterListener
{
  public void maxValueChanged(Integer maxValue, IntegerColorConverter source);

  public void nonterminatingColorChanged(Color newColor, IntegerColorConverter source);
}
