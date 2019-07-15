// E1103_Replace_Error_Code_with_Exception.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * A method returns a special code to indicate an error.
 * 
 * Throw an exception instead.
 * 
 * int withdraw(int amount) {
 * if (amount > _balance)
 * return -1;
 * else {
 * _balance -= amount;
 * return 0;
 * }
 * }
 * 
 * Image
 * void withdraw(int amount) throws BalanceException {
 * if (amount > _balance) throw new BalanceException();
 * _balance -= amount;
 * }
 * 
 * Motivation
 * In computers, as in life, things go wrong occasionally. When things go wrong,
 * you need to do something about it. In the simplest case, you can stop the
 * program with an error code. This is the software equivalent of committing
 * suicide because you miss a flight. (If I did that I wouldn’t be alive even if
 * I were a cat.) Despite my glib attempt at humor, there is merit to the
 * software suicide option. If the cost of a program crash is small and the user
 * is tolerant, stopping the program is fine. However, more important programs
 * need more important measures.
 * 
 * The problem is that the part of a program that spots an error isn’t always
 * the part that can figure out what to do about it. When such a routine finds
 * an error, it needs to let its caller know, and the caller may pass the error
 * up the chain. In many languages a special output is used to indicate error.
 * Unix and C-based systems traditionally use a return code to signal success or
 * failure of a routine.
 * 
 * Java has a better way: exceptions. Exceptions are better because they clearly
 * separate normal processing from error processing. This makes programs easier
 * to understand, and as I hope you now believe, understandability is next to
 * godliness.
 * 
 * Mechanics
 * • Decide whether the exception should be checked or unchecked.
 * 
 * Image If the caller is responsible for testing the condition before calling,
 * make the exception unchecked.
 * 
 * Image If the exception is checked, either create a new exception or use an
 * existing one.
 * 
 * • Find all the callers and adjust them to use the exception.
 * 
 * Image If the exception is unchecked, adjust the callers to make the
 * appropriate check before calling the method. Compile and test after each such
 * change.
 * 
 * Image If the exception us checked, adjust the callers to call the method in a
 * try block.
 * 
 * • Change the signature of the method to reflect the new usage.
 * 
 * If you have many callers, this can be too big a change. You can make it more
 * gradual with the following steps:
 * 
 * • Decide whether the exception should be checked or unchecked.
 * 
 * • Create a new method that uses the exception.
 * 
 * • Modify the body of the old method to call the new method.
 * 
 * • Compile and test.
 * 
 * • Adjust each caller of the old method to call the new method. Compile and
 * test after each change.
 * 
 * • Delete the old method.
 * 
 * Example
 * Isn’t it strange that computer textbooks often assume you can’t withdraw more
 * than your balance from an account, although in real life you often can?
 * 
 * class Account...
 * int withdraw(int amount) {
 * if (amount > _balance)
 * return -1;
 * else {
 * _balance -= amount;
 * return 0;
 * }
 * }
 * private int _balance;
 * 
 * To change this code to use an exception I first need to decide whether to use
 * a checked or unchecked exception. The decision hinges on whether it is the
 * responsibility of the caller to test the balance before withdrawing or
 * whether it is the responsibility of the withdraw routine to make the check.
 * If testing the balance is the caller’s responsibility, it is a programming
 * error to call withdraw with an amount greater than the balance. Because it is
 * a programming error that is, a bug I should use an unchecked exception. If
 * testing the balance is the withdraw routine’s responsibility, I must declare
 * the exception in the interface. That way I signal the caller to expect the
 * exception and to take appropriate measures.
 * 
 * Example: Unchecked Exception
 * Let’s take the unchecked case first. Here I expect the caller to do the
 * checking. First I look at the callers. In this case nobody should be using
 * the return code because it is a programmer error to do so. If I see code such
 * as
 * 
 * if (account.withdraw(amount) == -1)
 * handleOverdrawn();
 * else doTheUsualThing();
 * 
 * I need to replace it with code such as
 * 
 * if (!account.canWithdraw(amount))
 * handleOverdrawn();
 * else {
 * account.withdraw(amount);
 * doTheUsualThing();
 * }
 * 
 * I can compile and test after each change.
 * 
 * Now I need to remove the error code and throw an exception for the error
 * case. Because the behavior is (by definition) exceptional, I should use a
 * guard clause for the condition check:
 * 
 * void withdraw(int amount) {
 * if (amount > _balance)
 * throw new IllegalArgumentException ("Amount too large");
 * _balance -= amount;
 * }
 * 
 * Because it is a programmer error, I should signal even more clearly by using
 * an assertion:
 * 
 * class Account...
 * void withdraw(int amount) {
 * Assert.isTrue ("sufficient funds", amount <= _balance);
 * _balance -= amount;
 * }
 * 
 * class Assert...
 * static void isTrue (String comment, boolean test) {
 * if (! test) {
 * throw new RuntimeException ("Assertion failed: " + comment);
 * }
 * }
 * 
 * Example: Checked Exception
 * I handle the checked exception case slightly differently. First I create (or
 * use) an appropriate new exception:
 * 
 * class BalanceException extends Exception {}
 * 
 * Then I adjust the callers to look like
 * 
 * try {
 * account.withdraw(amount);
 * doTheUsualThing();
 * } catch (BalanceException e) {
 * handleOverdrawn();
 * }
 * 
 * Now I change the withdraw method to use the exception:
 * 
 * void withdraw(int amount) throws BalanceException {
 * if (amount > _balance) throw new BalanceException();
 * _balance -= amount;
 * }
 * 
 * The awkward thing about this procedure is that I have to change all the
 * callers and the called routine in one go. Otherwise the compiler spanks us.
 * If there are a lot of callers, this can be too large a change without the
 * compile and test step.
 * 
 * For these cases I can use a temporary intermediate method. I begin with the
 * same case as before:
 * 
 * if (account.withdraw(amount) == -1)
 * handleOverdrawn();
 * else doTheUsualThing();
 * 
 * class Account ...
 * int withdraw(int amount) {
 * if (amount > _balance)
 * return -1;
 * else {
 * _balance -= amount;
 * return 0;
 * }
 * }
 * 
 * The first step is to create a new withdraw method that uses the exception:
 * 
 * void newWithdraw(int amount) throws BalanceException {
 * if (amount > _balance) throw new BalanceException();
 * _balance -= amount;
 * }
 * 
 * Next I adjust the current withdraw method to use the new one:
 * 
 * int withdraw(int amount) {
 * try {
 * newWithdraw(amount);
 * return 0;
 * } catch (BalanceException e) {
 * return -1;
 * }
 * }
 * 
 * With that done, I can compile and test. Now I can replace each of the calls
 * to the old method with a call to the new one:
 * 
 * try {
 * account.newWithdraw(amount);
 * doTheUsualThing();
 * } catch (BalanceException e) {
 * handleOverdrawn();
 * }
 * 
 * With both old and new methods in place, I can compile and test after each
 * change. When I’m finished, I can delete the old method and use Rename Method
 * (273) to give the new method the old name.
 */
public class E1103_Replace_Error_Code_with_Exception
{

}
