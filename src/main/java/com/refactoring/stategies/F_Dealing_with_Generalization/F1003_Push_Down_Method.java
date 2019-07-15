// F1003_Push_Down_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * Behavior on a superclass is relevant only for some of its subclasses.
 * 
 * Move it to those subclasses.
 * 
 * Image
 * Motivation
 * Push Down Method is the opposite of Pull Up Method (322). I use it when I
 * need to move behavior from a superclass to a specific subclass, usually
 * because it makes sense only there. You often do this when you use Extract
 * Subclass (330).
 * 
 * Mechanics
 * • Declare a method in all subclasses and copy the body into each subclass.
 * 
 * Image You may need to declare fields as protected for the method to access
 * them. Usually you do this if you intend to push down the field later.
 * Otherwise use an accessor on the superclass. If this accessor is not public,
 * you need to declare it as protected.
 * 
 * • Remove method from superclass.
 * 
 * Image You may have to change callers to use the subclass in variable and
 * parameter declarations.
 * 
 * Image If it makes sense to access the method through a superclass variable,
 * you don’t intend to remove the method from any subclasses, and the superclass
 * is abstract, you can declare the method as abstract, in the superclass.
 * 
 * • Compile and test.
 * 
 * • Remove the method from each subclass that does not need it.
 * 
 * • Compile and test.
 */
public class F1003_Push_Down_Method
{

}
