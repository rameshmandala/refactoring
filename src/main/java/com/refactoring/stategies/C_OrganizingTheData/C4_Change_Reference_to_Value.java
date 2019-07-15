// C4_Change_Reference_to_Value.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * CHANGE REFERENCE TO VALUE
 * You have a reference object that is small, immutable, and awkward to manage.
 * 
 * Turn it into a value object.
 * 
 * Image
 * Motivation
 * As with Change Value to Reference (179), the decision between a reference and
 * a value object is not always clear. It is a decision that often needs
 * reversing.
 * 
 * The trigger for going from a reference to a value is that working with the
 * reference object becomes awkward. Reference objects have to be controlled in
 * some way. You always need to ask the controller for the appropriate object.
 * The memory links also can be awkward. Value objects are particularly useful
 * for distributed and concurrent systems.
 * 
 * An important property of value objects is that they should be immutable. Any
 * time you invoke a query on one, you should get the same result. If this is
 * true, there is no problem having many objects represent the same thing. If
 * the value is mutable, you have to ensure that changing any object also
 * updates all the other objects that represent the same thing. That’s so much
 * of a pain that the easiest thing to do is to make it a reference object.
 * 
 * It’s important to be clear on what immutable means. If you have a money class
 * with a currency and a value, that’s usually an immutable value object. That
 * does not mean your salary cannot change. It means that to change your salary,
 * you need to replace the existing money object with a new money object rather
 * than changing the amount on an exisiting money object. Your relationship can
 * change, but the money object itself does not.
 * 
 * Mechanics
 * • Check that the candidate object is immutable or can become immutable.
 * 
 * Image If the object isn’t currently immutable, use Remove Setting Method
 * (300) until it is.
 * 
 * Image If the candidate cannot become immutable, you should abandon this
 * refactoring.
 * 
 * • Create an equals method and a hash method.
 * 
 * • Compile and test.
 * 
 * • Consider removing any factory method and making a constructor public.
 * 
 * Example
 * I begin with a currency class:
 * 
 * class Currency...
 * private String _code;
 * 
 * public String getCode() {
 * return _code;
 * }
 * private Currency (String code) {
 * _code = code;
 * }
 * 
 * All this class does is hold and return a code. It is a reference object, so
 * to get an instance I need to use
 * 
 * Currency usd = Currency.get("USD");
 * 
 * The currency class maintains a list of instances. I can’t just use a
 * constructor (which is why it’s private).
 * 
 * new Currency("USD").equals(new Currency("USD")) // returns false
 * 
 * To convert this to a value object, the key thing to do is verify that the
 * object is immutable. If it isn’t, I don’t try to make this change, as a
 * mutable value causes no end of painful aliasing.
 * 
 * In this case the object is immutable, so the next step is to define an equals
 * method:
 * 
 * public boolean equals(Object arg) {
 * if (! (arg instanceof Currency)) return false;
 * Currency other = (Currency) arg;
 * return (_code.equals(other._code));
 * }
 * 
 * If I define equals, I also need to define hashCode. The simple way to do this
 * is to take the hash codes of all the fields used in the equals method and do
 * a biwise xor (^) on them. Here it’s easy because there’s only one:
 * 
 * public int hashCode() {
 * return _code.hashCode();
 * }
 * 
 * With both methods replaced, I can compile and test. I need to do both;
 * otherwise any collection that relies on hashing, such as Hashtable, HashSet
 * or HashMap, may act strangely.
 * 
 * Now I can create as many equal currencies as I like. I can get rid of all the
 * controller behavior on the class and the factory method and just use the
 * constructor, which I can now make public.
 * 
 * new Currency("USD").equals(new Currency("USD")) // now returns true
 */
public class C4_Change_Reference_to_Value
{

}
