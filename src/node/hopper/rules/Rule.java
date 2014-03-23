package node.hopper.rules;

/**
 * Created by Dark Guana on 2014-03-22.
 */
public interface Rule
{
  public Integer getNextValue(Integer current, Integer target);

  public String getDescription();
}
