// C93_Replace_Type_Code_with_State_OR_Strategy.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * You have a type code that affects the behavior of a class, but you cannot use
 * subclassing.
 * 
 * Image
 * Replace the type code with a state object.
 * 
 * Motivation
 * This is similar to Replace Type Code with Subclasses (223), but can be used
 * if the type code changes during the life of the object or if another reason
 * prevents subclassing. It uses either the state or strategy pattern [Gang of
 * Four].
 * 
 * State and strategy are very similar, so the refactoring is the same whichever
 * you use, and it doesn’t really matter. Choose the pattern that better fits
 * the specific circumstances. If you are trying to simplify a single algorithm
 * with Replace Conditional with Polymorphism (255), strategy is the better
 * term. If you are going to move state-specific data and you think of the
 * object as changing state, use the state pattern.
 * 
 * Mechanics
 * • Self-encapsulate the type code.
 * 
 * • Create a new class, and name it after the purpose of the type code. This is
 * the state object.
 * 
 * • Add subclasses of the state object, one for each type code.
 * 
 * Image It is easier to add the subclasses all at once, rather than one at a
 * time.
 * 
 * • Create an abstract query in the state object to return the type code.
 * Create overriding queries of each state object subclass to return the correct
 * type code.
 * 
 * • Compile.
 * 
 * • Create a field in the old class for the new state object.
 * 
 * • Adjust the type code query on the original class to delegate to the state
 * object.
 * 
 * • Adjust the type code setting methods on the original class to assign an
 * instance of the appropriate state object subclass.
 * 
 * • Compile and test.
 * 
 * Example
 * I again use the tiresome and brainless example of employee payment:
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
 * Here’s an example of the kind of conditional behavior that would use these
 * codes:
 * 
 * int payAmount() {
 * switch (_type) {
 * case ENGINEER:
 * return _monthlySalary;
 * case SALESMAN:
 * return _monthlySalary + _commission;
 * case MANAGER:
 * return _monthlySalary + _bonus;
 * default:
 * throw new RuntimeException("Incorrect Employee");
 * }
 * }
 * 
 * I assume this is an exciting and go-ahead company that allows promotion of
 * managers to engineers. Thus the type code is mutable, and I can’t use
 * subclassing. My first step, as ever, is to self-encapsulate the type code:
 * 
 * Employee (int type) {
 * setType (type);
 * }
 * 
 * int getType() {
 * return _type;
 * }
 * 
 * void setType(int arg) {
 * _type = arg;
 * }
 * 
 * int payAmount() {
 * switch (getType()) {
 * case ENGINEER:
 * return _monthlySalary;
 * case SALESMAN:
 * return _monthlySalary + _commission;
 * case MANAGER:
 * return _monthlySalary + _bonus;
 * default:
 * throw new RuntimeException("Incorrect Employee");
 * }
 * }
 * 
 * Now I declare the state class. I declare this as an abstract class and
 * provide an abstract method for returning the type code:
 * 
 * abstract class EmployeeType {
 * abstract int getTypeCode();
 * }
 * 
 * I now create the subclasses:
 * 
 * class Engineer extends EmployeeType {
 * 
 * int getTypeCode () {
 * return Employee.ENGINEER;
 * }
 * }
 * 
 * class Manager extends EmployeeType {
 * 
 * int getTypeCode () {
 * return Employee.MANAGER;
 * }
 * }
 * 
 * class Salesman extends EmployeeType {
 * 
 * int getTypeCode () {
 * return Employee.SALESMAN;
 * }
 * }
 * 
 * I compile so far, and it is all so trivial that even for me it compiles
 * easily. Now I actually hook the subclasses into the employee by modifying the
 * accessors for the type code:
 * 
 * class Employee...
 * private EmployeeType _type;
 * 
 * int getType() {
 * return _type.getTypeCode();
 * }
 * 
 * void setType(int arg) {
 * switch (arg) {
 * case ENGINEER:
 * _type = new Engineer();
 * break;
 * case SALESMAN:
 * _type = new Salesman();
 * break;
 * case MANAGER:
 * _type = new Manager();
 * break;
 * default:
 * throw new IllegalArgumentException("Incorrect Employee Code");
 * }
 * }
 * 
 * This means I now have a switch statement here. Once I’m finished refactoring,
 * it will be the only one anywhere in the code, and it will be executed only
 * when the type is changed. I can also use Replace Constructor with Factory
 * Method (304) to create factory methods for different cases. I can eliminate
 * all the other case statements with a swift thrust of Replace Conditional with
 * Polymorphism (255).
 * 
 * I like to finish Replace Type Code with State/Strategy (227) by moving all
 * knowledge of the type codes and subclasses over to the new class. First I
 * copy the type code definitions into the employee type, create a factory
 * method for employee types, and adjust the setting method on employee:
 * 
 * class Employee...
 * void setType(int arg) {
 * _type = EmployeeType.newType(arg);
 * }
 * 
 * class EmployeeType...
 * static EmployeeType newType(int code) {
 * switch (code) {
 * case ENGINEER:
 * return new Engineer();
 * case SALESMAN:
 * return new Salesman();
 * case MANAGER:
 * return new Manager();
 * default:
 * throw new IllegalArgumentException("Incorrect Employee Code");
 * }
 * }
 * static final int ENGINEER = 0;
 * static final int SALESMAN = 1;
 * static final int MANAGER = 2;
 * 
 * Then I remove the type code definitions from the employee and replace them
 * with references to the employee type:
 * 
 * class Employee...
 * int payAmount() {
 * switch (getType()) {
 * case EmployeeType.ENGINEER:
 * return _monthlySalary;
 * case EmployeeType.SALESMAN:
 * return _monthlySalary + _commission;
 * case EmployeeType.MANAGER:
 * return _monthlySalary + _bonus;
 * default:
 * throw new RuntimeException("Incorrect Employee");
 * }
 * }
 * 
 * I’m now ready to use Replace Conditional with Polymorphism (255) on
 * payAmount.
 */
public class C93_Replace_Type_Code_with_State_OR_Strategy
{

}
