// E1002_Remove_Parameter.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * A parameter is no longer used by the method body.
 * 
 * Image
 * Remove it.
 * 
 * Motivation
 * Programmers often add parameters but are reluctant to remove them. After all,
 * a spurious parameter doesn’t cause any problems, and you might need it again
 * later.
 * 
 * This is the demon Obfuscatis speaking; purge him from your soul! A parameter
 * indicates information that is needed; different values make a difference.
 * Your caller has to worry about what values to pass. By not removing the
 * parameter you are making further work for everyone who uses the method.
 * That’s not a good trade-off, especially because removing parameters is an
 * easy refactoring.
 * 
 * The case to be wary of here is a polymorphic method. In this case you may
 * well find that other implementations of the method do use the parameter. In
 * this case you shouldn’t remove the parameter. You might choose to add a
 * separate method that can be used in those cases, but you need to examine how
 * your callers use the method to see whether it is worth doing that. If some
 * callers already know they are dealing with a certain subclass and doing extra
 * work to find the parameter or are using knowledge of the class hierarchy to
 * know they can get away with a null, add an extra method without the
 * parameter. If they do not need to know about which class has which method,
 * the callers should be left in blissful ignorance.
 * 
 * Mechanics
 * The mechanics of Remove Parameter are very similar to those of Rename Method
 * (273) and Add Parameter (275).
 * 
 * • Check to see whether this method signature is implemented by a superclass
 * or subclass. Check to see whether the class or superclass uses the parameter.
 * If it does, don’t do this refactoring.
 * 
 * • Declare a new method without the parameter. Copy the old body of code to
 * the new method.
 * 
 * Image If you need to remove more than one parameter, it is easier to remove
 * them together.
 * 
 * • Compile.
 * 
 * • Change the body of the old method so that it calls the new one.
 * 
 * Image If you only have a few references, you can reasonably skip this step.
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
 * 
 * Because I’m pretty comfortable with adding and removing parameters, I often
 * do a batch in one go.
 */
public class E1002_Remove_Parameter
{

}
