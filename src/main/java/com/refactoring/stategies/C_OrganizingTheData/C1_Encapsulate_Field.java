// B3_Encapsulate_Field.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * ENCAPSULATE FIELD
 * There is a public field.
 * 
 * Make it private and provide accessors.
 * 
 * public String _name
 * 
 * Image
 * private String _name;
 * public String getName() {return _name;}
 * public void setName(String arg) {_name = arg;}
 * 
 * Motivation
 * One of the principal tenets of object orientation is encapsulation, or data
 * hiding. This says that you should never make your data public. When you make
 * data public, other objects can change and access data values without the
 * owning object’s knowing about it. This separates data from behavior.
 * 
 * This is seen as a bad thing because it reduces the modularity of the program.
 * When the data and behavior that uses it are clustered together, it is easier
 * to change the code, because the changed code is in one place rather than
 * scattered all over the program.
 * 
 * Encapsulate Field begins the process by hiding the data and adding accessors.
 * But this is only the first step. A class with only accessors is a dumb class
 * that doesn’t really take advantage of the opportunities of objects, and an
 * object is terrible thing to waste. Once I’ve done Encapsulate Field (206) I
 * look for methods that use the new methods to see whether they fancy packing
 * their bags and moving to the new object with a quick Move Method (142).
 * 
 * Mechanics
 * • Create getting and setting methods for the field.
 * 
 * • Find all clients outside the class that reference the field. If the client
 * uses the value, replace the reference with a call to the getting method. If
 * the client changes the value, replace the reference with a call to the
 * setting method.
 * 
 * Image If the field is an object and the client invokes a modifier on the
 * object, that is a use. Only use the setting method to replace an assignment.
 * 
 * • Compile and test after each change.
 * 
 * • Once all clients are changed, declare the field as private.
 * 
 * • Compile and test.
 */
public class C1_Encapsulate_Field
{

}
