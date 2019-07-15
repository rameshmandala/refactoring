// D1007_Introduce_Assertion.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.D_SimplifyingConditionalExpressions;

/**
 * A section of code assumes something about the state of the program.
 * 
 * Make the assumption explicit with an assertion.
 * 
 * double getExpenseLimit() {
 * // should have either expense limit or a primary project
 * return (_expenseLimit != NULL_EXPENSE) ?
 * _expenseLimit:
 * _primaryProject.getMemberExpenseLimit();
 * }
 * 
 * Image
 * double getExpenseLimit() {
 * Assert.isTrue (_expenseLimit != NULL_EXPENSE || _primaryProject != null);
 * return (_expenseLimit != NULL_EXPENSE) ?
 * _expenseLimit:
 * _primaryProject.getMemberExpenseLimit();
 * }
 * 
 * Motivation
 * Often sections of code work only if certain conditions are true. This may be
 * as simple as a square root calculation’s working only on a positive input
 * value. With an object it may be assumed that at least one of a group of
 * fields has a value in it.
 * 
 * Such assumptions often are not stated but can only be decoded by looking
 * through an algorithm. Sometimes the assumptions are stated with a comment. A
 * better technique is to make the assumption explicit by writing an assertion.
 * 
 * An assertion is a conditional statement that is assumed to be always true.
 * Failure of an assertion indicates programmer error. As such, assertion
 * failures should always result in unchecked exceptions. Assertions should
 * never be used by other parts of the system. Indeed assertions usually are
 * removed for production code. It is therefore important to signal something is
 * an assertion.
 * 
 * Assertions act as communication and debugging aids. In communication they
 * help the reader understand the assumptions the code is making. In debugging,
 * assertions can help catch bugs closer to their origin. I’ve noticed the
 * debugging help is less important when I write self-testing code, but I still
 * appreciate the value of assertions in communciation.
 * 
 * Mechanics
 * Because assertions should not affect the running of a system, adding one is
 * always behavior preserving.
 * 
 * • When you see that a condition is assumed to be true, add an assertion to
 * state it.
 * 
 * Image Have an assert class that you can use for assertion behavior.
 * 
 * Beware of overusing assertions. Don’t use assertions to check everything that
 * you think is true for a section of code. Use assertions only to check things
 * that need to be true. Overusing assertions can lead to duplicate logic that
 * is awkward to maintain. Logic that covers an assumption is good because it
 * forces you to rethink the section of the code. If the code works without the
 * assertion, the assertion is confusing rather than helpful and may hinder
 * modification in the future.
 * 
 * Always ask whether the code still works if an assertion fails. If the code
 * does work, remove the assertion.
 * 
 * Beware of duplicate code in assertions. Duplicate code smells just as bad in
 * assertion checks as it does anywhere else. Use Extract Method (110) liberally
 * to get rid of the duplication.
 * 
 * Example
 * Here’s a simple tale of expense limits. Employees can be given an individual
 * expense limit. If they are assigned a primary project, they can use the
 * expense limit of that primary project. They don’t have to have an expense
 * limit or a primary project, but they must have one or the other. This
 * assumption is taken for granted in the code that uses expense limits:
 * 
 * class Employee...
 * private static final double NULL_EXPENSE = -1.0;
 * private double _expenseLimit = NULL_EXPENSE;
 * private Project _primaryProject;
 * double getExpenseLimit() {
 * return (_expenseLimit != NULL_EXPENSE) ?
 * _expenseLimit:
 * _primaryProject.getMemberExpenseLimit();
 * }
 * 
 * boolean withinLimit (double expenseAmount) {
 * return (expenseAmount <= getExpenseLimit());
 * }
 * 
 * This code contains an implicit assumption that the employee has either a
 * project or a personal expense limit. Such an assertion should be clearly
 * stated in the code:
 * 
 * double getExpenseLimit() {
 * Assert.isTrue (_expenseLimit != NULL_EXPENSE || _primaryProject != null);
 * return (_expenseLimit != NULL_EXPENSE) ?
 * _expenseLimit:
 * _primaryProject.getMemberExpenseLimit();
 * }
 * 
 * This assertion does not change any aspect of the behavior of the program.
 * Either way, if the condition is not true, I get a runtime exception: either a
 * null pointer exception in withinLimit or a runtime exception inside
 * Assert.isTrue. In some circumstances the assertion helps find the bug,
 * because it is closer to where things went wrong. Mostly, however, the
 * assertion helps to communicate how the code works and what it assumes.
 * 
 * I often find I use Extract Method (110) on the conditional inside the
 * assertion. I either use it in several places and eliminate duplicate code or
 * use it simply to clarify the intention of the condition.
 * 
 * One of the complications of assertions in Java is that there is no simple
 * mechanism to putting them in. Assertions should be easily removable, so they
 * don’t affect performance in production code. Having a utility class, such as
 * Assert, certainly helps. Sadly, any expression inside the assertion
 * parameters executes whatever happens. The only way to stop that is to use
 * code like:
 * 
 * double getExpenseLimit() {
 * Assert.isTrue (Assert.ON &&
 * (_expenseLimit != NULL_EXPENSE || _primaryProject != null));
 * return (_expenseLimit != NULL_EXPENSE) ?
 * _expenseLimit:
 * _primaryProject.getMemberExpenseLimit();
 * }
 * 
 * or
 * 
 * double getExpenseLimit() {
 * if (Assert.ON)
 * Assert.isTrue (_expenseLimit != NULL_EXPENSE || _primaryProject != null);
 * return (_expenseLimit != NULL_EXPENSE) ?
 * _expenseLimit:
 * _primaryProject.getMemberExpenseLimit();
 * }
 * 
 * If Assert.ON is a constant, the compiler should detect and eliminate the dead
 * code if it is false. Adding the clause is messy, however, so many programmers
 * prefer the simpler use of Assert and then use a filter to remove any line
 * that uses assert at production time (using perl or the like).
 * 
 * The Assert class should have various methods that are named helpfully. In
 * addition to isTrue, you can have equals, and shouldNeverReachHere.
 */
public class D1007_Introduce_Assertion
{

}
