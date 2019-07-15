// E1005_Replace_Parameter_with_Explicit_Methods.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * You have a method that runs different code depending on the values of an
 * enumerated parameter.
 * 
 * Create a separate method for each value of the parameter.
 * 
 * void setValue (String name, int value) {
 * if (name.equals("height")) {
 * _height = value;
 * return;
 * }
 * if (name.equals("width")) {
 * _width = value;
 * return;
 * }
 * Assert.shouldNeverReachHere();
 * }
 * 
 * Image
 * void setHeight(int arg) {
 * _height = arg;
 * }
 * void setWidth (int arg) {
 * _width = arg;
 * }
 * 
 * Motivation
 * Replace Parameter with Explicit Methods is the reverse of Parameterize Method
 * (283). The usual case for the former is that you have discrete values of a
 * parameter, test for those values in a conditional, and do different things.
 * The caller has to decide what it wants to do by setting the parameter, so you
 * might as well provide different methods and avoid the conditional. You not
 * only avoid the conditional behavior but also gain compile time checking.
 * Furthermore your interface also is clearer. With the parameter, any
 * programmer using the method needs not only to look at the methods on the
 * class but also to determine a valid parameter value. The latter is often
 * poorly documented.
 * 
 * The clarity of the explicit interface can be worthwhile even when the compile
 * time checking isn’t an advantage. Switch.beOn() is a lot clearer than
 * Switch.setState(true), even when all you are doing is setting an internal
 * boolean field.
 * 
 * You shouldn’t use Replace Parameter with Explicit Methods when the parameter
 * values are likely to change a lot. If this happens and you are just setting a
 * field to the passed in parameter, use a simple setter. If you need
 * conditional behavior, you need Replace Conditional with Polymorphism (255).
 * 
 * Mechanics
 * • Create an explicit method for each value of the parameter.
 * 
 * • For each leg of the conditional, call the appropriate new method.
 * 
 * • Compile and test after changing each leg.
 * 
 * • Replace each caller of the conditional method with a call to the
 * appropriate new method.
 * 
 * • Compile and test.
 * 
 * • When all callers are changed, remove the conditional method.
 * 
 * Example
 * I want to create a subclass of employee on the basis of a passed in
 * parameter, often the result of Replace Constructor with Factory Method (304):
 * 
 * static final int ENGINEER = 0;
 * static final int SALESMAN = 1;
 * static final int MANAGER = 2;
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
 * Because this is a factory method, I can’t use Replace Conditional with
 * Polymorphism (255), because I haven’t created the object yet. I don’t expect
 * too many new subclasses, so an explicit interface makes sense. First I create
 * the new methods:
 * 
 * static Employee createEngineer() {
 * return new Engineer();
 * }
 * static Employee createSalesman() {
 * return new Salesman();
 * }
 * static Employee createManager() {
 * return new Manager();
 * }
 * 
 * One by one I replace the cases in the switch statements with calls to the
 * explicit methods:
 * 
 * static Employee create(int type) {
 * switch (type) {
 * case ENGINEER:
 * return Employee.createEngineer();
 * case SALESMAN:
 * return new Salesman();
 * case MANAGER:
 * return new Manager();
 * default:
 * throw new IllegalArgumentException("Incorrect type code value");
 * }
 * }
 * 
 * I compile and test after changing each leg, until I’ve replaced them all:
 * 
 * static Employee create(int type) {
 * switch (type) {
 * case ENGINEER:
 * return Employee.createEngineer();
 * case SALESMAN:
 * return Employee.createSalesman();
 * case MANAGER:
 * return Employee.createManager();
 * default:
 * throw new IllegalArgumentException("Incorrect type code value");
 * }
 * }
 * 
 * Now I move on to the callers of the old create method. I change code such as
 * 
 * Employee kent = Employee.create(ENGINEER)
 * 
 * to
 * 
 * Employee kent = Employee.createEngineer()
 * 
 * Once I’ve done that for all the callers of create, I can remove the create
 * method. I may also be able to get rid of the constants.
 */
public class E1005_Replace_Parameter_with_Explicit_Methods
{

}
