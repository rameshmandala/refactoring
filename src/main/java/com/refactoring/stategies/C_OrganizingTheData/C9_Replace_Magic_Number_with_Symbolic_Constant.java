// C9_Replace_Magic_Number_with_Symbolic_Constant.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * You have a literal number with a particular meaning.
 * 
 * Create a constant, name it after the meaning, and replace the number with it.
 * 
 * double potentialEnergy(double mass, double height) {
 * return mass * 9.81 * height;
 * }
 * 
 * Image
 * double potentialEnergy(double mass, double height) {
 * return mass * GRAVITATIONAL_CONSTANT * height;
 * }
 * static final double GRAVITATIONAL_CONSTANT = 9.81;
 * 
 * Motivation
 * Magic numbers are one of oldest ills in computing. They are numbers with
 * special values that usually are not obvious. Magic numbers are really nasty
 * when you need to reference the same logical number in more than one place. If
 * the numbers might ever change, making the change is a nightmare. Even if you
 * don’t make a change, you have the difficulty of figuring out what is going
 * on.
 * 
 * Many languages allow you to declare a constant. There is no cost in
 * performance and there is a great improvement in readability.
 * 
 * Before you do this refactoring, you should always look for an alternative.
 * Look at how the magic number is used. Often you can find a better way to use
 * it. If the magic number is a type code, consider Replace Type Code with Class
 * (218). If the magic number is the length of an array, use anArray.length
 * instead when you are looping through the array.
 * 
 * Mechanics
 * • Declare a constant and set it to the value of the magic number.
 * 
 * • Find all occurrences of the magic number.
 * 
 * • See whether the magic number matches the usage of the constant; if it does,
 * change the magic number to use the constant.
 * 
 * • Compile.
 * 
 * • When all magic numbers are changed, compile and test. At this point all
 * should work as if nothing has been changed.
 * 
 * Image A good test is to see whether you can change the constant easily. This
 * may mean altering some expected results to match the new value. This isn’t
 * always possible, but it is a good trick when it works.
 */
public class C9_Replace_Magic_Number_with_Symbolic_Constant
{

}
