// Pull_Up_Constructor_Body.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * You have constructors on subclasses with mostly identical bodies.
 * 
 * Create a superclass constructor; call this from the subclass methods.
 * 
 * class Manager extends Employee...
 * public Manager (String name, String id, int grade) {
 * _name = name;
 * _id = id;
 * _grade = grade;
 * }
 * 
 * Image
 * public Manager (String name, String id, int grade) {
 * super (name, id);
 * _grade = grade;
 * }
 * 
 * Motivation
 * Constructors are tricky things. They aren’t quite normal methods, so you are
 * more restricted in what you can do with them than you are when you use normal
 * methods.
 * 
 * If you see subclass methods with common behavior, your first thought is to
 * extract the common behavior into a method and pull it up into the superclass.
 * With a constructor, however, the common behavior is often the construction.
 * In this case you need a superclass constructor that is called by subclasses.
 * In many cases this is the whole body of the constructor. You can’t use Pull
 * Up Method (322) here, because you can’t inherit constructors (don’t you just
 * hate that?).
 * 
 * If refactoring becomes complex, you might want to consider Replace
 * Constructor with Factory Method (304) instead.
 * 
 * Mechanics
 * • Define a superclass constructor.
 * 
 * • Move the common code at the beginning from the subclass to the superclass
 * constructor.
 * 
 * Image This may be all the code.
 * 
 * Image Try to move common code to the beginning of the constructor.
 * 
 * • Call the superclass constructor as the first step in the subclass
 * constructor.
 * 
 * Image If all the code is common, this will be the only line of the subclass
 * constructor.
 * 
 * • Compile and test.
 * 
 * Image If there is any common code later, use Extract Method (110) to factor
 * out common code and use Pull Up Method to pull it up.
 * 
 * Example
 * Here are a manager and an employee:
 * 
 * class Employee...
 * protected String _name;
 * protected String _id;
 * 
 * class Manager extends Employee...
 * public Manager (String name, String id, int grade) {
 * _name = name;
 * _id = id;
 * _grade = grade;
 * }
 * 
 * private int _grade;
 * 
 * The fields from Employee should be set in a constructor for Employee. I
 * define one and make it protected to signal that subclasses should call it:
 * 
 * class Employee
 * protected Employee (String name, String id) {
 * _name = name;
 * _id = id;
 * }
 * 
 * Then I call it from the subclass:
 * 
 * public Manager (String name, String id, int grade) {
 * super (name, id);
 * _grade = grade;
 * }
 * 
 * A variation occurs when there is common code later. Say I have the following
 * code:
 * 
 * class Employee...
 * boolean isPriviliged() {..}
 * void assignCar() {..}
 * class Manager...
 * public Manager (String name, String id, int grade) {
 * super (name, id);
 * _grade = grade;
 * if (isPriviliged()) assignCar(); //every subclass does this
 * }
 * boolean isPriviliged() {
 * return _grade > 4;
 * }
 * 
 * I can’t move the assignCar behavior into the superclass constructor because
 * it must be executed after grade has been assigned to the field. So I need
 * Extract Method (110) and Pull Up Method (322).
 * 
 * class Employee...
 * void initialize() {
 * if (isPriviliged()) assignCar();
 * }
 * class Manager...
 * public Manager (String name, String id, int grade) {
 * super (name, id);
 * _grade = grade;
 * initialize();
 * }
 */
public class F1002_Pull_Up_Constructor_Body
{

}
