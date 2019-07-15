// E1004_Parameterize_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * Several methods do similar things but with different values contained in the
 * method body.
 * 
 * Image
 * Create one method that uses a parameter for the different values.
 * 
 * Motivation
 * You may see a couple of methods that do similar things but vary depending on
 * a few values. In this case you can simplify matters by replacing the separate
 * methods with a single method that handles the variations by parameters. Such
 * a change removes duplicate code and increases flexibility, because you can
 * deal with other variations by adding parameters.
 * 
 * Mechanics
 * • Create a parameterized method that can be substituted for each repetitive
 * method.
 * 
 * • Compile.
 * 
 * • Replace one old method with a call to the new method.
 * 
 * • Compile and test.
 * 
 * • Repeat for all the methods, testing after each one.
 * 
 * You may find that you cannot do this for the whole method, but you can for a
 * fragment of a method. In this case first extract the fragment into a method,
 * then parameterize that method.
 * 
 * Example
 * The simplest case is methods along the following lines:
 * 
 * class Employee {
 * void tenPercentRaise () {
 * salary *= 1.1;
 * }
 * 
 * void fivePercentRaise () {
 * salary *= 1.05;
 * }
 * 
 * which can be replaced with
 * 
 * void raise (double factor) {
 * salary *= (1 + factor);
 * }
 * 
 * Of course that is so simple that anyone would spot it.
 * 
 * A less obvious case is as follows:
 * 
 * protected Dollars baseCharge() {
 * double result = Math.min(lastUsage(),100) * 0.03;
 * if (lastUsage() > 100) {
 * result += (Math.min (lastUsage(),200) - 100) * 0.05;
 * };
 * if (lastUsage() > 200) {
 * result += (lastUsage() - 200) * 0.07;
 * };
 * return new Dollars (result);
 * }
 * 
 * this can be replaced with
 * 
 * protected Dollars baseCharge() {
 * double result = usageInRange(0, 100) * 0.03;
 * result += usageInRange (100,200) * 0.05;
 * result += usageInRange (200, Integer.MAX_VALUE) * 0.07;
 * return new Dollars (result);
 * }
 * 
 * protected int usageInRange(int start, int end) {
 * if (lastUsage() > start) return Math.min(lastUsage(),end) - start;
 * else return 0;
 * }
 * 
 * The trick is to spot code that is repetitive on the basis of a few values
 * that can be passed in as parameters.
 */
public class E1004_Parameterize_Method
{

}
