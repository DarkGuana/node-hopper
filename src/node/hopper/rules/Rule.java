package node.hopper.rules;

/**
 * A Rule is used to figure out the next value in a node chain from the current Integer to the target Integer.
 * Many Rules do not actually need the target value to determine the next value, but some do.
 * For example, if the Rule was to divide by two, and current = 8, the next value would be 4.
 * If the Rule were to divide by two if more than the target, current = 8, and target = 10 the next value would be null.
 * Created by Dark Guana on 2014-03-22.
 */
public interface Rule
{
  public Integer getNextValue(Integer current, Integer target);

  public String getDescription();
}
