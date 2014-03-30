package node.hopper.rules;

/**
 * A PrioritizedConditionalRule is a collection of other ConditionalRules that will be iterated through in the order
 * they were added.  The container Rule will return the value created by the first rule that reports itself as
 * applicable.
 * Created by Dark Guana on 2014-03-29.
 */
public interface PrioritizedConditionalRule extends ConditionalRule
{
  void addNewConditional(ConditionalRule rule);
}