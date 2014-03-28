package node.hopper.app;

import node.hopper.graph.DistanceGraph;
import node.hopper.rules.RuleLibrary;
import node.hopper.rules.simple.MultipleConditional;
import node.hopper.rules.simple.PrioritizedConditionalRuleList;
import node.hopper.rules.simple.SimpleConditionalRule;
import node.hopper.rules.simple.SimpleRuleLibrary;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dark Guana on 2014-03-27.
 */
public class TextApplication
{
  private final RuleLibrary library;

  public TextApplication()
  {
    this.library = new SimpleRuleLibrary();
  }

  public void run()
  {
    int width = 101;
    int length = 101;

    PrioritizedConditionalRuleList rule = new PrioritizedConditionalRuleList();
    rule.addNewConditional(new SimpleConditionalRule(library.getLessThan(), library.getMultiply(3)));
    rule.addNewConditional(new SimpleConditionalRule(new MultipleConditional(library.getDividableBy(2), library.getMoreThan()), library.getDivide(2)));
    rule.addNewConditional(new SimpleConditionalRule(library.getMoreThan(0), library.getSubtract(1)));

    DistanceGraph dg = new DistanceGraph(width, length, rule);
    dg.populateAllDistances();

    StringBuilder reporter = new StringBuilder();
    for (int x = 0; x < width; x++)
    {
      reporter.append('\t').append(x);
    }
    reporter.append('\n');
    for (int y = 0; y < length; y++)
    {
      reporter.append(y);
      for (int x = 0; x < width; x++)
      {
        String displayValue = dg.getDistanceBetween(y,x).toString(); // dg.getDistanceBetween(y,x) < 0 ? "#" : " ";
        reporter.append('\t').append(displayValue);
      }
      reporter.append('\n');
    }

    System.out.println(reporter.toString());
  }

  public static void main (String[] args)
  {
    TextApplication app = new TextApplication();
    long start = System.currentTimeMillis();
    app.run();
    long end = System.currentTimeMillis();

    long timeSpent = end - start;
    long min = TimeUnit.MILLISECONDS.toMinutes(timeSpent);
    long sec = TimeUnit.MILLISECONDS.toSeconds(timeSpent) - TimeUnit.MINUTES.toSeconds(min);
    long mil = timeSpent - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(timeSpent));
    String time = String.format("%d min, %d sec, %d milli", min, sec, mil);

    System.out.println("Application completed in " + time);
  }
}
