// E1100_Hide_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * A method is not used by any other class.
 * 
 * Image
 * Make the method private.
 * 
 * Motivation
 * Refactoring often causes you to change decisions about the visibility of
 * methods. It is easy to spot cases in which you need to make a method more
 * visible: another class needs it and you thus relax the visibility. It is
 * somewhat more difficult to tell when a method is too visible. Ideally a tool
 * should check all methods to see whether they can be hidden. If it doesn’t,
 * you should make this check at regular intervals.
 * 
 * A particularly common case is hiding getting and setting methods as you work
 * up a richer interface that provides more behavior. This case is most common
 * when you are starting with a class that is little more than an encapsulated
 * data holder. As more behavior is built into the class, you may find that many
 * of the getting and setting methods are no longer needed publicly, in which
 * case they can be hidden. If you make a getting or setting method private and
 * you are using direct variable access, you can remove the method.
 * 
 * Mechanics
 * • Check regularly for opportunities to make a method more private.
 * 
 * Image Use a lint-style tool, do manual checks every so often, and check when
 * you remove a call to a method in another class.
 * 
 * Image Particularly look for cases such as this with setting methods.
 * 
 * • Make each method as private as you can.
 * 
 * • Compile after doing a group of hidings.
 * 
 * Image The compiler checks this naturally, so you don’t need to compile with
 * each change. If one goes wrong, it is easy to spot.
 */
public class E1100_Hide_Method
{

}
