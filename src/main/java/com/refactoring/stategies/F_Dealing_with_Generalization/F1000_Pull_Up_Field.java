// F1000_Pull_Up_Field.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * Two subclasses have the same field.
 * 
 * Move the field to the superclass.
 * 
 * Image
 * Motivation
 * If subclasses are developed independently, or combined through refactoring,
 * you often find that they duplicate features. In particular, certain fields
 * can be duplicates. Such fields sometimes have similar names but not always.
 * The only way to determine what is going on is to look at the fields and see
 * how they are used by other methods. If they are being used in a similar way,
 * you can generalize them.
 * 
 * Doing this reduces duplication in two ways. It removes the duplicate data
 * declaration and allows you to move from the subclasses to the superclass
 * behavior that uses the field.
 * 
 * Mechanics
 * • Inspect all uses of the candidate fields to ensure they are used in the
 * same way.
 * 
 * • If the fields do not have the same name, rename the fields so that they
 * have the name you want to use for the superclass field.
 * 
 * • Compile and test.
 * 
 * • Create a new field in the superclass.
 * 
 * Image If the fields are private, you will need to protect the superclass
 * field so that the subclasses can refer to it.
 * 
 * • Delete the subclass fields.
 * 
 * • Compile and test.
 * 
 * • Consider using Self Encapsulate Field (171) on the new field.
 */
public class F1000_Pull_Up_Field
{

}
