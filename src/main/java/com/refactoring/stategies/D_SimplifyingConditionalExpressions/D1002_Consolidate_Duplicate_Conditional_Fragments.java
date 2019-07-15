// D1002_Consolidate_Duplicate_Conditional_Fragments.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.D_SimplifyingConditionalExpressions;

/**
 * The same fragment of code is in all branches of a conditional expression.
 * 
 * Move it outside of the expression.
 * 
 * if (isSpecialDeal()) {
 * total = price * 0.95;
 * send();
 * }
 * else {
 * total = price * 0.98;
 * send();
 * }
 * 
 * Image
 * if (isSpecialDeal())
 * total = price * 0.95;
 * else
 * total = price * 0.98;
 * send();
 * 
 * Motivation
 * Sometimes you find the same code executed in all legs of a conditional. In
 * that case you should move the code to outside the conditional. This makes
 * clearer what varies and what stays the same.
 * 
 * Mechanics
 * • Identify code that is executed the same way regardless of the condition.
 * 
 * • If the common code is at the beginning, move it to before the conditional.
 * 
 * • If the common code is at the end, move it to after the conditional.
 * 
 * • If the common code is in the middle, look to see whether the code before or
 * after it changes anything. If it does, you can move the common code forward
 * or backward to the ends. You can then move it as described for code at the
 * end or the beginning.
 * 
 * • If there is more than a single statement, you should extract that code into
 * a method.
 * 
 * Example
 * You find this situation with code such as the following:
 * 
 * if (isSpecialDeal()) {
 * total = price * 0.95;
 * send();
 * }
 * else {
 * total = price * 0.98;
 * send();
 * }
 * 
 * Because the send method is executed in either case, I should move it out of
 * the conditional:
 * 
 * if (isSpecialDeal())
 * total = price * 0.95;
 * else
 * total = price * 0.98;
 * send();
 * 
 * The same situation can apply to exceptions. If code is repeated after an
 * exception-causing statement in the try block and all the catch blocks, I can
 * move it to the final block.
 */
public class D1002_Consolidate_Duplicate_Conditional_Fragments
{

}
