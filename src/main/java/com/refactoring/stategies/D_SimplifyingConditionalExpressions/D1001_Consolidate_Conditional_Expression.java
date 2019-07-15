// D1001_Consolidate_Conditional_Expression.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.D_SimplifyingConditionalExpressions;

/**
 * You have a sequence of conditional tests with the same result.
 * 
 * Combine them into a single conditional expression and extract it.
 * 
 * double disabilityAmount() {
 * if (_seniority < 2) return 0;
 * if (_monthsDisabled > 12) return 0;
 * if (_isPartTime) return 0;
 * // compute the disability amount
 * 
 * Image
 * double disabilityAmount() {
 * if (isNotEligableForDisability()) return 0;
 * // compute the disability amount
 * 
 * Motivation
 * Sometimes you see a series of conditional checks in which each check is
 * different yet the resulting action is the same. When you see this, you should
 * use ands and ors to consolidate them into a single conditional check with a
 * single result.
 * 
 * Consolidating the conditional code is important for two reasons. First, it
 * makes the check clearer by showing that you are really making a single check
 * that’s oring the other checks together. The sequence has the same effect, but
 * it communicates carrying out a sequence of separate checks that just happen
 * to be done together. The second reason for this refactoring is that it often
 * sets you up for Extract Method (110). Extracting a condition is one of the
 * most useful things you can do to clarify your code. It replaces a statement
 * of what you are doing with why you are doing it.
 * 
 * The reasons in favor of consolidating conditionals also point to reasons for
 * not doing it. If you think the checks are really independent and shouldn’t be
 * thought of as a single check, don’t do the refactoring. Your code already
 * communicates your intention.
 * 
 * Mechanics
 * • Check that none of the conditionals has side effects.
 * 
 * Image If there are side effects, you won’t be able to do this refactoring.
 * 
 * • Replace the string of conditionals with a single conditional statement
 * using logical operators.
 * 
 * • Compile and test.
 * 
 * • Consider using Extract Method (110) on the condition.
 * 
 * Example: Ors
 * The state of the code is along the lines of the following:
 * 
 * double disabilityAmount() {
 * if (_seniority < 2) return 0;
 * if (_monthsDisabled > 12) return 0;
 * if (_isPartTime) return 0;
 * // compute the disability amount
 * ...
 * 
 * Here we see a sequence of conditional checks that all result in the same
 * thing. With sequential code like this, the checks are the equivalent of an or
 * statement:
 * 
 * double disabilityAmount() {
 * if ((_seniority < 2) || (_monthsDisabled > 12) || (_isPartTime)) return 0;
 * // compute the disability amount
 * ...
 * 
 * Now I can look at the condition and use Extract Method (110) to communicate
 * what the condition is looking for:
 * 
 * double disabilityAmount() {
 * if (isNotEligibleForDisability()) return 0;
 * // compute the disability amount
 * ...
 * }
 * 
 * boolean isNotEligibleForDisability() {
 * return ((_seniority < 2) || (_monthsDisabled > 12) || (_isPartTime));
 * }
 * 
 * Example: Ands
 * That example showed ors, but I can do the same with ands. Here the set up is
 * something like the following:
 * 
 * if (onVacation())
 * if (lengthOfService() > 10)
 * return 1;
 * return 0.5;
 * 
 * This would be changed to
 * 
 * if (onVacation() && lengthOfService() > 10) return 1;
 * else return 0.5;
 * 
 * You may well find you get a combination of these that yields an expression
 * with ands, ors, and nots. In these cases the conditions may be messy, so I
 * try to use Extract Method (110) on parts of the expression to make it
 * simpler.
 * 
 * If the routine I’m looking at tests only the condition and returns a value, I
 * can turn the routine into a single return statement using the ternary
 * operator. So
 * 
 * if (onVacation() && lengthOfService() > 10) return 1;
 * else return 0.5;
 * 
 * becomes
 * 
 * return (onVacation() && lengthOfService() > 10) ? 1 : 0.5;
 */
public class D1001_Consolidate_Conditional_Expression
{

}
