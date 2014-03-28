package node.hopper.rules;

/**
 * Created by Dark Guana on 2014-03-27.
 */
public interface RuleLibrary
{
  Conditional getLessThan();

  Conditional getMoreThan();

  Conditional getMoreThan(int check);

  Conditional getDividableBy(int div);

  Rule getMultiply(int mod);

  Rule getDivide(int mod);

  Rule getSubtract(int mod);
}
