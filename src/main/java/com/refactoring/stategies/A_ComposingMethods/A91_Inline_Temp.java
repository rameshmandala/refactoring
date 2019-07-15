package com.refactoring.stategies.A_ComposingMethods;

public class A91_Inline_Temp
{
    /**
     * INLINE TEMP
     * You have a temp that is assigned to once with a simple expression, and
     * the temp is getting in the way of other refactorings.
     * 
     * Replace all references to that temp with the expression.
     * 
     * double basePrice = anOrder.basePrice();
     * return (basePrice > 1000)
     * 
     * Image
     * return (anOrder.basePrice() > 1000)
     * 
     * Motivation
     * Most of the time Inline Temp is used as part of Replace Temp with Query
     * (120), so the real motivation is there. The only time Inline Temp is used
     * on its own is when you find a temp that is assigned the value of a method
     * call. Often this temp isn’t doing any harm and you can safely leave it
     * there. If the temp is getting in the way of other refactorings, such as
     * Extract Method (110), it’s time to inline it.
     * 
     * Mechanics
     * • Check that the right hand side of the assignment is free of
     * side-effects.
     * 
     * • Declare the temp as final if it isn’t already, and compile.
     * 
     * Image This checks that the temp is really only assigned to once.
     * 
     * • Find all references to the temp and replace them with the right-hand
     * side of the assignment.
     * 
     * • Compile and test after each change.
     * 
     * • Remove the declaration and the assignment of the temp.
     * 
     * • Compile and test.
     */
}
