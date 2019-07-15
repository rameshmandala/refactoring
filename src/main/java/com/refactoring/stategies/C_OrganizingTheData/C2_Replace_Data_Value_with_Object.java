// C2_Replace_Data_Value_with_Object.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * REPLACE DATA VALUE WITH OBJECT
 * You have a data item that needs additional data or behavior.
 * 
 * Turn the data item into an object.
 * 
 * Image
 * Motivation
 * Often in early stages of development you make decisions about representing
 * simple facts as simple data items. As development proceeds you realize that
 * those simple items aren’t so simple anymore. A telephone number may be
 * represented as a string for a while, but later you realize that the telephone
 * needs special behavior for formatting, extracting the area code, and the
 * like. For one or two items you may put the methods in the owning object, but
 * quickly the code smells of duplication and feature envy. When the smell
 * begins, turn the data value into an object.
 * 
 * Mechanics
 * • Create the class for the value. Give it a final field of the same type as
 * the value in the source class. Add a getter and a constructor that takes the
 * field as an argument.
 * 
 * • Compile.
 * 
 * • Change the type of the field in the source class to the new class.
 * 
 * • Change the getter in the source class to call the getter in the new class.
 * 
 * • If the field is mentioned in the source class constructor, assign the field
 * using the constructor of the new class.
 * 
 * • Change the getting method to create a new instance of the new class.
 * 
 * • Compile and test.
 * 
 * • You may now need to use Change Value to Reference (179) on the new object.
 * 
 * Example
 * I start with an order class that has stored the customer of the order as a
 * string and wants to turn the customer into an object. This way I have
 * somewhere to store data, such as an address or credit rating, and useful
 * behavior that uses this information.
 * 
 * class Order...
 * public Order (String customer) {
 * _customer = customer;
 * }
 * public String getCustomer() {
 * return _customer;
 * }
 * public void setCustomer(String arg) {
 * _customer = arg;
 * }
 * private String _customer;
 * 
 * Some client code that uses this looks like
 * 
 * private static int numberOfOrdersFor(Collection orders, String customer) {
 * int result = 0;
 * Iterator iter = orders.iterator();
 * while (iter.hasNext()) {
 * Order each = (Order) iter.next();
 * if (each.getCustomer().equals(customer)) result++;
 * }
 * return result;
 * }
 * 
 * First I create the new customer class. I give it a final field for a string
 * attribute, because that is what the order currently uses. I call it name,
 * because that seems to be what the string is used for. I also add a getting
 * method and provide a constructor that uses the attribute:
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
 * Now I change the type of the customer field and change methods that reference
 * it to use the appropriate references on the customer class. The getter and
 * constructor are obvious. For the setter I create a new customer:
 * 
 * class Order...
 * public Order (String customer) {
 * _customer = new Customer(customer);
 * }
 * public String getCustomer() {
 * return _customer.getName();
 * }
 * private Customer _customer;
 * 
 * public void setCustomer(String arg) {
 * _customer = new Customer(arg);
 * }
 * 
 * The setter creates a new customer because the old string attribute was a
 * value object, and thus the customer currently also is a value object. This
 * means that each order has its own customer object. As a rule value objects
 * should be immutable; this avoids some nasty aliasing bugs. Later on I will
 * want customer to be a reference object, but that’s another refactoring. At
 * this point I can compile and test.
 * 
 * Now I look at the methods on order that manipulate customer and make some
 * changes to make the new state of affairs clearer. With the getter I use
 * Rename Method (273) to make it clear that it is the name not the object that
 * is returned:
 * 
 * public String getCustomerName() {
 * return _customer.getName();
 * }
 * 
 * On the constructor and setter, I don’t need to change the signature, but the
 * name of the arguments should change:
 * 
 * public Order (String customerName) {
 * _customer = new Customer(customerName);
 * }
 * public void setCustomer(String customerName) {
 * _customer = new Customer(customerName);
 * }
 * 
 * Further refactoring may well cause me to add a new constructor and setter
 * that takes an existing customer.
 * 
 * This finishes this refactoring, but in this case, as in many others, there is
 * another step. If I want to add such things as credit ratings and addresses to
 * our customer, I cannot do so now. This is because the customer is treated as
 * a value object. Each order has its own customer object. To give a customer
 * these attributes I need to apply Change Value to Reference (179) to the
 * customer so that all orders for the same customer share the same customer
 * object. You’ll find this example continued there.
 */
public class C2_Replace_Data_Value_with_Object
{

}
