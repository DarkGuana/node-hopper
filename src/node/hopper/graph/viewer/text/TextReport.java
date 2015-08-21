package node.hopper.graph.viewer.text;

import node.hopper.graph.IntegerAggregate;
import node.hopper.graph.IntegerAggregation;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Viewer implementation that spits out the hops to text.
 */
public class TextReport
{
  private IntegerAggregation dataSource;
  private int                maximumStringLength;

  public TextReport(IntegerAggregation dataSource)
  {
    this.dataSource = dataSource;
  }

  private String getStringValue(Integer start, Integer end)
  {
    IntegerAggregate aggregate = dataSource.getAggregate(start, end);
    if(aggregate == null)
      return "*";
    else if (aggregate.isNonterminating())
      return "#";
    else
      return String.valueOf(aggregate.getValue().intValue());
  }

  public void reportToStdOut()
  {
    int maxLength = getMaximumStringLength();
    StringBuilder reporter = new StringBuilder();

    reporter.append(padTo("", maxLength));
    for (int x = 0; x < dataSource.getMaxTargetNode(); x++)
    {
      reporter.append(padTo(Integer.toString(x), maxLength));
    }
    reporter.append('\n');
    for (int y = 0; y < dataSource.getMaxStartNode(); y++)
    {
      reporter.append(padTo(Integer.toString(y), maxLength));
      for (int x = 0; x < dataSource.getMaxTargetNode(); x++)
      {
        String displayValue = getStringValue(y, x);
        reporter.append(padTo(displayValue, maxLength));
      }
      reporter.append('\n');
    }

    System.out.println(reporter.toString());
  }

  private String padTo(String padee, int length)
  {
    if (padee.length() >= length)
      return padee;
    StringBuilder builder = new StringBuilder();
    for (int i = padee.length(); i < length; i++)
    {
      builder.append(" ");
    }
    builder.append(padee);
    return builder.toString();
  }

  public int getMaximumStringLength()
  {
    int maxLength = 1;

    // Check values
    for (int y = 0; y < dataSource.getMaxStartNode(); y++)
    {
      for (int x = 0; x < dataSource.getMaxTargetNode(); x++)
      {
        String displayValue = getStringValue(x, y);
        if(displayValue.length() > maxLength)
          maxLength = displayValue.length();
      }
    }

    // Check positions
    if(dataSource.getMaxStartNode().toString().length() > maxLength)
      maxLength = dataSource.getMaxStartNode().toString().length();
    if(dataSource.getMaxTargetNode().toString().length() > maxLength)
      maxLength = dataSource.getMaxTargetNode().toString().length();

    // Add one to ensure there is at least one space between entries
    maxLength++;

    return maxLength;
  }
}
