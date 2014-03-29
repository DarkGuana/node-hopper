package node.hopper.rules;

/**
 * Created by Dark Guana on 2014-03-29.
 */
public interface PrioritizedConditionalRule extends ConditionalRule
{
  void addNewConditional(ConditionalRule rule);
}
