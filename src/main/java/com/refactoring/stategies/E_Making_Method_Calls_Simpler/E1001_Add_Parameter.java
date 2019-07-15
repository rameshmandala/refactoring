// E1001_Add_Parameter.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * ADD PARAMETER
 * A method needs more information from its caller.
 * 
 * Image
 * Add a parameter for an object that can pass on this information.
 * 
 * Motivation
 * Add Parameter is a very common refactoring, one that you almost certainly
 * have already done. The motivation is simple. You have to change a method, and
 * the change requires information that wasn’t passed in before, so you add a
 * parameter.
 * 
 * Actually most of what I have to say is motivation against doing this
 * refactoring. Often you have other alternatives to adding a parameter. If
 * available, these alternatives are better because they don’t lead to
 * increasing the length of parameter lists. Long parameter lists smell bad
 * because they are hard to remember and often involve data clumps.
 * 
 * Look at the existing parameters. Can you ask one of those objects for the
 * information you need? If not, would it make sense to give them a method to
 * provide that information? What are you using the information for? Should that
 * behavior be on another object, the one that has the information? Look at the
 * existing parameters and think about them with the new parameter. Perhaps you
 * should consider Introduce Parameter Object (295).
 * 
 * I’m not saying that you should never add parameters; I do it frequently, but
 * you need to be aware of the alternatives.
 * 
 * Mechanics
 * The mechanics of Add Parameter are very similar to those of Rename Method
 * (273).
 * 
 * • Check to see whether this method signature is implemented by a superclass
 * or subclass. If it is, carry out these steps for each implementation.
 * 
 * • Declare a new method with the added parameter. Copy the old body of code
 * over to the new method.
 * 
 * Image If you need to add more than one parameter, it is easier to add them at
 * the same time.
 * 
 * • Compile.
 * 
 * • Change the body of the old method so that it calls the new one.
 * 
 * Image If you only have a few references, you can reasonably skip this step.
 * 
 * Image You can supply any value for the parameter, but usually you use null
 * for object parameter and a clearly odd value for built-in types. It’s often a
 * good idea to use something other than zero for numbers so you can spot this
 * case more easily.
 * 
 * • Compile and test.
 * 
 * • Find all references to the old method and change them to refer to the new
 * one. Compile and test after each change.
 * 
 * • Remove the old method.
 * 
 * Image If the old method is part of the interface and you cannot remove it,
 * leave it in place and mark it as deprecated.
 * 
 * • Compile and test.
 */
public class E1001_Add_Parameter
{

}
