package node.hopper.rules;

/**
 * A ConditionalRule is a convenience class representing a Conditional activated Rule.  In general, this will usually
 * return a null, or other value that indicates the Rule wasn't activated.
 * Created by Dark Guana on 2014-03-22.
 */
public interface ConditionalRule extends Rule, Conditional
{
  public Rule getRule();

  public Conditional getConditional();
}
