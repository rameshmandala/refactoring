// F1001_Pull_Up_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * You have methods with identical results on subclasses.
 * 
 * Move them to the superclass.
 * 
 * Image
 * Motivation
 * Eliminating duplicate behavior is important. Although two duplicate methods
 * work fine as they are, they are nothing more than a breeding ground for bugs
 * in the future. Whenever there is duplication, you face the risk that an
 * alteration to one will not be made to the other. Usually it is difficult to
 * find the duplicates.
 * 
 * The easiest case of using Pull Up Method occurs when the methods have the
 * same body, implying there’s been a copy and paste. Of course it’s not always
 * as obvious as that. You could just do the refactoring and see if the tests
 * croak, but that puts a lot of reliance on your tests. I usually find it
 * valuable to look for the differences; often they show up behavior that I
 * forgot to test for.
 * 
 * Often Pull Up Method comes after other steps. You see two methods in
 * different classes that can be parameterized in such a way that they end up as
 * essentially the same method. In that case the smallest step is to
 * parameterize each method separately and then generalize them. Do it in one go
 * if you feel confident enough.
 * 
 * A special case of the need for Pull Up Method occurs when you have a subclass
 * method that overrides a superclass method yet does the same thing.
 * 
 * The most awkward element of Pull Up Method is that the body of the methods
 * may refer to features that are on the subclass but not on the superclass. If
 * the feature is a method, you can either generalize the other method or create
 * an abstract method in the superclass. You may need to change a method’s
 * signature or create a delegating method to get this to work.
 * 
 * If you have two methods that are similar but not the same, you may be able to
 * use Form Template Method (345).
 * 
 * Mechanics
 * • Inspect the methods to ensure they are identical.
 * 
 * Image If the methods look like they do the same thing but are not identical,
 * use algorithm substitution on one of them to make them identical.
 * 
 * • If the methods have different signatures, change the signatures to the one
 * you want to use in the superclass.
 * 
 * • Create a new method in the superclass, copy the body of one of the methods
 * to it, adjust, and compile.
 * 
 * Image If you are in a strongly typed language and the method calls another
 * method that is present on both subclasses but not the superclass, declare an
 * abstract method on the superclass.
 * 
 * Image If the method uses a subclass field, use Pull Up Field (320) or Self
 * Encapsulate Field (171) and declare and use an abstract getting method.
 * 
 * • Delete one subclass method.
 * 
 * • Compile and test.
 * 
 * • Keep deleting subclass methods and testing until only the superclass method
 * remains.
 * 
 * • Take a look at the callers of this method to see whether you can change a
 * required type to the superclass.
 * 
 * Example
 * Consider a customer with two subclasses: regular customer and preferred
 * customer.
 * 
 * Image
 * The createBill method is identical for each class:
 * 
 * void createBill (date Date) {
 * double chargeAmount = chargeFor (lastBillDate, date);
 * addBill (date, charge);
 * }
 * 
 * I can’t move the method up into the superclass, because chargeFor is
 * different on each subclass. First I have to declare it on the superclass as
 * abstract:
 * 
 * class Customer...
 * abstract double chargeFor(date start, date end)
 * 
 * Then I can copy createBill from one of the subclasses. I compile with that in
 * place and then remove the createBill method from one of the subclasses,
 * compile, and test. I then remove it from the other, compile, and test:
 * 
 * Image
 */
public class F1001_Pull_Up_Method
{

}
