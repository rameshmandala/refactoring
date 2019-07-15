// C94_Replace_Subclass_with_Fields.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * ou have subclasses that vary only in methods that return constant data.
 * 
 * Image
 * Change the methods to superclass fields and eliminate the subclasses.
 * 
 * Motivation
 * You create subclasses to add features or allow behavior to vary. One form of
 * variant behavior is the constant method [Beck]. A constant method is one that
 * returns a hard-coded value. This can be very useful on subclasses that return
 * different values for an accessor. You define the accessor in the superclass
 * and implement it with different values on the subclass.
 * 
 * Although constant methods are useful, a subclass that consists only of
 * constant methods is not doing enough to be worth existing. You can remove
 * such subclasses completely by putting fields in the superclass. By doing that
 * you remove the extra complexity of the subclasses.
 * 
 * Mechanics
 * • Use Replace Constructor with Factory Method (304) on the subclasses.
 * 
 * • If any code refers to the subclasses, replace the reference with one to the
 * superclass.
 * 
 * • Declare final fields for each constant method on the superclass.
 * 
 * • Declare a protected superclass constructor to initialize the fields.
 * 
 * • Add or modify subclass constructors to call the new superclass constructor.
 * 
 * • Compile and test.
 * 
 * • Implement each constant method in the superclass to return the field and
 * remove the method from the subclasses.
 * 
 * • Compile and test after each removal.
 * 
 * • When all the subclass methods have been removed, use Inline Method (117) to
 * inline the constructor into the factory method of the superclass.
 * 
 * • Compile and test.
 * 
 * • Remove the subclass.
 * 
 * • Compile and test.
 * 
 * • Repeat inlining the constructor and elminating each subclass until they are
 * all gone.
 * 
 * Example
 * I begin with a person and sex-oriented subclasses:
 * 
 * abstract class Person {
 * 
 * abstract boolean isMale();
 * abstract char getCode();
 * ...
 * 
 * class Male extends Person {
 * boolean isMale() {
 * return true;
 * }
 * char getCode() {
 * return 'M';
 * }
 * }
 * 
 * class Female extends Person {
 * boolean isMale() {
 * return false;
 * }
 * char getCode() {
 * return 'F';
 * }
 * }
 * 
 * Here the only difference between the subclasses is that they have
 * implementations of abstract methods that return a hard-coded constant method
 * [Beck]. I remove these lazy subclasses.
 * 
 * First I need to use Replace Constructor with Factory Method (304). In this
 * case I want a factory method for each subclass:
 * 
 * class Person...
 * static Person createMale(){
 * return new Male();
 * }
 * static Person createFemale() {
 * return new Female();
 * }
 * 
 * I then replace calls of the form
 * 
 * Person kent = new Male();
 * 
 * with
 * 
 * Person kent = Person.createMale();
 * 
 * Once I’ve replaced all of these calls I shouldn’t have any references to the
 * subclasses. I can check this with a text search and check that at least
 * nothing accesses them outside the package by making the classes private.
 * 
 * Now I declare fields for each constant method on the superclass:
 * 
 * class Person...
 * private final boolean _isMale;
 * private final char _code;
 * 
 * I add a protected constructor on the superclass:
 * 
 * class Person...
 * protected Person (boolean isMale, char code) {
 * _isMale = isMale;
 * _code = code;
 * }
 * 
 * I add constructors that call this new constructor:
 * 
 * class Male...
 * Male() {
 * super (true, 'M');
 * }
 * class Female...
 * Female() {
 * super (false, 'F');
 * }
 * 
 * With that done I can compile and test. The fields are created and
 * initialized, but so far they aren’t being used. I can now start bringing the
 * fields into play by putting accessors on the superclass and eliminating the
 * subclass methods:
 * 
 * class Person...
 * boolean isMale() {
 * return _isMale;
 * }
 * class Male...
 * boolean isMale() {
 * return true;
 * }
 * 
 * I can do this one field and one subclass at a time or all in one go if I’m
 * feeling lucky.
 * 
 * After all the subclasses are empty, so I remove the abstract marker from the
 * person class and use Inline Method (117) to inline the subclass constructor
 * into the superclass:
 * 
 * class Person
 * static Person createMale(){
 * return new Person(true, 'M');
 * }
 * 
 * After compiling and testing I delete the male class and repeat the process
 * for the female class.
 */
public class C94_Replace_Subclass_with_Fields
{

}
