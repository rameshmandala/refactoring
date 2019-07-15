// E1007_Replace_Parameter_with_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * An object invokes a method, then passes the result as a parameter for a
 * method. The receiver can also invoke this method.
 * 
 * Remove the parameter and let the receiver invoke the method.
 * 
 * int basePrice = _quantity * _itemPrice;
 * discountLevel = getDiscountLevel();
 * double finalPrice = discountedPrice (basePrice, discountLevel);
 * 
 * Image
 * int basePrice = _quantity * _itemPrice;
 * double finalPrice = discountedPrice (basePrice);
 * 
 * Motivation
 * If a method can get a value that is passed in as parameter by another means,
 * it should. Long parameter lists are difficult to understand, and we should
 * reduce them as much as possible.
 * 
 * One way of reducing parameter lists is to look to see whether the receiving
 * method can make the same calculation. If an object is calling a method on
 * itself, and the calculation for the parameter does not reference any of the
 * parameters of the calling method, you should be able to remove the parameter
 * by turning the calculation into its own method. This is also true if you are
 * calling a method on a different object that has a reference to the calling
 * object.
 * 
 * You can’t remove the parameter if the calculation relies on a parameter of
 * the calling method, because that parameter may change with each call (unless,
 * of course, that parameter can be replaced with a method). You also can’t
 * remove the parameter if the receiver does not have a reference to the sender,
 * and you don’t want to give it one.
 * 
 * In some cases the parameter may be there for a future parameterization of the
 * method. In this case I would still get rid of it. Deal with the
 * parameterization when you need it; you may find out that you don’t have the
 * right parameter anyway. I would make an exception to this rule only when the
 * resulting change in the interface would have painful consequences around the
 * whole program, such as a long build or changing of a lot of embedded code. If
 * this worries you, look into how painful such a change would really be. You
 * should also look to see whether you can reduce the dependencies that cause
 * the change to be so painful. Stable interfaces are good, but freezing a poor
 * interface is a problem.
 * 
 * Mechanics
 * • If necessary, extract the calculation of the parameter into a method.
 * 
 * • Replace references to the parameter in method bodies with references to the
 * method.
 * 
 * • Compile and test after each replacement.
 * 
 * • Use Remove Parameter (277) on the parameter.
 * 
 * Example
 * Another unlikely variation on discounting orders is as follows:
 * 
 * public double getPrice() {
 * int basePrice = _quantity * _itemPrice;
 * int discountLevel;
 * if (_quantity > 100) discountLevel = 2;
 * else discountLevel = 1;
 * double finalPrice = discountedPrice (basePrice, discountLevel);
 * return finalPrice;
 * }
 * 
 * private double discountedPrice (int basePrice, int discountLevel) {
 * if (discountLevel == 2) return basePrice * 0.1;
 * else return basePrice * 0.05;
 * }
 * 
 * I can begin by extracting the calculation of the discount level:
 * 
 * public double getPrice() {
 * int basePrice = _quantity * _itemPrice;
 * int discountLevel = getDiscountLevel();
 * double finalPrice = discountedPrice (basePrice, discountLevel);
 * return finalPrice;
 * }
 * 
 * private int getDiscountLevel() {
 * if (_quantity > 100) return 2;
 * else return 1;
 * }
 * 
 * I then replace references to the parameter in discountedPrice:
 * 
 * private double discountedPrice (int basePrice, int discountLevel) {
 * if (getDiscountLevel() == 2) return basePrice * 0.1;
 * else return basePrice * 0.05;
 * }
 * 
 * Then I can use Remove Parameter (277):
 * 
 * public double getPrice() {
 * int basePrice = _quantity * _itemPrice;
 * int discountLevel = getDiscountLevel();
 * double finalPrice = discountedPrice (basePrice);
 * return finalPrice;
 * }
 * 
 * private double discountedPrice (int basePrice) {
 * if (getDiscountLevel() == 2) return basePrice * 0.1;
 * else return basePrice * 0.05;
 * }
 * 
 * I can now get rid of the temp:
 * 
 * public double getPrice() {
 * int basePrice = _quantity * _itemPrice;
 * double finalPrice = discountedPrice (basePrice);
 * return finalPrice;
 * }
 * 
 * Then it’s time to get rid of the other parameter and its temp. I am left with
 * 
 * public double getPrice() {
 * return discountedPrice ();
 * }
 * 
 * private double discountedPrice () {
 * if (getDiscountLevel() == 2) return getBasePrice() * 0.1;
 * else return getBasePrice() * 0.05;
 * }
 * 
 * private double getBasePrice() {
 * return _quantity * _itemPrice;
 * }
 * 
 * so I might as well use Inline Method (117) on discountedPrice:
 * 
 * private double getPrice () {
 * if (getDiscountLevel() == 2) return getBasePrice() * 0.1;
 * else return getBasePrice() * 0.05;
 * }
 */
public class E1007_Replace_Parameter_with_Method
{

}
