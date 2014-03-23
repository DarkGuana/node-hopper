package node.hopper.rules;

/**
 * Created by Dark Guana on 2014-03-22.
 */
public interface Conditional
{
  public boolean isApplicable(Integer current, Integer target);

  public String getDescription();
}
