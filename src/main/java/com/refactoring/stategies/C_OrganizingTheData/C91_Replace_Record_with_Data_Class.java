// C91_Replace_Record_with_Data_Class.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * You need to interface with a record structure in a traditional programming
 * environment.
 * 
 * Make a dumb data object for the record.
 * 
 * Motivation
 * Record structures are a common feature of programming environments. There are
 * various reasons for bringing them into an object-oriented program. You could
 * be copying a legacy program, or you could be communicating a structured
 * record with a traditional programming API, or a database record. In these
 * cases it is useful to create an interfacing class to deal with this external
 * element. It is simplest to make the class look like the external record. You
 * move other fields and methods into the class later. A less obvious but very
 * compelling case is an array in which the element in each index has a special
 * meaning. In this case you use Replace Array with Object (186).
 * 
 * Mechanics
 * • Create a class to represent the record.
 * 
 * • Give the class a private field with a getting method and a setting method
 * for each data item.
 * 
 * You now have a dumb data object. It has no behavior yet but further
 * refactoring will explore that issue.
 * 
 * REPLACE TYPE CODE WITH CLASS
 * A class has a numeric type code that does not affect its behavior.
 * 
 * Image
 * Replace the number with a new class.
 * 
 * Motivation
 * Numeric type codes, or enumerations, are a common feature of C-based
 * languages. With symbolic names they can be quite readable. The problem is
 * that the symbolic name is only an alias; the compiler still sees the
 * underlying number. The compiler type checks using the number not the symbolic
 * name. Any method that takes the type code as an argument expects a number,
 * and there is nothing to force a symbolic name to be used. This can reduce
 * readability and be a source of bugs.
 * 
 * If you replace the number with a class, the compiler can type check on the
 * class. By providing factory methods for the class, you can statically check
 * that only valid instances are created and that those instances are passed on
 * to the correct objects.
 * 
 * Before you do Replace Type Code with Class, however, you need to consider the
 * other type code replacements. Replace the type code with a class only if the
 * type code is pure data, that is, it does not cause different behavior inside
 * a switch statement. For a start Java can only switch on an integer, not an
 * arbitrary class, so the replacement will fail. More important than that, any
 * switch has to be removed with Replace Conditional with Polymorphism (255). In
 * order for that refactoring, the type code first has to be handled with
 * Replace Type Code with Subclasses (223) or Replace Type Code with
 * State/Strategy (227).
 * 
 * Even if a type code does not cause different behavior depending on its value,
 * there might be behavior that is better placed in the type code class, so be
 * alert to the value of a Move Method (142) or two.
 * 
 * Mechanics
 * • Create a new class for the type code.
 * 
 * Image The class needs a code field that matches the type code and a getting
 * method for this value. It should have static variables for the allowable
 * instances of the class and a static method that returns the appropriate
 * instance from an argument based on the original code.
 * 
 * • Modify the implementation of the source class to use the new class.
 * 
 * Image Maintain the old code-based interface, but change the static fields to
 * use new class to generate the codes. Alter the other code-based methods to
 * get the code numbers from the new class.
 * 
 * • Compile and test.
 * 
 * Image At this point the new class can do run-time checking of the codes.
 * 
 * • For each method on the source class that uses the code, create a new method
 * that uses the new class instead.
 * 
 * Image Methods that use the code as an argument need new methods that use an
 * instance of the new class as an argument. Methods that return a code need a
 * new method that returns an instance of the new class. It is often wise to use
 * Rename Method (273) on an old accessor before creating a new one to make the
 * program clearer when it is using an old code.
 * 
 * • One by one, change the clients of the source class so that they use the new
 * interface.
 * 
 * • Compile and test after each client is updated.
 * 
 * Image You may need to alter several methods before you have enough
 * consistency to compile and test.
 * 
 * • Remove the old interface that uses the codes, and remove the static
 * declarations of the codes.
 * 
 * • Compile and test.
 * 
 * Example
 * A person has a blood group modeled with a type code:
 * 
 * class Person {
 * 
 * public static final int O = 0;
 * public static final int A = 1;
 * public static final int B = 2;
 * public static final int AB = 3;
 * 
 * private int _bloodGroup;
 * 
 * public Person (int bloodGroup) {
 * _bloodGroup = bloodGroup;
 * }
 * 
 * public void setBloodGroup(int arg) {
 * _bloodGroup = arg;
 * }
 * 
 * public int getBloodGroup() {
 * return _bloodGroup;
 * }
 * }
 * 
 * I start by creating a new blood group class with instances that contain the
 * type code number:
 * 
 * class BloodGroup {
 * public static final BloodGroup O = new BloodGroup(0);
 * public static final BloodGroup A = new BloodGroup(1);
 * public static final BloodGroup B = new BloodGroup(2);
 * public static final BloodGroup AB = new BloodGroup(3);
 * private static final BloodGroup[] _values = {O, A, B, AB};
 * 
 * private final int _code;
 * 
 * private BloodGroup (int code ) {
 * _code = code;
 * }
 * 
 * public int getCode() {
 * return _code;
 * }
 * 
 * public static BloodGroup code(int arg) {
 * return _values[arg];
 * }
 * }
 * 
 * I then replace the code in Person with code that uses the new class:
 * 
 * class Person {
 * 
 * public static final int O = BloodGroup.O.getCode();
 * public static final int A = BloodGroup.A.getCode();
 * public static final int B = BloodGroup.B.getCode();
 * public static final int AB = BloodGroup.AB.getCode();
 * 
 * private BloodGroup _bloodGroup;
 * 
 * public Person (int bloodGroup) {
 * _bloodGroup = BloodGroup.code(bloodGroup);
 * }
 * 
 * public int getBloodGroup() {
 * return _bloodGroup.getCode();
 * }
 * 
 * public void setBloodGroup(int arg) {
 * _bloodGroup = BloodGroup.code (arg);
 * }
 * }
 * 
 * At this point I have run-time checking within the blood group class. To
 * really gain from the change I have to alter the users of the person class to
 * use blood group instead of integers.
 * 
 * To begin I use Rename Method (273) on the accessor for the person’s blood
 * group to clarify the new state of affairs:
 * 
 * class Person...
 * public int getBloodGroupCode() {
 * return _bloodGroup.getCode();
 * }
 * 
 * I then add a new getting method that uses the new class:
 * 
 * public BloodGroup getBloodGroup() {
 * return _bloodGroup;
 * }
 * 
 * I also create a new constructor and setting method that uses the class:
 * 
 * public Person (BloodGroup bloodGroup ) {
 * _bloodGroup = bloodGroup;
 * }
 * 
 * public void setBloodGroup(BloodGroup arg) {
 * _bloodGroup = arg;
 * }
 * 
 * Now I go to work on the clients of Person. The art is to work on one client
 * at a time so that you can take small steps. Each client may need various
 * changes, and that makes it more tricky. Any reference to the static variables
 * needs to be changed. So
 * 
 * Person thePerson = new Person(Person.A)
 * 
 * becomes
 * 
 * Person thePerson = new Person(BloodGroup.A);
 * 
 * References to the getting method need to use the new one, so
 * 
 * thePerson.getBloodGroupCode()
 * 
 * becomes
 * 
 * thePerson.getBloodGroup().getCode()
 * 
 * The same is true for setting methods, so
 * 
 * thePerson.setBloodGroup(Person.AB)
 * 
 * becomes
 * 
 * thePerson.setBloodGroup(BloodGroup.AB)
 * 
 * Once this is done for all clients of Person, I can remove the getting method,
 * constructor, static definitions, and setting methods that use the integer:
 * 
 * class Person ...
 * public static final int O = BloodGroup.O.getCode();
 * public static final int A = BloodGroup.A.getCode();
 * public static final int B = BloodGroup.B.getCode();
 * public static final int AB = BloodGroup.AB.getCode();
 * public Person (int bloodGroup) {
 * _bloodGroup = BloodGroup.code(bloodGroup);
 * }
 * public int getBloodGroupCode() {
 * return _bloodGroup.getCode();
 * }
 * public void setBloodGroup(int arg) {
 * _bloodGroup = BloodGroup.code (arg);
 * }
 * 
 * If no clients use the numeric code I can also privatize the methods on blood
 * group that use the code:
 * 
 * class BloodGroup...
 * private int getCode() {
 * return _code;
 * }
 * 
 * private static BloodGroup code(int arg) {
 * return _values[arg];
 * }
 */
public class C91_Replace_Record_with_Data_Class
{

}
