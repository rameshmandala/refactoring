// E1102_Encapsulate_Downcast.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * A method returns an object that needs to be downcasted by its callers.
 * 
 * Move the downcast to within the method.
 * 
 * Object lastReading() {
 * return readings.lastElement();
 * }
 * 
 * Image
 * Reading lastReading() {
 * return (Reading) readings.lastElement();
 * }
 * 
 * Motivation
 * Downcasting is one of the most annoying things you have to do with strongly
 * typed OO languages. It is annoying because it feels unnecessary; you are
 * telling the compiler something it ought to be able to figure out itself. But
 * because the figuring out often is rather complicated, you often have to do it
 * yourself. This is particularly prevalent in Java, in which the lack of
 * templates means that you have to downcast whenever you take an object out of
 * a collection.
 * 
 * Downcasting may be a necessary evil, but you should do it as little as
 * possible. If you return a value from a method, and you know the type of what
 * is returned is more specialized than what the method signature says, you are
 * putting unnecessary work on your clients. Rather than forcing them to do the
 * downcasting, you should always provide them with the most specific type you
 * can.
 * 
 * Often you find this situation with methods that return an iterator or
 * collection. Look instead to see what people are using the iterator for and
 * provide the method for that.
 * 
 * Mechanics
 * • Look for cases in which you have to downcast the result from calling a
 * method.
 * 
 * Image These cases often appear with methods that return a collection or
 * iterator.
 * 
 * • Move the downcast into the method.
 * 
 * Image With methods that return collections, use Encapsulate Collection (208).
 * 
 * Example
 * I have a method called lastReading, which returns the last reading from a
 * vector of readings:
 * 
 * Object lastReading() {
 * return readings.lastElement();
 * }
 * 
 * I should replace this with
 * 
 * Reading lastReading() {
 * return (Reading) readings.lastElement();
 * }
 * 
 * A good lead-in to doing this is where I have collection classes. Say this
 * collection of readings is on a Site class and I see code like this:
 * 
 * Reading lastReading = (Reading) theSite.readings().lastElement()
 * 
 * I can avoid the downcast and hide which collection is being used with
 * 
 * Reading lastReading = theSite.lastReading();
 * 
 * class Site...
 * Reading lastReading() {
 * return (Reading) readings().lastElement();
 * }
 * 
 * Altering a method to return a subclass alters the signature of the method but
 * does not break existing code because the compiler knows it can substitute a
 * subclass for the superclass. Of course you should ensure that the subclass
 * does not do anything that breaks the contract of the superclass.
 */
public class E1102_Encapsulate_Downcast
{

}
