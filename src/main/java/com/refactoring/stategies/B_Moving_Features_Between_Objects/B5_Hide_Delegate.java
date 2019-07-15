// B5_Hide_Delegate.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.B_Moving_Features_Between_Objects;

/**
 * HIDE DELEGATE
 * A client is calling a delegate class of an object.
 * 
 * Image
 * Create methods on the server to hide the delegate.
 * 
 * Motivation
 * One of the keys, if not the key, to objects is encapsulation. Encapsulation
 * means that objects need to know less about other parts of the system. Then
 * when things change, fewer objects need to be told about the change—which
 * makes the change easier to make.
 * 
 * Anyone involved in objects knows that you should hide your fields, despite
 * the fact that Java allows fields to be public. As you become more
 * sophisticated, you realize there is more you can encapsulate.
 * 
 * If a client calls a method defined on one of the fields of the server object,
 * the client needs to know about this delegate object. If the delegate changes,
 * the client also may have to change. You can remove this dependency by placing
 * a simple delegating method on the server, which hides the delegate (Figure
 * 7.1). Changes become limited to the server and don’t propagate to the client.
 * 
 * Image
 * Figure 7.1. Simple delegation
 * 
 * You may find it is worthwhile to use Extract Class (149) for some clients of
 * the server or all clients. If you hide from all clients, you can remove all
 * mention of the delegate from the interface of the server.
 * 
 * Mechanics
 * • For each method on the delegate, create a simple delegating method on the
 * server.
 * 
 * • Adjust the client to call the server.
 * 
 * Image If the client is not in the same package as the server, consider
 * changing the delegate method’s access to package visibility.
 * 
 * • Compile and test after adjusting each method.
 * 
 * • If no client needs to access the delegate anymore, remove the server’s
 * accessor for the delegate.
 * 
 * • Compile and test.
 * 
 * Example
 * I start with a person and a department:
 * 
 * class Person {
 * Department _department;
 * 
 * public Department getDepartment() {
 * return _department;
 * }
 * public void setDepartment(Department arg) {
 * _department = arg;
 * }
 * }
 * 
 * class Department {
 * private String _chargeCode;
 * private Person _manager;
 * 
 * public Department (Person manager) {
 * _manager = manager;
 * }
 * 
 * public Person getManager() {
 * return _manager;
 * }
 * ...
 * 
 * If a client wants to know a person’s manager, it needs to get the department
 * first:
 * 
 * manager = john.getDepartment().getManager();
 * 
 * This reveals to the client how the department class works and that the
 * department is responsible to tracking the manager. I can reduce this coupling
 * by hiding the department class from the client. I do this by creating a
 * simple delegating method on person:
 * 
 * public Person getManager() {
 * return _department.getManager();
 * }
 * 
 * I now need to change all clients of person to use this new method:
 * 
 * manager = john.getManager();
 * 
 * Once I’ve made the change for all methods of department and for all the
 * clients of person, I can remove the getDepartment accessor on person.
 */
public class B5_Hide_Delegate
{

}
