package node.hopper.graph.viewer.color;

import java.awt.*;

/**
 * TODO: Comment this
 * Created by Dark Guana on 2014-04-13.
 */
public interface IntegerColorLibraryListener
{
  public void maxValueChanged(Integer maxValue, IntegerColorLibrary source);

  public void nonterminatingColorChanged(Color newColor, IntegerColorLibrary source);
}
