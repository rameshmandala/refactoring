// F1005_Extract_Subclass.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * A class has features that are used only in some instances.
 * 
 * Create a subclass for that subset of features.
 * 
 * Image
 * Motivation
 * The main trigger for use of Extract Subclass is the realization that a class
 * has behavior used for some instances of the class and not for others.
 * Sometimes this is signaled by a type code, in which case you can use Replace
 * Type Code with Subclasses (223) or Replace Type Code with State/Strategy
 * (227). But you don’t have to have a type code to suggest the use for a
 * subclass.
 * 
 * The main alternative to Extract Subclass is Extract Class (149). This is a
 * choice between delegation and inheritance. Extract Subclass (330) is usually
 * simpler to do, but it has limitations. You can’t change the class-based
 * behavior of an object once the object is created. You can change the
 * class-based behavior with Extract Class (149) simply by plugging in different
 * components. You can also use only subclasses to represent one set of
 * variations. If you want the class to vary in several different ways, you have
 * to use delegation for all but one of them.
 * 
 * Mechanics
 * • Define a new subclass of the source class.
 * 
 * • Provide constructors for the new subclass.
 * 
 * Image In the simple cases, copy the arguments of the superclass and call the
 * superclass constructor with super.
 * 
 * Image If you want to hide the use of the subclass from clients, you can use
 * Replace Constructor with Factory Method (304).
 * 
 * • Find all calls to constructors of the superclass. If they need the
 * subclass, replace with a call to the new constructor.
 * 
 * Image If the subclass constructor needs different arguments, use Rename
 * Method (273) to change it. If some of the constructor parameters of the
 * superclass are no longer needed, use Rename Method (273) on that too.
 * 
 * Image If the superclass can no longer be directly instantiated, declare it
 * abstract.
 * 
 * • One by one use Push Down Method (328) and Push Down Field (329) to move
 * features onto the subclass.
 * 
 * Image Unlike Extract Class (149) it usually is easier to work with the
 * methods first and the data last.
 * 
 * Image When a public method is pushed, you may need to redefine a caller’s
 * variable or parameter type to call the new method. The compiler will catch
 * these cases.
 * 
 * • Look for any field that designates information now indicated by the
 * hierarchy (usually a boolean or type code). Eliminate it by using Self
 * Encapsulate Field (171) and replacing the getter with polymorphic constant
 * methods. All users of this field should be refactored with Replace
 * Conditional with Polymorphism (255).
 * 
 * Image For any methods outside the class that use an accessor, consider using
 * Move Method (142) to move the method into this class; then use Replace
 * Conditional with Polymorphism (255).
 * 
 * • Compile and test after each push down.
 * 
 * Example
 * I’ll start with a job item class that determines prices for items of work at
 * a local garage:
 * 
 * class JobItem ...
 * public JobItem (int unitPrice, int quantity, boolean isLabor, Employee
 * employee) {
 * _unitPrice = unitPrice;
 * _quantity = quantity;
 * _isLabor = isLabor;
 * _employee = employee;
 * }
 * public int getTotalPrice() {
 * return getUnitPrice() * _quantity;
 * }
 * public int getUnitPrice(){
 * return (_isLabor) ?
 * _employee.getRate():
 * _unitPrice;
 * }
 * public int getQuantity(){
 * return _quantity;
 * }
 * public Employee getEmployee() {
 * return _employee;
 * }
 * private int _unitPrice;
 * private int _quantity;
 * private Employee _employee;
 * private boolean _isLabor;
 * 
 * class Employee...
 * public Employee (int rate) {
 * _rate = rate;
 * }
 * public int getRate() {
 * return _rate;
 * }
 * private int _rate;
 * 
 * I extract a LaborItem subclass from this class because some of the behavior
 * and data are needed only in that case. I begin by creating the new class:
 * 
 * class LaborItem extends JobItem {}
 * 
 * The first thing I need is a constructor for the labor item because job item
 * does not have a no-arg constructor. For this I copy the signature of the
 * parent constructor:
 * 
 * public LaborItem (int unitPrice, int quantity, boolean isLabor, Employee
 * employee) {
 * super (unitPrice, quantity, isLabor, employee);
 * }
 * 
 * This is enough to get the new subclass to compile. However, the constructor
 * is messy; arguments are needed by the labor item, and some are not. I deal
 * with that later.
 * 
 * The next step is to look for calls to the constructor of the job item, and to
 * look for cases where the constructor of the labor item should be called
 * instead. So statements like
 * 
 * JobItem j1 = new JobItem (0, 5, true, kent);
 * 
 * become
 * 
 * JobItem j1 = new LaborItem (0, 5, true, kent);
 * 
 * At this stage I have not changed the type of the variable; I have changed
 * only the type of the constructor. This is because I want to use the new type
 * only where I have to. At this point I have no specific interface for the
 * subclass, so I don’t want to declare any variations yet.
 * 
 * Now is a good time to clean up the constructor parameter lists. I use Rename
 * Method (273) on each of them. I work with the superclass first. I create the
 * new constructor and make the old one protected (the subclass still needs it):
 * 
 * class JobItem...
 * protected JobItem (int unitPrice, int quantity, boolean isLabor, Employee
 * employee) {
 * _unitPrice = unitPrice;
 * _quantity = quantity;
 * _isLabor = isLabor;
 * _employee = employee;
 * }
 * public JobItem (int unitPrice, int quantity) {
 * this (unitPrice, quantity, false, null)
 * }
 * 
 * Calls from outside now use the new constructor:
 * 
 * JobItem j2 = new JobItem (10, 15);
 * 
 * Once I’ve compiled and tested, I use Rename Method (273) on the subclass
 * constructor:
 * 
 * class LaborItem
 * public LaborItem (int quantity, Employee employee) {
 * super (0, quantity, true, employee);
 * }
 * 
 * For the moment, I still use the protected superclass constructor.
 * 
 * Now I can start pushing down the features of the job item. I begin with the
 * methods. I start with using Push Down Method (328) on getEmployee:
 * 
 * class LaborItem...
 * public Employee getEmployee() {
 * return _employee;
 * }
 * class JobItem...
 * protected Employee _employee;
 * 
 * Because the employee field will be pushed down later, I declare it as
 * protected for the moment.
 * 
 * Once the employee field is protected, I can clean up the constructors so that
 * employee is set only in the subclass into which it is being pushed down:
 * 
 * class JobItem...
 * protected JobItem (int unitPrice, int quantity, boolean isLabor) {
 * _unitPrice = unitPrice;
 * _quantity = quantity;
 * _isLabor = isLabor;
 * }
 * class LaborItem ...
 * public LaborItem (int quantity, Employee employee) {
 * super (0, quantity, true);
 * _employee = employee;
 * }
 * 
 * The field _isLabor is used to indicate information that is now inherent in
 * the hierarchy. So I can remove the field. The best way to do this is to first
 * use Self Encapsulate Field (171) and then change the accessor to use a
 * polymorphic constant method. A polymorphic constant method is one whereby
 * each implementation returns a (different) fixed value:
 * 
 * class JobItem...
 * protected boolean isLabor() {
 * return false;
 * }
 * 
 * class LaborItem...
 * protected boolean isLabor() {
 * return true;
 * }
 * 
 * Then I can get rid of the isLabor field.
 * 
 * Now I can look at users of the isLabor methods. These should be refactored
 * with Replace Conditional with Polymorphism (255). I take the method
 * 
 * class JobItem...
 * public int getUnitPrice(){
 * return (isLabor()) ?
 * _employee.getRate():
 * _unitPrice;
 * }
 * 
 * and replace it with
 * 
 * class JobItem...
 * public int getUnitPrice(){
 * return _unitPrice;
 * }
 * class LaborItem...
 * public int getUnitPrice(){
 * return _employee.getRate();
 * }
 * 
 * Once a group of methods that use some data have been pushed down, I can use
 * Push Down Field (329) on the data. That I can’t use it because a method uses
 * the data is a signal for more work on the methods, either with Push Down
 * Method (328) or Replace Conditional with Polymorphism (255).
 * 
 * Because the unit price is used only by items that are nonlabor (parts job
 * items), I can use Extract Subclass (330) on job item again to create a parts
 * item class. When I’ve done that, the job item class will be abstract.
 */
public class F1005_Extract_Subclass
{

}
