package node.hopper.rules;

/**
 * A Conditional is simply a coded rule that is usually used to pass an activation condition along to another rule.
 * Created by Dark Guana on 2014-03-22.
 */
public interface Conditional
{
  public boolean isApplicable(Integer current, Integer target);

  public String getDescription();
}
