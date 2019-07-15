// B6_Remove_Middle_Man.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.B_Moving_Features_Between_Objects;

/**
 * https://www.safaribooksonline.com/library/view/refactoring-improving-the/0201485672/ch07.html#ch07lev1sec1
 * REMOVE MIDDLE MAN
 * A class is doing too much simple delegation.
 *
 * Image
 * Get the client to call the delegate directly.
 *
 * Motivation
 * In the motivation for Hide Delegate, I talked about the advantages of
 * encapsulating the use of a delegated object. There is a price for this. The
 * price is that every time the client wants to use a new feature of the
 * delegate, you have to add a simple delegating method to the server. After
 * adding features for a while, it becomes painful. The server class is just a
 * middle man, and perhaps it’s time for the client to call the delegate
 * directly.
 *
 * It’s hard to figure out what the right amount of hiding is. Fortunately, with
 * Hide Delegate and Remove Middle Man (160) it does not matter so much. You can
 * adjust your system as time goes on. As the system changes, the basis for how
 * much you hide also changes. A good encapsulation six months ago may be
 * awkward now. Refactoring means you never have to say you’re sorry—you just
 * fix it.
 *
 * Mechanics
 * • Create an accessor for the delegate.
 *
 * • For each client use of a delegate method, remove the method from the server
 * and replace the call in the client to call method on the delegate.
 *
 * • Compile and test after each method.
 *
 * Example
 * For an example I use person and department flipped the other way. I start
 * with person hiding the department:
 *
 * class Person...
 * Department _department;
 * public Person getManager() {
 * return _department.getManager();
 *
 * class Department...
 * private Person _manager;
 * public Department (Person manager) {
 * _manager = manager;
 * }
 *
 * To find a person’s manager, clients ask:
 *
 * manager = john.getManager();
 *
 * This is simple to use and encapsulates the department. However, if lots of
 * methods are doing this, I end up with too many of these simple delegations on
 * the person. That’s when it is good to remove the middle man. First I make an
 * accessor for the delegate:
 *
 * class Person...
 * public Department getDepartment() {
 * return _department;
 * }
 *
 * Then I take each method at a time. I find clients that use the method on
 * person and change it to first get the delegate. Then I use it:
 *
 * manager = john.getDepartment().getManager();
 *
 * I can then remove getManager from person. A compile shows whether I missed
 * anything.
 *
 * I may want to keep some of these delegations for convenience. I also may want
 * to hide the delegate from some clients but show it to others. That also will
 * leave some of the simple delegations in place.
 */
public class B6_Remove_Middle_Man
{

}
