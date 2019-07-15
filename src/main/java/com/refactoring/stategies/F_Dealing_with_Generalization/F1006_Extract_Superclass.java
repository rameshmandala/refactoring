// F1006_Extract_Superclass.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * You have two classes with similar features.
 * 
 * Create a superclass and move the common features to the superclass.
 * 
 * Image
 * Motivation
 * Duplicate code is one of the principal bad things in systems. If you say
 * things in multiple places, then when it comes time to change what you say,
 * you have more things to change than you should.
 * 
 * One form of duplicate code is two classes that do similar things in the same
 * way or similar things in different ways. Objects provide a built-in mechanism
 * to simplify this situation with inheritance. However, you often don’t notice
 * the commonalities until you have created some classes, in which case you need
 * to create the inheritance structure later.
 * 
 * An alternative is Extract Class (149). The choice is essentially between
 * inheritance and delegation. Inheritance is the simpler choice if the two
 * classes share interface as well as behavior. If you make the wrong choice,
 * you can always use Replace Inheritance with Delegation (352) later.
 * 
 * Mechanics
 * • Create a blank abstract superclass; make the original classes subclasses of
 * this superclass.
 * 
 * • One by one, use Pull Up Field (320), Pull Up Method (322), and Pull Up
 * Constructor Body (325) to move common elements to the superclass.
 * 
 * Image It’s usually easier to move the fields first.
 * 
 * Image If you have subclass methods that have different signatures but the
 * same purpose, use Rename Method (273) to get them to the same name and then
 * use Pull Up Method (322).
 * 
 * Image If you have methods with the same signature but different bodies,
 * declare the common signature as an abstract method on the superclass.
 * 
 * Image If you have methods with different bodies that do the same thing, you
 * may try using Substitute Algorithm (139) to copy one body into the other. If
 * this works, you can then use Pull Up Method (322).
 * 
 * • Compile and test after each pull.
 * 
 * • Examine the methods left on the subclasses. See if there are common parts,
 * if there are you can use Extract Method (110) followed by Pull Up Method
 * (322) on the common parts. If the overall flow is similar, you may be able to
 * use Form Template Method (345).
 * 
 * • After pulling up all the common elements, check each client of the
 * subclasses. If they use only the common interface you can change the required
 * type to the superclass.
 * 
 * Example
 * For this case I have an employee and a department:
 * 
 * class Employee...
 * public Employee (String name, String id, int annualCost) {
 * _name = name;
 * _id = id;
 * _annualCost = annualCost;
 * }
 * public int getAnnualCost() {
 * return _annualCost;
 * }
 * public String getId(){
 * return _id;
 * }
 * public String getName() {
 * return _name;
 * }
 * private String _name;
 * private int _annualCost;
 * private String _id;
 * 
 * public class Department...
 * public Department (String name) {
 * _name = name;
 * }
 * public int getTotalAnnualCost(){
 * Enumeration e = getStaff();
 * int result = 0;
 * while (e.hasMoreElements()) {
 * Employee each = (Employee) e.nextElement();
 * result += each.getAnnualCost();
 * }
 * return result;
 * }
 * public int getHeadCount() {
 * return _staff.size();
 * }
 * public Enumeration getStaff() {
 * return _staff.elements();
 * }
 * public void addStaff(Employee arg) {
 * _staff.addElement(arg);
 * }
 * public String getName() {
 * return _name;
 * }
 * private String _name;
 * private Vector _staff = new Vector();
 * 
 * There are a couple of areas of commonality here. First, both employees and
 * departments have names. Second, they both have annual costs, although the
 * methods for calculating them are slightly different. I extract a superclass
 * for both of these features. The first step is to create the new superclass
 * and define the existing superclasses to be subclasses of this superclass:
 * 
 * abstract class Party {}
 * class Employee extends Party...
 * class Department extends Party...
 * 
 * Now I begin to pull up features to the superclass. It is usually easier to
 * use Pull Up Field (320) first:
 * 
 * class Party...
 * protected String _name;
 * 
 * Then I can use Pull Up Method (322) on the getters:
 * 
 * class Party {
 * 
 * public String getName() {
 * return _name;
 * }
 * 
 * I like to make the field private. To do this I need to use Pull Up
 * Constructor Body (325) to assign the name:
 * 
 * class Party...
 * protected Party (String name) {
 * _name = name;
 * }
 * private String _name;
 * 
 * class Employee...
 * public Employee (String name, String id, int annualCost) {
 * super (name);
 * _id = id;
 * _annualCost = annualCost;
 * }
 * 
 * class Department...
 * public Department (String name) {
 * super (name);
 * }
 * 
 * The methods Department.getTotalAnnualCost and Employee.getAnnualCost, do
 * carry out the same intention, so they should have the same name. I first use
 * Rename Method (273) to get them to the same name:
 * 
 * class Department extends Party {
 * public int getAnnualCost(){
 * Enumeration e = getStaff();
 * int result = 0;
 * while (e.hasMoreElements()) {
 * Employee each = (Employee) e.nextElement();
 * result += each.getAnnualCost();
 * }
 * return result;
 * }
 * 
 * Their bodies are still different, so I cannot use Pull Up Method (322);
 * however, I can declare an abstract method on the superclass:
 * 
 * abstract public int getAnnualCost()
 * 
 * Once I’ve made the obvious changes, I look at the clients of the two classes
 * to see whether I can change any of them to use the new superclass. One client
 * of these classes is the department class itself, which holds a collection of
 * employees. The getAnnualCost method uses only the annual cost method, which
 * is now declared on the party:
 * 
 * class Department...
 * public int getAnnualCost(){
 * Enumeration e = getStaff();
 * int result = 0;
 * while (e.hasMoreElements()) {
 * Party each = (Party) e.nextElement();
 * result += each.getAnnualCost();
 * }
 * return result;
 * }
 * 
 * This behavior suggests a new possibility. I could treat department and
 * employee as a composite [Gang of Four]. This would allow me to let a
 * department include another department. This would be new functionality, so it
 * is not strictly a refactoring. If a composite were wanted, I would obtain it
 * by changing the name of the staff field to better represent the picture. This
 * change would involve making a corresponding change in the name addStaff and
 * altering the parameter to be a party. The final change would be to make the
 * headCount method recursive. I could do this by creating a headcount method
 * for employee that just returns 1 and using Substitute Algorithm (139) on the
 * department’s headcount to sum the headcounts of the components.
 */
public class F1006_Extract_Superclass
{

}
