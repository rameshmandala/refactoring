// F1007_Collapse_Hierarchy.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * A superclass and subclass are not very different.
 * 
 * Merge them together.
 * 
 * Image
 * Motivation
 * If you have been working for a while with a class hierarchy, it can easily
 * become too tangled for its own good. Refactoring the hierarchy often involves
 * pushing methods and fields up and down the hierarchy. After you’ve done this
 * you can well find you have a subclass that isn’t adding any value, so you
 * need to merge the classes together.
 * 
 * Mechanics
 * • Choose which class is going to be removed: the superclass or the
 * subclasses.
 * 
 * • Use Pull Up Field (320) and Pull Up Method (322) or Push Down Method (328)
 * and Push Down Field (329) to move all the behavior and data of the removed
 * class to the class with which it is being merged.
 * 
 * • Compile and test with each move.
 * 
 * • Adjust references to the class that will be removed to use the merged
 * class. This will affect variable declarations, parameter types, and
 * constructors.
 * 
 * • Remove the empty class.
 * 
 * • Compile and test.
 */
public class F1007_Collapse_Hierarchy
{

}
