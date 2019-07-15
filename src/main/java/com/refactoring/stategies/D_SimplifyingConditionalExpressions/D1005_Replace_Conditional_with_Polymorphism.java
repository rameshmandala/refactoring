// D1005_Replace_Conditional_with_Polymorphism.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.D_SimplifyingConditionalExpressions;

/**
 * You have a conditional that chooses different behavior depending on the type
 * of an object.
 * 
 * Move each leg of the conditional to an overriding method in a subclass. Make
 * the original method abstract.
 * 
 * double getSpeed() {
 * switch (_type) {
 * case EUROPEAN:
 * return getBaseSpeed();
 * case AFRICAN:
 * return getBaseSpeed() - getLoadFactor() * _numberOfCoconuts;
 * case NORWEGIAN_BLUE:
 * return (_isNailed) ? 0 : getBaseSpeed(_voltage);
 * }
 * throw new RuntimeException ("Should be unreachable");
 * }
 * 
 * Image
 * Image
 * Motivation
 * One of the grandest sounding words in object jargon is polymorphism. The
 * essence of polymorphsim is that it allows you to avoid writing an explicit
 * conditional when you have objects whose behavior varies depending on their
 * types.
 * 
 * As a result you find that switch statements that switch on type codes or
 * if-then-else statements that switch on type strings are much less common in
 * an object-oriented program.
 * 
 * Polymorphism gives you many advantages. The biggest gain occurs when this
 * same set of conditions appears in many places in the program. If you want to
 * add a new type, you have to find and update all the conditionals. But with
 * subclasses you just create a new subclass and provide the appropriate
 * methods. Clients of the class don’t need to know about the subclasses, which
 * reduces the dependencies in your system and makes it easier to update.
 * 
 * Mechanics
 * Before you can begin with Replace Conditional with Polymorphism you need to
 * have the necessary inheritance structure. You may already have this structure
 * from previous refactorings. If you don’t have the structure, you need to
 * create it.
 * 
 * To create the inheritance structure you have two options: Replace Type Code
 * with Subclasses (223) and Replace Type Code with State/Strategy (227).
 * Subclasses are the simplest option, so you should use them if you can. If you
 * update the type code after the object is created, however, you cannot use
 * subclassing and have to use the state/strategy pattern. You also need to use
 * the state/strategy pattern if you are already subclassing this class for
 * another reason. Remember that if several case statements are switching on the
 * same type code, you only need to create one inheritance structure for that
 * type code.
 * 
 * You can now attack the conditional. The code you target may be a switch
 * (case) statement or an if statement.
 * 
 * • If the conditional statement is one part of a larger method, take apart the
 * conditional statement and use Extract Method (110).
 * 
 * • If necessary use Move Method (142) to place the conditional at the top of
 * the inheritance structure.
 * 
 * • Pick one of the subclasses. Create a subclass method that overrides the
 * conditional statement method. Copy the body of that leg of the conditional
 * statement into the subclass method and adjust it to fit.
 * 
 * Image You may need to make some private members of the superclass protected
 * in order to do this.
 * 
 * • Compile and test.
 * 
 * • Remove the copied leg of the conditional statement.
 * 
 * • Compile and test.
 * 
 * • Repeat with each leg of the conditional statement until all legs are turned
 * into subclass methods.
 * 
 * • Make the superclass method abstract.
 * 
 * Example
 * I use the tedious and simplistic example of employee payment. I’m using the
 * classes after using Replace Type Code with State/Strategy (227) so the
 * objects look like Figure 9.1 (see the example in Chapter 8 for how we got
 * here).
 * 
 * Image
 * Figure 9.1. The inheritance structure
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
 * int getType() {
 * return _type.getTypeCode();
 * }
 * private EmployeeType _type;
 * 
 * abstract class EmployeeType...
 * abstract int getTypeCode();
 * 
 * class Engineer extends EmployeeType...
 * int getTypeCode() {
 * return Employee.ENGINEER;
 * }
 * 
 * ... and other subclasses
 * 
 * The case statement is already nicely extracted, so there is nothing to do
 * there. I do need to move it into the employee type, because that is the class
 * that is being subclassed.
 * 
 * class EmployeeType...
 * int payAmount(Employee emp) {
 * switch (getTypeCode()) {
 * case ENGINEER:
 * return emp.getMonthlySalary();
 * case SALESMAN:
 * return emp.getMonthlySalary() + emp.getCommission();
 * case MANAGER:
 * return emp.getMonthlySalary() + emp.getBonus();
 * default:
 * throw new RuntimeException("Incorrect Employee");
 * }
 * }
 * 
 * Because I need data from the employee, I need to pass in the employee as an
 * argument. Some of this data might be moved to the employee type object, but
 * that is an issue for another refactoring.
 * 
 * When this compiles, I change the payAmount method in Employee to delegate to
 * the new class:
 * 
 * class Employee...
 * int payAmount() {
 * return _type.payAmount(this);
 * }
 * 
 * Now I can go to work on the case statement. It’s rather like the way small
 * boys kill insects—I remove one leg at a time. First I copy the Engineer leg
 * of the case statement onto the Engineer class.
 * 
 * class Engineer...
 * int payAmount(Employee emp) {
 * return emp.getMonthlySalary();
 * }
 * 
 * This new method overrides the whole case statement for engineers. Because I’m
 * paranoid, I sometimes put a trap in the case statement:
 * 
 * class EmployeeType...
 * int payAmount(Employee emp) {
 * switch (getTypeCode()) {
 * case ENGINEER:
 * throw new RuntimeException ("Should be being overridden");
 * case SALESMAN:
 * return emp.getMonthlySalary() + emp.getCommission();
 * case MANAGER:
 * return emp.getMonthlySalary() + emp.getBonus();
 * default:
 * throw new RuntimeException("Incorrect Employee");
 * }
 * }
 * 
 * carry on until all the legs are removed:
 * 
 * class Salesman...
 * int payAmount(Employee emp) {
 * return emp.getMonthlySalary() + emp.getCommission();
 * }
 * class Manager...
 * int payAmount(Employee emp) {
 * return emp.getMonthlySalary() + emp.getBonus();
 * }
 * 
 * and then declare the superclass method abstract:
 * 
 * class EmployeeType...
 * abstract int payAmount(Employee emp);
 */
public class D1005_Replace_Conditional_with_Polymorphism
{

}
