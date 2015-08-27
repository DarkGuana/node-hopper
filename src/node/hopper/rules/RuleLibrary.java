package node.hopper.rules;

/**
 * A RuleLibrary is the exposure point for an implementation of the rules system.  It is responsible for serving up
 * implementations of the requested Conditionals and Rules to classes that request them, without exposing
 * implementation details.
 * Created by Dark Guana on 2014-03-27.
 */
public interface RuleLibrary
{
  PrioritizedConditionalRule getNewPCRule();

  ConditionalRule combine(Conditional conditional, Rule rule);

  Conditional combine(Conditional... conditions);

  Conditional getLessThanTarget();

  Conditional getMoreThanTarget();

  Conditional getMoreThan(int check);

  Conditional getDividableBy(int div);

  Rule getMultiply(int mod);

  Rule getDivide(int mod);

  Rule getAdd(int mod);

  Rule getSubtract(int mod);
}
