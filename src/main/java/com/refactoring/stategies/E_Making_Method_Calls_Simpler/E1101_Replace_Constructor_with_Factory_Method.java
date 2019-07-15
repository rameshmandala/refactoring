// E1101_Replace_Constructor_with_Factory_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * You want to do more than simple construction when you create an object.
 * 
 * Replace the constructor with a factory method.
 * 
 * Employee (int type) {
 * _type = type;
 * }
 * 
 * Image
 * static Employee create(int type) {
 * return new Employee(type);
 * }
 * 
 * Motivation
 * The most obvious motivation for Replace Constructor with Factory Method comes
 * with replacing a type code with subclassing. You have an object that often is
 * created with a type code but now needs subclasses. The exact subclass is
 * based on the type code. However, constructors can only return an instance of
 * the object that is asked for. So you need to replace the constructor with a
 * factory method [Gang of Four].
 * 
 * You can use factory methods for other situations in which constructors are
 * too limited. Factory methods are essential for Change Value to Reference
 * (179). They also can be used to signal different creation behavior that goes
 * beyond the number and types of parameters.
 * 
 * Mechanics
 * • Create a factory method. Make its body a call to the current constructor.
 * 
 * • Replace all calls to the constructor with calls to the factory method.
 * 
 * • Compile and test after each replacement.
 * 
 * • Declare the constructor private.
 * 
 * • Compile.
 * 
 * Example
 * A quick but wearisome and belabored example is the employee payment system. I
 * have the following employee:
 * 
 * class Employee {
 * 
 * private int _type;
 * static final int ENGINEER = 0;
 * static final int SALESMAN = 1;
 * static final int MANAGER = 2;
 * 
 * Employee (int type) {
 * _type = type;
 * }
 * 
 * I want to make subclasses of Employee to correspond to the type codes. So I
 * need to create a factory method:
 * 
 * static Employee create(int type) {
 * return new Employee(type);
 * }
 * 
 * I then change all callers of the constructor to use this new method and make
 * the constructor private:
 * 
 * client code...
 * Employee eng = Employee.create(Employee.ENGINEER);
 * 
 * class Employee...
 * private Employee (int type) {
 * _type = type;
 * }
 * 
 * Example: Creating Subclasses with a String
 * So far I do not have a great gain; the advantage comes from the fact that I
 * have separated the receiver of the creation call from the class of object
 * created. If I later apply Replace Type Code with Subclasses (Chapter 8) to
 * turn the codes into subclasses of employee, I can hide these subclasses from
 * clients by using the factory method:
 * 
 * static Employee create(int type) {
 * switch (type) {
 * case ENGINEER:
 * return new Engineer();
 * case SALESMAN:
 * return new Salesman();
 * case MANAGER:
 * return new Manager();
 * default:
 * throw new IllegalArgumentException("Incorrect type code value");
 * }
 * }
 * 
 * The sad thing about this is that I have a switch. Should I add a new
 * subclass, I have to remember to update this switch statement, and I tend
 * toward forgetfulness.
 * 
 * A good way around this is to use Class.forName. The first thing to do is to
 * change the type of the parameter, essentially a variation on Rename Method
 * (273). First I create a new method that takes a string as an argument:
 * 
 * static Employee create (String name) {
 * try {
 * return (Employee) Class.forName(name).newInstance();
 * } catch (Exception e) {
 * throw new IllegalArgumentException ("Unable to instantiate" + name);
 * }
 * }
 * 
 * I then convert the integer create to use this new method:
 * 
 * class Employee {
 * static Employee create(int type) {
 * switch (type) {
 * case ENGINEER:
 * return create("Engineer");
 * case SALESMAN:
 * return create("Salesman");
 * case MANAGER:
 * return create("Manager");
 * default:
 * throw new IllegalArgumentException("Incorrect type code value");
 * }
 * }
 * 
 * I can then work on the callers of create to change statements such as
 * 
 * Employee.create(ENGINEER)
 * 
 * to
 * 
 * Employee.create( Engineer )
 * 
 * When I’m done I can remove the integer parameter version of the method.
 * 
 * This approach is nice in that it removes the need to update the create method
 * as I add new subclasses of employee. The approach, however, lacks compile
 * time checking: a spelling mistake leads to a runtime error. If this is
 * important, I use an explicit method (see below), but then I have to add a new
 * method every time I add a subclass. It’s a trade-off between flexibility and
 * type safety. Fortunately, if I make the wrong choice, I can use either
 * Parameterize Method (283) or Replace Parameter with Explicit Methods (285) to
 * reverse the decision.
 * 
 * Another reason to be wary of class.forName is that it exposes subclass names
 * to clients. This is not too bad because you can use other strings and carry
 * out other behavior with the factory method. That’s a good reason not to use
 * Inline Method (117) to remove the factory.
 * 
 * Example: Creating Subclasses with Explicit Methods
 * I can use a differenct approach to hide subclasses with explicit methods.
 * This is useful if you have just a few subclasses that don’t change. So I may
 * have an abstract Person class with subclasses for Male and Female. I begin by
 * defining a factory method for each subclass on the superclass:
 * 
 * class Person...
 * static Person createMale(){
 * return new Male();
 * }
 * static Person createFemale() {
 * return new Female();
 * }
 * 
 * I can then replace calls of the form
 * 
 * Person kent = new Male();
 * 
 * with
 * 
 * Person kent = Person.createMale();
 * 
 * This leaves the superclass knowing about the subclasses. If you want to avoid
 * this, you need a more complex scheme, such as a product trader [Bäumer and
 * Riehle]. Most of the time, however, that complexity isn’t needed, and this
 * approach works nicely.
 */
public class E1101_Replace_Constructor_with_Factory_Method
{

}
