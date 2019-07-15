// F1100_Replace_Delegation_with_Inheritance.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * You’re using delegation and are often writing many simple delegations for the
 * entire interface.
 * 
 * Make the delegating class a subclass of the delegate.
 * 
 * Image
 * Motivation
 * This is the flip side of Replace Inheritance with Delegation (352). If you
 * find yourself using all the methods of the delegate and are sick of writing
 * all those simple delegating methods, you can switch back to inheritance
 * pretty easily.
 * 
 * There are a couple of caveats to bear in mind. If you aren’t using all the
 * methods of the class to which you are delegating, you shouldn’t use Replace
 * Delegation with Inheritance, because a subclass should always follow the
 * interface of the superclass. If the delegating methods are tiresome, you have
 * other options. You can let the clients call the delegate themselves with
 * Remove Middle Man (160). You can use Extract Superclass (336) to separate the
 * common interface and then inherit from the new class. You can use Extract
 * Interface (341) in a similar way.
 * 
 * Another situation to beware of is that in which the delegate is shared by
 * more than one object and is mutable. In this case you can’t replace the
 * delegate with inheritance because you’ll no longer share the data. Data
 * sharing is a responsibility that cannot be transferred back to inheritance.
 * When the object is immutable, data sharing is not a problem, because you can
 * just copy and nobody can tell.
 * 
 * Mechanics
 * • Make the delegating object a subclass of the delegate.
 * 
 * • Compile.
 * 
 * Image You may get some method clashes at this point; methods may have the
 * same name but vary in return type, exceptions, or visibility. Use Rename
 * Method (273) to fix these.
 * 
 * • Set the delegate field to be the object itself.
 * 
 * • Remove the simple delegation methods.
 * 
 * • Compile and test.
 * 
 * • Replace all other delegations with calls to the object itself.
 * 
 * • Remove the delegate field.
 * 
 * Example
 * A simple employee delegates to a simple person:
 * 
 * class Employee {
 * Person _person = new Person();
 * 
 * public String getName() {
 * return _person.getName();
 * }
 * public void setName(String arg) {
 * _person.setName(arg);
 * }
 * public String toString () {
 * return "Emp: " + _person.getLastName();
 * }
 * }
 * 
 * class Person {
 * String _name;
 * 
 * public String getName() {
 * return _name;
 * }
 * public void setName(String arg) {
 * _name = arg;
 * }
 * public String getLastName() {
 * return _name.substring(_name.lastIndexOf(' ')+1);
 * }
 * }
 * 
 * The first step is just to declare the subclass:
 * 
 * class Employee extends Person
 * 
 * Compiling at this point alerts me to any method clashes. These occur if
 * methods with the name have different return types or throw different
 * exceptions. Any such problems need to be fixed with Rename Method (273). This
 * simple example is free of such encumbrances.
 * 
 * The next step is to make the delegate field refer to the object itself. I
 * must remove all simple delegation methods such as getName and setName. If I
 * leave any in, I will get a stack overflow error caused by infinite recursion.
 * In this case this means removing getName and setName from Employee.
 * 
 * Once I’ve got the class working, I can change the methods that use the
 * delegate methods. I switch them to use calls directly:
 * 
 * public String toString () {
 * return "Emp: " + getLastName();
 * }
 * 
 * Once I’ve got rid of all methods that use delegate methods, I can get rid of
 * the _person field.
 */
public class F1100_Replace_Delegation_with_Inheritance
{

}
