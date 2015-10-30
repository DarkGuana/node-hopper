package node.hopper.app;

import node.hopper.graph.DistanceGraph;
import node.hopper.graph.viewer.text.TextReport;
import node.hopper.rules.PrioritizedConditionalRule;
import node.hopper.rules.RuleLibrary;
import node.hopper.rules.simple.SimpleRuleLibrary;

import java.util.concurrent.TimeUnit;

/**
 * TODO: Comment this
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
    int width = 11;
    int length = 11;


//    PrioritizedConditionalRule rule = library.getNewPCRule();
//    rule.addNewConditional(library.combine(library.getMoreThanTarget(), library.getSubtract(1)));
//    rule.addNewConditional(library.combine(library.getLessThanTarget(), library.getMultiply(2)));
//    PrioritizedConditionalRule rule = library.getNewPCRule();
//    rule.addNewConditional(library.combine(library.getLessThanTarget(), library.getMultiply(3)));
//    rule.addNewConditional(library.combine(library.combine(library.getDividableBy(2), library.getMoreThanTarget()), library.getDivide(2)));
//    rule.addNewConditional(library.combine(library.getMoreThan(0), library.getSubtract(1)));

    PrioritizedConditionalRule rule = library.getNewPCRule();
    rule.addNewConditional(library.combine(library.getLessThanTarget(), library.getMultiply(100)));
    rule.addNewConditional(
      library.combine(library.combine(library.getDividableBy(99), library.getMoreThanTarget()), library.getDivide(99)));
    rule.addNewConditional(library.combine(library.getMoreThan(0), library.getSubtract(2)));

    DistanceGraph dg = new DistanceGraph(width, length, DistanceGraph.PopulationMethod.START_TO_FINISH, rule);
    dg.populateAllDistances();

    TextReport report = new TextReport(dg);
    report.reportToStdOut();
  }

  public static void main(String[] args)
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
