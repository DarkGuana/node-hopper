package node.hopper.graph.viewer.color;

import java.awt.*;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public interface IntegerColorConverter
{
  public static final int RGB_MAX_VALUE = 255;

  public Color getColor(int value);
}
