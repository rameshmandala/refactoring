// C92_Replace_Type_Code_with_Subclasses.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * You have an immutable type code that affects the behavior of a class.
 * 
 * Image
 * Replace the type code with subclasses.
 * 
 * Motivation
 * If you have a type code that does not affect behavior, you can use Replace
 * Type Code with Class (218). However, if the type code affects behavior, the
 * best thing to do is to use polymorphism to handle the variant behavior.
 * 
 * This situation usually is indicated by the presence of case-like conditional
 * statements. These may be switches or if-then-else constructs. In either case
 * they test the value of the type code and then execute different code
 * depending on the value of the type code. Such conditionals need to be
 * refactored with Replace Conditional with Polymorphism (255). For this
 * refactoring to work, the type code has to be replaced with an inheritance
 * structure that will host the polymorphic behavior. Such an inheritance
 * structure has a class with subclasses for each type code.
 * 
 * The simplest way to establish this structure is Replace Type Code with
 * Subclasses. You take the class that has the type code and create a subclass
 * for each type code. However, there are cases in which you can’t do this. In
 * the first the value of the type code changes after the object is created. In
 * the second the class with the type code is already subclassed for another
 * reason. In either of these cases you need to use Replace Type Code with
 * State/Strategy.
 * 
 * Replace Type Code with Subclasses is primarily a scaffolding move that
 * enables Replace Conditional with Polymorphism (255). The trigger to use
 * Replace Type Code with Subclasses is the presence of conditional statements.
 * If there are no conditional statements, Replace Type Code with Class (218) is
 * the better and less critical move.
 * 
 * Another reason to Replace Type Code with Subclasses is the presence of
 * features that are relevant only to objects with certain type codes. Once
 * you’ve done this refactoring, you can use Push Down Method (328) and Push
 * Down Field (329) to clarify that these features are relevant only in certain
 * cases.
 * 
 * The advantage of Replace Type Code with Subclasses is that it moves knowledge
 * of the variant behavior from clients of the class to the class itself. If I
 * add new variants, all I need to do is add a subclass. Without polymorphism I
 * have to find all the conditionals and change those. So this refactoring is
 * particularly valuable when variants keep changing.
 * 
 * Mechanics
 * • Self-encapsulate the type code.
 * 
 * Image If the type code is passed into the constructor, you need to replace
 * the constructor with a factory method.
 * 
 * • For each value of the type code, create a subclass. Override the getting
 * method of the type code in the subclass to return the relevant value.
 * 
 * Image This value is hard coded into the return (e.g., return 1). This looks
 * messy, but it is a temporary measure until all case statements have been
 * replaced.
 * 
 * • Compile and test after replacing each type code value with a subclass.
 * 
 * • Remove the type code field from the superclass. Declare the accessors for
 * the type code as abstract.
 * 
 * • Compile and test.
 * 
 * Example
 * I use the boring and unrealistic example of employee payment:
 * 
 * class Employee...
 * private int _type;
 * static final int ENGINEER = 0;
 * static final int SALESMAN = 1;
 * static final int MANAGER = 2;
 * 
 * Employee (int type) {
 * _type = type;
 * }
 * 
 * The first step is to use Self Encapsulate Field (171) on the type code:
 * 
 * int getType() {
 * return _type;
 * }
 * 
 * Because the employee’s constructor uses a type code as a parameter, I need to
 * replace it with a factory method:
 * 
 * Employee create(int type) {
 * return new Employee(type);
 * }
 * 
 * private Employee (int type) {
 * _type = type;
 * }
 * 
 * I can now start with engineer as a subclass. First I create the subclass and
 * the overriding method for the type code:
 * 
 * class Engineer extends Employee {
 * 
 * int getType() {
 * return Employee.ENGINEER;
 * }
 * }
 * 
 * I also need to alter the factory method to create the appropriate object:
 * 
 * class Employee
 * static Employee create(int type) {
 * if (type == ENGINEER) return new Engineer();
 * else return new Employee(type);
 * }
 * 
 * I continue, one by one, until all the codes are replaced with subclasses. At
 * this point I can get rid of the type code field on employee and make getType
 * an abstract method. At this point the factory method looks like this:
 * 
 * abstract int getType();
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
 * Of course this is the kind of switch statement I would prefer to avoid. But
 * there is only one, and it is only used at creation.
 * 
 * Naturally once you have created the subclasses you should use Push Down
 * Method (328) and Push Down Field (329) on any methods and fields that are
 * relevant only for particular types of employee.
 */
public class C92_Replace_Type_Code_with_Subclasses
{

}
