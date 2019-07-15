// C7_Change_Unidirectional_Association_to_Bidirectional.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * You have two classes that need to use each other’s features, but there is
 * only a one-way link.
 * 
 * Image
 * Add back pointers, and change modifiers to update both sets.
 * 
 * Motivation
 * You may find that you have initially set up two classes so that one class
 * refers to the other. Over time you may find that a client of the referred
 * class needs to get to the objects that refer to it. This effectively means
 * navigating backward along the pointer. Pointers are one-way links, so you
 * can’t do this. Often you can get around this problem by finding another
 * route. This may cost in computation but is reasonable, and you can have a
 * method on the referred class that uses this behavior. Sometimes, however,
 * this is not easy, and you need to set up a two-way reference, sometimes
 * called a back pointer. If you aren’t used to back pointers, it’s easy to
 * become tangled up using them. Once you get used to the idiom, however, it is
 * not too complicated.
 * 
 * The idiom is awkward enough that you should have tests, at least until you
 * are comfortable with the idiom. Because I usually don’t bother testing
 * accessors (the risk is not high enough), this is the rare case of a
 * refactoring that adds a test.
 * 
 * This refactoring uses back pointers to implement bidirectionality. Other
 * techniques, such as link objects, require other refactorings.
 * 
 * Mechanics
 * • Add a field for the back pointer.
 * 
 * • Decide which class will control the association.
 * 
 * • Create a helper method on the noncontrolling side of the association. Name
 * this method to clearly indicate its restricted use.
 * 
 * • If the existing modifier is on the controlling side, modify it to update
 * the back pointers.
 * 
 * • If the existing modifier is on the controlled side, create a controlling
 * method on the controlling side and call it from the existing modifier.
 * 
 * Example
 * A simple program has an order that refers to a customer:
 * 
 * class Order...
 * Customer getCustomer() {
 * return _customer;
 * }
 * void setCustomer (Customer arg) {
 * _customer = arg;
 * }
 * Customer _customer;
 * 
 * The customer class has no reference to the order.
 * 
 * I start the refactoring by adding a field to the customer. As a customer can
 * have several orders, so this field is a collection. Because I don’t want a
 * customer to have the same order more than once in its collection, the correct
 * collection is a set:
 * 
 * class Customer {
 * private Set _orders = new HashSet();
 * 
 * Now I need to decide which class will take charge of the association. I
 * prefer to let one class take charge because it keeps all the logic for
 * manipulating the association in one place. My decision process runs as
 * follows:
 * 
 * 1. If both objects are reference objects and the association is one to many,
 * then the object that has the one reference is the controller. (That is, if
 * one customer has many orders, the order controls the association.)
 * 
 * 2. If one object is a component of the other, the composite should control
 * the association.
 * 
 * 3. If both objects are reference objects and the association is many to many,
 * it doesn’t matter whether the order or the customer controls the association.
 * 
 * Because the order will take charge, I need to add a helper method to the
 * customer that allows direct access to the orders collection. The order’s
 * modifier will use this to synchronize both sets of pointers. I use the name
 * friendOrders to signal that this method is to be used only in this special
 * case. I also minimize its visibility by making it package visibility if at
 * all possible. I do have to make it public if the other class is in another
 * package:
 * 
 * class Customer...
 * Set friendOrders() {
 * // should only be used by Order when modifying the association
 * return _orders;}
 * 
 * Now I update the modifier to update the back pointers:
 * 
 * class Order...
 * void setCustomer (Customer arg) ...
 * if (_customer != null)
 * {
 * _customer.friendOrders().remove(this);
 * }
 * _customer = arg;
 * if (_customer != null)
 * {
 * _customer.friendOrders().add(this);
 * }
 * }
 * 
 * The exact
 * code in
 * the controlling
 * modifier varies
 * with the
 * multiplicity of
 * the association.
 * If the
 * customer is
 * not allowed
 * to be null,
 * I can
 * forgo the null checks,
 * but I
 * need to check for a null
 * argument. The basic
 * pattern is
 * always the same,however:
 * first tell
 * the other
 * object to
 * remove its
 * pointer to you,
 * set your
 * pointer to the new object,
 * and then
 * tell the new
 * object to
 * add a
 * pointer to
 * you.
 * 
 * If you
 * want to
 * modify the
 * link through
 * the customer, let
 * it call
 * the controlling method:
 * 
 * class Customer...
 * void addOrder(Order arg)
 * {
 * arg.setCustomer(this);
 * }
 * 
 * If an
 * order can
 * have many customers,
 * you have
 * a many-to-many case,
 * and the
 * methods look like this:
 * 
 * class Order... //controlling methods
 * void addCustomer(Customer arg)
 * {
 * arg.friendOrders().add(this);
 * _customers.add(arg);
 * }
 * 
 * void removeCustomer(Customer arg)
 * {
 * arg.friendOrders().remove(this);
 * _customers.remove(arg);
 * }
 * 
 * class Customer...
 * void addOrder(Order arg)
 * {
 * arg.addCustomer(this);
 * }
 * 
 * void removeOrder(Order arg)
 * {
 * arg.removeCustomer(this);
 * }
 */
public class C7_Change_Unidirectional_Association_to_Bidirectional
{

}
