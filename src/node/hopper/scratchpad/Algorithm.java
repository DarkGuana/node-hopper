package node.hopper.scratchpad;

import node.hopper.rules.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dark Guana on 2014-03-22.
 */
public class Algorithm
{
  public static void main(String[] args)
  {
    PrioritizedConditionalRuleList rule = new PrioritizedConditionalRuleList();
    rule.addNewConditional(new SimpleConditionalRule(RuleLibrary.getLessThan(), RuleLibrary.getMultiply(10)));
    rule.addNewConditional(new SimpleConditionalRule(new MultipleConditional(RuleLibrary.getDividableBy(2), RuleLibrary.getMoreThan()), RuleLibrary.getDivide(2)));
    rule.addNewConditional(new SimpleConditionalRule(RuleLibrary.getMoreThan(0), RuleLibrary.getSubtract(1)));

    Integer current = 3;
    Integer target = 90;
    Set<Integer> history = new HashSet<Integer>();
    for (int i = 0 ; i < 100 && !current.equals(target) && current != null; i++)
    {
      history.add(current);
      Integer next = rule.getNextValue(current, target);
      System.out.println(current + " -> " + next);
      current = next;
      if (history.contains(next))
      {
        System.out.println("Loop detected");
        break;
      }
    }
  }
}
