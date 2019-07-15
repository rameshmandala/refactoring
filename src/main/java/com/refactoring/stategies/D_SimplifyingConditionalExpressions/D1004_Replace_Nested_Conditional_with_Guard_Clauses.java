// D1004_Replace_Nested_Conditional_with_Guard_Clauses.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.D_SimplifyingConditionalExpressions;

/**
 * A method has conditional behavior that does not make clear the normal path of
 * execution.
 * 
 * Use guard clauses for all the special cases.
 * 
 * double getPayAmount() {
 * double result;
 * if (_isDead) result = deadAmount();
 * else {
 * if (_isSeparated) result = separatedAmount();
 * else {
 * if (_isRetired) result = retiredAmount();
 * else result = normalPayAmount();
 * };
 * }
 * return result;
 * };
 * 
 * Image
 * double getPayAmount() {
 * if (_isDead) return deadAmount();
 * if (_isSeparated) return separatedAmount();
 * if (_isRetired) return retiredAmount();
 * return normalPayAmount();
 * };
 * 
 * Motivation
 * I often find that conditional expressions come in two forms. The first form
 * is a check whether either course is part of the normal behavior. The second
 * form is a situation in which one answer from the conditional indicates normal
 * behavior and the other indicates an unusual condition.
 * 
 * These kinds of conditionals have different intentions, and these intentions
 * should come through in the code. If both are part of normal behavior, use a
 * condition with an if and an else leg. If the condition is an unusual
 * condition, check the condition and return if the condition is true. This kind
 * of check is often called a guard clause [Beck].
 * 
 * The key point about Replace Nested Conditional with Guard Clauses is one of
 * emphasis. If you are using an if-then-else construct you are giving equal
 * weight to the if leg and the else leg. This communicates to the reader that
 * the legs are equally likely and important. Instead the guard clause says,
 * “This is rare, and if it happens, do something and get out.”
 * 
 * I often find I use Replace Nested Conditional with Guard Clauses when I’m
 * working with a programmer who has been taught to have only one entry point
 * and one exit point from a method. One entry point is enforced by modern
 * languages, and one exit point is really not a useful rule. Clarity is the key
 * principle: if the method is clearer with one exit point, use one exit point;
 * otherwise don’t.
 * 
 * Mechanics
 * • For each check put in the guard clause.
 * 
 * Image The guard clause either returns, or throws an exception.
 * 
 * • Compile and test after each check is replaced with a guard clause.
 * 
 * Image If all guard clauses yield the same result, use Consolidate Conditional
 * Expressions.
 * 
 * Example
 * Imagine a run of a payroll system in which you have special rules for dead,
 * separated, and retired employees. Such cases are unusual, but they do happen
 * from time to time.
 * 
 * If I see the code like this
 * 
 * double getPayAmount() {
 * double result;
 * if (_isDead) result = deadAmount();
 * else {
 * if (_isSeparated) result = separatedAmount();
 * else {
 * if (_isRetired) result = retiredAmount();
 * else result = normalPayAmount();
 * };
 * }
 * return result;
 * };
 * 
 * Then the checking is masking the normal course of action behind the checking.
 * So instead it is clearer to use guard clauses. I can introduce these one at a
 * time. I like to start at the top:
 * 
 * double getPayAmount() {
 * double result;
 * if (_isDead) return deadAmount();
 * if (_isSeparated) result = separatedAmount();
 * else {
 * if (_isRetired) result = retiredAmount();
 * else result = normalPayAmount();
 * };
 * return result;
 * };
 * 
 * I continue one at a time:
 * 
 * double getPayAmount() {
 * double result;
 * if (_isDead) return deadAmount();
 * if (_isSeparated) return separatedAmount();
 * if (_isRetired) result = retiredAmount();
 * else result = normalPayAmount();
 * return result;
 * };
 * 
 * and then
 * 
 * double getPayAmount() {
 * double result;
 * if (_isDead) return deadAmount();
 * if (_isSeparated) return separatedAmount();
 * if (_isRetired) return retiredAmount();
 * result = normalPayAmount();
 * return result;
 * };
 * 
 * By this point the result temp isn’t pulling its weight so I nuke it:
 * 
 * double getPayAmount() {
 * if (_isDead) return deadAmount();
 * if (_isSeparated) return separatedAmount();
 * if (_isRetired) return retiredAmount();
 * return normalPayAmount();
 * };
 * 
 * Nested conditional code often is written by programmers who are taught to
 * have one exit point from a method. I’ve found that is a too simplistic rule.
 * When I have no further interest in a method, I signal my lack of interest by
 * getting out. Directing the reader to look at an empty else block only gets in
 * the way of comprehension.
 * 
 * Example: Reversing the Conditions
 * In reviewing the manuscript of this book, Joshua Kerievsky pointed out that
 * you often do Replace Nested Conditional with Guard Clauses by reversing the
 * conditional expressions. He kindly came up with an example to save further
 * taxing of my imagination:
 * 
 * public double getAdjustedCapital() {
 * double result = 0.0;
 * if (_capital > 0.0) {
 * if (_intRate > 0.0 && _duration > 0.0) {
 * result = (_income / _duration) * ADJ_FACTOR;
 * }
 * }
 * return result;
 * }
 * 
 * Again I make the replacements one at a time, but this time I reverse the
 * conditional as I put in the guard clause:
 * 
 * public double getAdjustedCapital() {
 * double result = 0.0;
 * if (_capital <= 0.0) return result;
 * if (_intRate > 0.0 && _duration > 0.0) {
 * result = (_income / _duration) * ADJ_FACTOR;
 * }
 * return result;
 * }
 * 
 * Because the next conditional is a bit more complicated, I can reverse it in
 * two steps. First I add a not:
 * 
 * public double getAdjustedCapital() {
 * double result = 0.0;
 * if (_capital <= 0.0) return result;
 * if (!(_intRate > 0.0 && _duration > 0.0)) return result;
 * result = (_income / _duration) * ADJ_FACTOR;
 * return result;
 * }
 * 
 * Leaving nots in a conditional like that twists my mind around at a painful
 * angle, so I simplify it as follows:
 * 
 * public double getAdjustedCapital() {
 * double result = 0.0;
 * if (_capital <= 0.0) return result;
 * if (_intRate <= 0.0 || _duration <= 0.0) return result;
 * result = (_income / _duration) * ADJ_FACTOR;
 * return result;
 * }
 * 
 * In these situations I prefer to put an explicit value on the returns from the
 * guards. That way you can easily see the result of the guard’s failing (I
 * would also consider Replace Magic Number with Symbolic Constant [204] here).
 * 
 * public double getAdjustedCapital() {
 * double result = 0.0;
 * if (_capital <= 0.0) return 0.0;
 * if (_intRate <= 0.0 || _duration <= 0.0) return 0.0;
 * result = (_income / _duration) * ADJ_FACTOR;
 * return result;
 * }
 * 
 * With that done I can also remove the temp:
 * 
 * public double getAdjustedCapital() {
 * if (_capital <= 0.0) return 0.0;
 * if (_intRate <= 0.0 || _duration <= 0.0) return 0.0;
 * return (_income / _duration) * ADJ_FACTOR;
 * }
 */
public class D1004_Replace_Nested_Conditional_with_Guard_Clauses
{

}
