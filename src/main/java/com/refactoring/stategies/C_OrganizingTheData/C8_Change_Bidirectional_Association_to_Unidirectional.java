// C8_Change_Bidirectional_Association_to_Unidirectional.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * You have a two-way association but one class no longer needs features from
 * the other.
 * 
 * Image
 * Drop the unneeded end of the association.
 * 
 * Motivation
 * Bidirectional associations are useful, but they carry a price. The price is
 * the added complexity of maintaining the two-way links and ensuring that
 * objects are properly created and removed. Bidirectional associations are not
 * natural for many programmers, so they often are a source of errors.
 * 
 * Lots of two-way links also make it easy for mistakes to lead to zombies:
 * objects that should be dead but still hang around because of a reference that
 * was not cleared.
 * 
 * Bidirectional associations force an interdependency between the two classes.
 * Any change to one class may cause a change to another. If the classes are in
 * separate packages, you get an interdependency between the packages. Many
 * interdependencies lead to a highly coupled system, in which any little change
 * leads to lots of unpredictable ramifications.
 * 
 * You should use bidirectional associations when you need to but not when you
 * don’t. As soon as you see a bidirectional association is no longer pulling
 * its weight, drop the unnecessary end.
 * 
 * Mechanics
 * • Examine all the readers of the field that holds the pointer that you wish
 * to remove to see whether the removal is feasible.
 * 
 * Image Look at direct readers and further methods that call the methods.
 * 
 * Image Consider whether it is possible to determine the other object without
 * using the pointer. If so you will be able to use Substitute Algorithm (139)
 * on the getter to allow clients to use the getting method even if there is no
 * pointer.
 * 
 * Image Consider adding the object as an argument to all methods that use the
 * field.
 * 
 * • If clients need to use the getter, use Self Encapsulate Field (171), carry
 * out Substitute Algorithm (139) on the getter, compile, and test.
 * 
 * • If clients don’t need the getter, change each user of the field so that it
 * gets the object in the field another way. Compile and test after each change.
 * 
 * • When no reader is left in the field, remove all updates to the field, and
 * remove the field.
 * 
 * Image If there are many places that assign the field, use Self Encapsulate
 * Field (171) so that they all use a single setter. Compile and test. Change
 * the setter to have an empty body. Compile and test. If that works, remove the
 * field, the setter, and all calls to the setter.
 * 
 * • Compile and test.
 * 
 * Example
 * I start from where I ended up from the example in Change Unidirectional
 * Association to Bidirectional (197). I have a customer and order with a
 * bidirectional link:
 * 
 * class Order...
 * Customer getCustomer() {
 * return _customer;
 * }
 * void setCustomer (Customer arg) {
 * if (_customer != null) _customer.friendOrders().remove(this);
 * _customer = arg;
 * if (_customer != null) _customer.friendOrders().add(this);
 * }
 * private Customer _customer;
 * 
 * class Customer...
 * void addOrder(Order arg) {
 * arg.setCustomer(this);
 * }
 * private Set _orders = new HashSet();
 * Set friendOrders() {
 * //should only be used by Order
 * return _orders;}
 * 
 * I’ve found that in my application I don’t have orders unless I already have a
 * customer,so I want to break the link from order to customer.
 * 
 * The most difficult part of this refactoring is checking that I can do it.Once
 * I know it’s safe to do,it’s easy.The issue is whether code relies on the
 * customer field’s being there.To remove the field,I need to provide an
 * alternative.
 * 
 * My first move is to study all the readers of the field and the methods that
 * use those readers.Can I find another way to provide the customer object?Often
 * this means passing in the customer as an argument for an operation.Here’s a
 * simplistic example of this:
 * 
 * class Order...
 * double getDiscountedPrice()
 * {
 * return getGrossPrice() * (1 - _customer.getDiscount());
 * }
 * 
 * changes to
 * 
 * class Order...
 * double getDiscountedPrice(Customer customer)
 * {
 * return getGrossPrice() * (1 - customer.getDiscount());
 * }
 * 
 * This works
 * particularly well
 * when the
 * behavior is
 * being called
 * by the customer,
 * because then it’
 * s easy
 * to pass
 * itself in
 * as an argument.So
 * 
 * class Customer...
 * double getPriceFor(Order order) {
 * Assert.isTrue(_orders.contains(order)); // see Introduce Assertion (267)
 * return order.getDiscountedPrice();
 * 
 * becomes
 * 
 * class Customer...
 * double getPriceFor(Order order) {
 * Assert.isTrue(_orders.contains(order));
 * return order.getDiscountedPrice(this);
 * }
 * 
 * Another alternative I consider is changing the getter so that it gets the
 * customer without using the field. If it does, I can use Substitute Algorithm
 * (139) on the body of Order.getCustomer. I might do something like this:
 * 
 * Customer getCustomer() {
 * Iterator iter = Customer.getInstances().iterator();
 * while (iter.hasNext()) {
 * Customer each = (Customer)iter.next();
 * if (each.containsOrder(this)) return each;
 * }
 * return null;
 * }
 * 
 * Slow, but it works. In a database context it may not even be that slow if I
 * use a database query. If the order class contains methods that use the
 * customer field, I can change them to use getCustomer by using Self
 * Encapsulate Field (171).
 * 
 * If I retain the accessor, the association is still bidirectional in interface
 * but is unidirectional in implementation. I remove the backpointer but retain
 * the interdependencies between the two classes.
 * 
 * If I substitute the getting method, I substitute that and leave the rest till
 * later. Otherwise I change the callers one at a time to use the customer from
 * another source. I compile and test after each change. In practice, this
 * process usually is pretty rapid. If it were complicated, I would give up on
 * this refactoring.
 * 
 * Once I’ve eliminated the readers of the field, I can work on the writers of
 * the field. This is as simple as removing any assignments to the field and
 * then removing the field. Because nobody is reading it any more, that
 * shouldn’t matter.
 */
public class C8_Change_Bidirectional_Association_to_Unidirectional
{

}
