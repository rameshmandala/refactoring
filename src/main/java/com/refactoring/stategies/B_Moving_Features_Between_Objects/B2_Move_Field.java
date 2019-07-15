// B2_Move_Field.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.B_Moving_Features_Between_Objects;

/**
 *
 */
public class B2_Move_Field
{
    /**
     * MOVE FIELD
     * A field is, or will be, used by another class more than the class on
     * which it is defined.
     * 
     * Image
     * Create a new field in the target class, and change all its users.
     * 
     * Motivation
     * Moving state and behavior between classes is the very essence of
     * refactoring. As the system develops, you find the need for new classes
     * and the need to shuffle responsibilities around. A design decision that
     * is reasonable and correct one week can become incorrect in another. That
     * is not a problem; the only problem is not to do something about it.
     * 
     * I consider moving a field if I see more methods on another class using
     * the field than the class itself. This usage may be indirect, through
     * getting and setting methods. I may choose to move the methods; this
     * decision based on interface. But if the methods seem sensible where they
     * are, I move the field.
     * 
     * Another reason for field moving is when doing Extract Class (149). In
     * that case the fields go first and then the methods.
     * 
     * Mechanics
     * • If the field is public, use Encapsulate Field (206).
     * 
     * Image If you are likely to be moving the methods that access it
     * frequently or if a lot of methods access the field, you may find it
     * useful to use Self Encapsulate Field (171)
     * 
     * • Compile and test.
     * 
     * • Create a field in the target class with getting and setting methods.
     * 
     * • Compile the target class.
     * 
     * • Determine how to reference the target object from the source.
     * 
     * Image An existing field or method may give you the target. If not, see
     * whether you can easily create a method that will do so. Failing that, you
     * need to create a new field in the source that can store the target. This
     * may be a permanent change, but you can also do it temporarily until you
     * have refactored enough to remove it.
     * 
     * • Remove the field on the source class.
     * 
     * • Replace all references to the source field with references to the
     * appropriate method on the target.
     * 
     * Image For accesses to the variable, replace the reference with a call to
     * the target object’s getting method; for assignments, replace the
     * reference with a call to the setting method.
     * 
     * Image If the field is not private, look in all the subclasses of the
     * source for references.
     * 
     * • Compile and test.
     * 
     * Example
     * Here is part of an account class:
     * 
     * class Account...
     * private AccountType _type;
     * private double _interestRate;
     * 
     * double interestForAmount_days (double amount, int days) {
     * return _interestRate * amount * days / 365;
     * }
     * 
     * I want to move the interest rate field to the account type. There are
     * several methods with that reference, of which interestForAmount_days is
     * one example.
     * 
     * I next create the field and accessors in the account type:
     * 
     * class AccountType...
     * private double _interestRate;
     * 
     * 
     * void setInterestRate (double arg) {
     * _interestRate = arg;
     * }
     * 
     * double getInterestRate () {
     * return _interestRate;
     * }
     * 
     * I can compile the new class at this point.
     * 
     * Now I redirect the methods from the account class to use the account type
     * and remove the interest rate field in the account. I must remove the
     * field to be sure that the redirection is actually happening. This way the
     * compiler helps spot any method I failed to redirect.
     * 
     * private double _interestRate;
     * 
     * double interestForAmount_days (double amount, int days) {
     * return _type.getInterestRate() * amount * days / 365;
     * }
     * 
     * Example: Using Self-Encapsulation
     * If a lot of methods use the interest rate field, I might start by using
     * Self Encapsulate Field (171):
     * 
     * class Account...
     * private AccountType _type;
     * private double _interestRate;
     * 
     * double interestForAmount_days (double amount, int days) {
     * return getInterestRate() * amount * days / 365;
     * }
     * 
     * private void setInterestRate (double arg) {
     * _interestRate = arg;
     * }
     * 
     * private double getInterestRate () {
     * return _interestRate;
     * }
     * 
     * That way I only need to do the redirection for the accessors:
     * 
     * double interestForAmountAndDays (double amount, int days) {
     * return getInterestRate() * amount * days / 365;
     * }
     * 
     * private void setInterestRate (double arg) {
     * _type.setInterestRate(arg);
     * }
     * 
     * private double getInterestRate () {
     * return _type.getInterestRate();
     * }
     * 
     * I can redirect the clients of the accessors to use the new object later
     * if I want. Using self-encapsulation allows me to take a smaller step.
     * This is useful if I’m doing a lot of things with the class. In
     * particular, it simplifies use Move Method (142) to move methods to the
     * target class. If they refer to the accessor, such references don’t need
     * to change.
     */
}
