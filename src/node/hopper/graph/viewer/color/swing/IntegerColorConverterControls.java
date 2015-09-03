package node.hopper.graph.viewer.color.swing;

import node.hopper.graph.viewer.color.IntegerColorLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * TODO (clm): DOCUMENT ME!!!
 */
public class IntegerColorConverterControls extends JPanel
{
  private static final Color[] DEFAULT_COLORS = {Color.BLACK, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY,
      Color.WHITE, Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE,
      Color.MAGENTA};

  private final IntegerColorLibrary converter;
  private JPanel nonterminatingColorControl;
  private JComboBox<Color> colorDropdown;
  private Component colorStrategyControl;

  public IntegerColorConverterControls(IntegerColorLibrary converter)
  {
    this.converter = converter;

    buildLayout();
  }

  private void buildLayout()
  {
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Color Controls"));
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    add(getNonterminatingColorControl());
    add(getColorStrategyControl());
  }

  public JPanel getNonterminatingColorControl()
  {
    if (nonterminatingColorControl == null)
    {
      nonterminatingColorControl = new JPanel();
      nonterminatingColorControl.add(new JLabel("Nonterminating: "));
      nonterminatingColorControl.add(getColorDropdown());
    }
    return nonterminatingColorControl;
  }

  public Component getColorDropdown()
  {
    if (colorDropdown == null)
    {
      colorDropdown = new JComboBox<Color>();
      colorDropdown.setRenderer(new ListCellRenderer<Color>()
      {
        @Override
        public Component getListCellRendererComponent(JList<? extends Color> list, final Color value, int index,
            boolean isSelected, boolean cellHasFocus)
        {
          JLabel label = new JLabel(value.getRed() + ", " + value.getGreen() + ", " + value.getBlue());
          label.setIconTextGap(10);
          label.setIcon(new Icon()
          {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y)
            {
              Graphics2D paint = (Graphics2D) g.create();
              paint.setColor(value);
              paint.fillRect(2, 2, getIconWidth() - 4, getIconHeight() - 4);
            }

            @Override
            public int getIconWidth()
            {
              return 16;
            }

            @Override
            public int getIconHeight()
            {
              return 16;
            }
          });
          return label;
        }
      });

      for (Color color : DEFAULT_COLORS)
      {
        ((DefaultComboBoxModel<Color>) colorDropdown.getModel()).addElement(color);
      }

      colorDropdown.setSelectedItem(converter.getNonterminatingColor());

      colorDropdown.addItemListener(new ItemListener()
      {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
          converter.setNonterminatingColor((Color) e.getItem());
        }
      });
    }
    return colorDropdown;
  }

  public Component getColorStrategyControl()
  {
    if(colorStrategyControl == null)
      colorStrategyControl = new JLabel("ColorStrategy");
    return colorStrategyControl;
  }
}
