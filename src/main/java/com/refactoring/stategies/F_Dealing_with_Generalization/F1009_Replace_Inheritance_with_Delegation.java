// F1009_Replace_Inheritance_with_Delegation.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * A subclass uses only part of a superclasses interface or does not want to
 * inherit data.
 * 
 * Create a field for the superclass, adjust methods to delegate to the
 * superclass, and remove the subclassing.
 * 
 * Image
 * Motivation
 * Inheritance is a wonderful thing, but sometimes it isn’t what you want. Often
 * you start inheriting from a class but then find that many of the superclass
 * operations aren’t really true of the subclass. In this case you have an
 * interface that’s not a true reflection of what the class does. Or you may
 * find that you are inheriting a whole load of data that is not appropriate for
 * the subclass. Or you may find that there are protected superclass methods
 * that don’t make much sense with the subclass.
 * 
 * You can live with the situation and use convention to say that although it is
 * a subclass, it’s using only part of the superclass function. But that results
 * in code that says one thing when your intention is something else—a confusion
 * you should remove.
 * 
 * By using delegation instead, you make it clear that you are making only
 * partial use of the delegated class. You control which aspects of the
 * interface to take and which to ignore. The cost is extra delegating methods
 * that are boring to write but are too simple to go wrong.
 * 
 * Mechanics
 * • Create a field in the subclass that refers to an instance of the
 * superclass. Initialize it to this.
 * 
 * • Change each method defined in the subclass to use the delegate field.
 * Compile and test after changing each method.
 * 
 * Image You won’t be able to replace any methods that invoke a method on super
 * that is defined on the subclass, or they may get into an infinite recurse.
 * These methods can be replaced only after you have broken the inheritance.
 * 
 * • Remove the subclass declaration and replace the delegate assignment with an
 * assignment to a new object.
 * 
 * • For each superclass method used by a client, add a simple delegating
 * method.
 * 
 * • Compile and test.
 * 
 * Example
 * One of the classic examples of inappropriate inheritance is making a stack a
 * subclass of vector. Java 1.1 does this in its utilities (naughty boys!), but
 * in this case I use a simplified form of stack:
 * 
 * class MyStack extends Vector {
 * 
 * public void push(Object element) {
 * insertElementAt(element,0);
 * }
 * 
 * public Object pop() {
 * Object result = firstElement();
 * removeElementAt(0);
 * return result;
 * }
 * }
 * 
 * Looking at the users of the class, I realize that clients do only four things
 * with stack: push, pop, size, and isEmpty. The latter two are inherited from
 * Vector.
 * 
 * I begin the delegation by creating a field for the delegated vector. I link
 * this field to this so that I can mix delegation and inheritance while I carry
 * out the refactoring:
 * 
 * private Vector _vector = this;
 * 
 * Now I start replacing methods to get them to use the delegation. I begin with
 * push:
 * 
 * public void push(Object element) {
 * _vector.insertElementAt(element,0);
 * }
 * 
 * I can compile and test here, and everything will still work. Now pop:
 * 
 * public Object pop() {
 * Object result = _vector.firstElement();
 * _vector.removeElementAt(0);
 * return result;
 * }
 * 
 * Once I’ve completed these subclass methods, I need to break the link to the
 * superclass:
 * 
 * class MyStack extends Vector
 * private Vector _vector = new Vector();
 * 
 * I then add simple delegating methods for superclass methods used by clients:
 * 
 * public int size() {
 * return _vector.size();
 * }
 * 
 * public boolean isEmpty() {
 * return _vector.isEmpty();
 * }
 * 
 * Now I can compile and test. If I forgot to add a delegating method, the
 * compilation will tell me.
 */
public class F1009_Replace_Inheritance_with_Delegation
{

}
