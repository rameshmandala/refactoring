// C3_Change_Value_to_Reference.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * CHANGE VALUE TO REFERENCE
 * You have a class with many equal instances that you want to replace with a
 * single object.
 * 
 * Image
 * Turn the object into a reference object.
 * 
 * Motivation
 * You can make a useful classification of objects in many systems: reference
 * objects and value objects. Reference objects are things like customer or
 * account. Each object stands for one object in the real world, and you use the
 * object identity to test whether they are equal. Value objects are things like
 * date or money. They are defined entirely through their data values. You don’t
 * mind that copies exist; you may have hundreds of “1/1/2000” objects around
 * your system. You do need to tell whether two of the objects are equal, so you
 * need to override the equals method (and the hashCode method too).
 * 
 * The decision between reference and value is not always clear. Sometimes you
 * start with a simple value with a small amount of immutable data. Then you
 * want to give it some changeable data and ensure that the changes ripple to
 * everyone referring to the object. At this point you need to turn it into a
 * reference object.
 * 
 * Mechanics
 * • Use Replace Constructor with Factory Method (304).
 * 
 * • Compile and test.
 * 
 * • Decide what object is responsible for providing access to the objects.
 * 
 * Image This may be a static dictionary or a registry object.
 * 
 * Image You may have more than one object that acts as an access point for the
 * new object.
 * 
 * • Decide whether the objects are precreated or created on the fly.
 * 
 * Image If the objects are precreated and you are retrieving them from memory,
 * you need to ensure they are loaded before they are needed.
 * 
 * • Alter the factory method to return the reference object.
 * 
 * Image If the objects are precomputed, you need to decide how to handle errors
 * if someone asks for an object that does not exist.
 * 
 * Image You may want to use Rename Method (273) on the factory to convey that
 * it returns an existing object.
 * 
 * • Compile and test.
 * 
 * Example
 * I start where I left off in the example for Replace Data Value with Object
 * (175). I have the following customer class:
 * 
 * class Customer {
 * public Customer (String name) {
 * _name = name;
 * }
 * public String getName() {
 * return _name;
 * }
 * private final String _name;
 * }
 * 
 * It is used by an order class:
 * 
 * class Order...
 * public Order (String customerName) {
 * _customer = new Customer(customerName);
 * }
 * public void setCustomer(String customerName) {
 * _customer = new Customer(customerName);
 * }
 * public String getCustomerName() {
 * return _customer.getName();
 * }
 * private Customer _customer;
 * 
 * and some client code:
 * 
 * private static int numberOfOrdersFor(Collection orders, String customer) {
 * int result = 0;
 * Iterator iter = orders.iterator();
 * while (iter.hasNext()) {
 * Order each = (Order) iter.next();
 * if (each.getCustomerName().equals(customer)) result++;
 * }
 * return result;
 * }
 * 
 * At the moment it is a value. Each order has its own customer object even if
 * they are for the same conceptual customer. I want to change this so that if
 * we have several orders for the same conceptual customer, they share a single
 * customer object. For this case this means that there should be only one
 * customer object for each customer name.
 * 
 * I begin by using Replace Constructor with Factory Method (304). This allows
 * me to take control of the creation process, which will become important
 * later. I define the factory method on customer:
 * 
 * class Customer {
 * public static Customer create (String name) {
 * return new Customer(name);
 * }
 * 
 * Then I replace the calls to the constructor with calls to the factory:
 * 
 * class Order {
 * public Order (String customer) {
 * _customer = Customer.create(customer);
 * }
 * 
 * Then I make the constructor private:
 * 
 * class Customer {
 * private Customer (String name) {
 * _name = name;
 * }
 * 
 * Now I have to decide how to access the customers. My preference is to use
 * another object. Such a situation works well with something like the line
 * items on an order. The order is responsible for providing access to the line
 * items. However, in this situation there isn’t such an obvious object. In this
 * situation I usually create a registry object to be the access point. For
 * simplicity in this example, however, I store them using a static field on
 * customer, making the customer class the access point:
 * 
 * private static Dictionary _instances = new Hashtable();
 * 
 * Then I decide whether to create customers on the fly when asked or to create
 * them in advance. I’ll use the latter. In my application start-up code I load
 * the customers that are in use. These could come from a database or from a
 * file. For simplicity I use explicit code. I can always use Substitute
 * Algorithm (139) to change it later.
 * 
 * class Customer...
 * static void loadCustomers() {
 * new Customer ("Lemon Car Hire").store();
 * new Customer ("Associated Coffee Machines").store();
 * new Customer ("Bilston Gasworks").store();
 * }
 * private void store() {
 * _instances.put(this.getName(), this);
 * }
 * 
 * Now I alter the factory method to return the precreated customer:
 * 
 * public static Customer create (String name) {
 * return (Customer) _instances.get(name);
 * }
 * 
 * Because the create method always returns an existing customer, I should make
 * this clear by using Rename Method (273).
 * 
 * class Customer...
 * public static Customer getNamed (String name) {
 * return (Customer) _instances.get(name);
 * }
 */
public class C3_Change_Value_to_Reference
{

}
