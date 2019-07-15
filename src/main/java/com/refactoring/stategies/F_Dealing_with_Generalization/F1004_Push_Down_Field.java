// F1004_Push_Down_Field.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * A field is used only by some subclasses.
 * 
 * Move the field to those subclasses.
 * 
 * Image
 * Motivation
 * Push Down Field (329) is the opposite of Pull Up Field (320). Use it when you
 * don’t need a field in the superclass but only in a subclass.
 * 
 * Mechanics
 * • Declare the field in all subclasses.
 * 
 * • Remove the field from the superclass.
 * 
 * • Compile and test.
 * 
 * • Remove the field from all subclasses that don’t need it.
 * 
 * • Compile and test.
 */
public class F1004_Push_Down_Field
{

}
