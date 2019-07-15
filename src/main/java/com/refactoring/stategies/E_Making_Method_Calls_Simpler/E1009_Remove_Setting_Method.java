// E1009_Remove_Setting_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * A field should be set at creation time and never altered.
 * 
 * Image
 * Remove any setting method for that field.
 * 
 * Motivation
 * Providing a setting method indicates that a field may be changed. If you
 * don’t want that field to change once the object is created, then don’t
 * provide a setting method (and make the field final). That way your intention
 * is clear and you often remove the very possibility that the field will
 * change.
 * 
 * This situation often occurs when programmers blindly use indirect variable
 * access [Beck]. Such programmers then use setters even in a constructor. I
 * guess there is an argument for consistency but not compared with the
 * confusion that the setting method will cause later on.
 * 
 * Mechanics
 * • If the field isn’t final, make it so.
 * 
 * • Compile and test.
 * 
 * • Check that the setting method is called only in the constructor, or in a
 * method called by the constructor.
 * 
 * • Modify the constructor to access the variables directly.
 * 
 * Image You cannot do this if you have a subclass setting the private fields of
 * a superclass. In this case you should try to provide a protected superclass
 * method (ideally a constructor) to set these values. Whatever you do, don’t
 * give the superclass method a name that will confuse it with a setting method.
 * 
 * • Compile and test.
 * 
 * • Remove the setting method.
 * 
 * • Compile.
 * 
 * Example
 * A simple example is as follows:
 * 
 * class Account {
 * 
 * private String _id;
 * 
 * Account (String id) {
 * setId(id);
 * }
 * 
 * void setId (String arg) {
 * _id = arg;
 * }
 * 
 * which can be replaced with
 * 
 * class Account {
 * 
 * private final String _id;
 * 
 * Account (String id) {
 * _id = id;
 * }
 * 
 * The problems come in some variations. First is the case in which you are
 * doing computation on the argument:
 * 
 * class Account {
 * 
 * private String _id;
 * 
 * Account (String id) {
 * setId(id);
 * }
 * 
 * void setId (String arg) {
 * _id = "ZZ" + arg;
 * }
 * 
 * If the change is simple (as here) and there is only one constructor, I can
 * make the change in the constructor. If the change is complex or I need to
 * call it from separate methods, I need to provide a method. In that case I
 * need to name the method to make its intention clear:
 * 
 * class Account {
 * 
 * private final String _id;
 * 
 * Account (String id) {
 * initializeId(id);
 * }
 * 
 * void initializeId (String arg) {
 * _id = "ZZ" + arg;
 * }
 * 
 * An awkward case lies with subclasses that initialize private superclass
 * variables:
 * 
 * class InterestAccount extends Account...
 * 
 * private double _interestRate;
 * 
 * InterestAccount (String id, double rate) {
 * setId(id);
 * _interestRate = rate;
 * }
 * 
 * The problem is that I cannot access id directly to set it. The best solution
 * is to use a superclass constructor:
 * 
 * class InterestAccount...
 * 
 * InterestAccount (String id, double rate) {
 * super(id);
 * _interestRate = rate;
 * }
 * 
 * If that is not possible, a well-named method is the best thing to use:
 * 
 * class InterestAccount...
 * 
 * InterestAccount (String id, double rate) {
 * initializeId(id);
 * _interestRate = rate;
 * }
 * 
 * Another case to consider is setting the value of a collection:
 * 
 * class Person {
 * Vector getCourses() {
 * return _courses;
 * }
 * void setCourses(Vector arg) {
 * _courses = arg;
 * }
 * private Vector _courses;
 * 
 * Here I want to replace the setter with add and remove operations. I talk
 * about this in Encapsulate Collection (208).
 */
public class E1009_Remove_Setting_Method
{

}
