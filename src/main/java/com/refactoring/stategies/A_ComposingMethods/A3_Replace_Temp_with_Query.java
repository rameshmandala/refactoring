package com.refactoring.stategies.A_ComposingMethods;

/**
 * you are using a temporary variable to hold the result of an expression.
 *
 * Extract the expression into a method. Replace all references to the temp with
 * the new method. The new method can then be used in other methods.
 *
 * double basePrice = _quantity * _itemPrice;
 * if (basePrice > 1000)
 * return basePrice * 0.95;
 * else
 * return basePrice * 0.98;
 *
 * Image
 * if (basePrice() > 1000)
 * return basePrice() * 0.95;
 * else
 * return basePrice() * 0.98;
 * ...
 * double basePrice() {
 * return _quantity * _itemPrice;
 * }
 *
 * Motivation
 * The problem with temps is that they are temporary and local. Because they can
 * be seen only in the context of the method in which they are used, temps tend
 * to encourage longer methods, because that’s the only way you can reach the
 * temp. By replacing the temp with a query method, any method in the class can
 * get at the information. That helps a lot in coming up with cleaner code for
 * the class.
 *
 * Replace Temp with Query often is a vital step before Extract Method (110).
 * Local variables make it difficult to extract, so replace as many variables as
 * you can with queries.
 *
 * The straightforward cases of this refactoring are those in which temps are
 * only assigned to once and those in which the expression that generates the
 * assignment is free of side effects. Other cases are trickier but possible.
 * You may need to use Split Temporary Variable (128) or Separate Query from
 * Modifier (279) first to make things easier. If the temp is used to collect a
 * result (such as summing over a loop), you need to copy some logic into the
 * query method.
 *
 * Mechanics
 * Here is the simple case:
 *
 * • Look for a temporary variable that is assigned to once.
 *
 * Image If a temp is set more than once consider Split Temporary Variable
 * (128).
 *
 * • Declare the temp as final.
 *
 * • Compile.
 *
 * Image This will ensure that the temp is only assigned to once.
 *
 * • Extract the right-hand side of the assignment into a method.
 *
 * Image Initially mark the method as private. You may find more use for it
 * later, but you can easily relax the protection later.
 *
 * Image Ensure the extracted method is free of side effects, that is, it does
 * not modify any object. If it is not free of side effects, use Separate Query
 * from Modifier (279).
 *
 * • Compile and test.
 *
 * • Use Replace Temp with Inline Temp (119) on the temp.
 *
 * Temps often are used to store summary information in loops. The entire loop
 * can be extracted into a method; this removes several lines of noisy code.
 * Sometimes a loop may be used to sum up multiple values, as in the example on
 * page 26. In this case, duplicate the loop for each temp so that you can
 * replace each temp with a query. The loop should be very simple, so there is
 * little danger in duplicating the code.
 *
 * You may be concerned about performance in this case. As with other
 * performance issues, let it slide for the moment. Nine times out of ten, it
 * won’t matter. When it does matter, you will fix the problem during
 * optimization. With your code better factored, you will often find more
 * powerful optimizations, which you would have missed without refactoring. If
 * worse comes to worse, it’s very easy to put the temp back.
 */
public class A3_Replace_Temp_with_Query
{
    /*
     * Example
     * I start with a simple method:
     * 
     * double getPrice() {
     * int basePrice = _quantity * _itemPrice;
     * double discountFactor;
     * if (basePrice > 1000) discountFactor = 0.95;
     * else discountFactor = 0.98;
     * return basePrice * discountFactor;
     * }
     * 
     * I’m inclined to replace both temps, one at a time.
     * 
     * Although it’s pretty clear in this case, I can test that they are
     * assigned only to once by declaring them as final:
     * 
     * double getPrice() {
     * final int basePrice = _quantity * _itemPrice;
     * final double discountFactor;
     * if (basePrice > 1000) discountFactor = 0.95;
     * else discountFactor = 0.98;
     * return basePrice * discountFactor;
     * }
     * 
     * Compiling will then alert me to any problems. I do this first, because if
     * there is a problem, I shouldn’t be doing this refactoring. I replace the
     * temps one at a time. First I extract the right-hand side of the
     * assignment:
     * 
     * double getPrice() {
     * final int basePrice = basePrice();
     * final double discountFactor;
     * if (basePrice > 1000) discountFactor = 0.95;
     * else discountFactor = 0.98;
     * return basePrice * discountFactor;
     * }
     * 
     * private int basePrice() {
     * return _quantity * _itemPrice;
     * }
     * 
     * I compile and test, then I begin with Replace Temp with Inline Temp
     * (119). First I replace the first reference to the temp:
     * 
     * double getPrice() {
     * final int basePrice = basePrice();
     * final double discountFactor;
     * if (basePrice() > 1000) discountFactor = 0.95;
     * else discountFactor = 0.98;
     * return basePrice * discountFactor;
     * }
     * 
     * Compile and test and do the next (sounds like a caller at a line dance).
     * Because it’s the last, I also remove the temp declaration:
     * 
     * double getPrice() {
     * final double discountFactor;
     * if (basePrice() > 1000) discountFactor = 0.95;
     * else discountFactor = 0.98;
     * return basePrice() * discountFactor;
     * }
     * 
     * With that gone I can extract discountFactor in a similar way:
     * 
     * double getPrice() {
     * final double discountFactor = discountFactor();
     * return basePrice() * discountFactor;
     * }
     * 
     * private double discountFactor() {
     * if (basePrice() > 1000) return 0.95;
     * else return 0.98;
     * }
     * 
     * See how it would have been difficult to extract discountFactor if I had
     * not replaced basePrice with a query.
     * 
     * The getPrice method ends up as follows:
     * 
     * double getPrice() {
     * return basePrice() * discountFactor();
     * }
     * 
     * INTRODUCE EXPLAINING VARIABLE
     * You have a complicated expression.
     * 
     * Put the result of the expression, or parts of the expression, in a
     * temporary variable with a name that explains the purpose.
     * 
     * if ( (platform.toUpperCase().indexOf("MAC") > -1)&&
     * (browser.toUpperCase().indexOf("IE") > -1)&&
     * wasInitialized() && resize > 0 )
     * {
     * // do something
     * }
     * 
     * Image
     * final boolean isMacOs = platform.toUpperCase().indexOf("MAC") > -1;
     * final boolean isIEBrowser = browser.toUpperCase().indexOf("IE") > -1;
     * final boolean wasResized = resize > 0;
     * 
     * if (isMacOs && isIEBrowser && wasInitialized() && wasResized) {
     * // do something
     * }
     * 
     * Motivation
     * Expressions can become very complex and hard to read. In such situations
     * temporary variables can be helpful to break down the expression into
     * something more manageable.
     * 
     * Introduce Explaining Variable is particularly valuable with conditional
     * logic in which it is useful to take each clause of a condition and
     * explain what the condition means with a well-named temp. Another case is
     * a long algorithm, in which each step in the computation can be explained
     * with a temp.
     * 
     * Introduce Explaining Variable is a very common refactoring, but I confess
     * I don’t use it that much. I almost always prefer to use Extract Method
     * (110) if I can. A temp is useful only within the context of one method. A
     * method is useable throughout the object and to other objects. There are
     * times, however, when local variables make it difficult to use Extract
     * Method (110). That’s when I use Introduce Explaining Variable (124).
     * 
     * Mechanics
     * • Declare a final temporary variable, and set it to the result of part of
     * the complex expression.
     * 
     * • Replace the result part of the expression with the value of the temp.
     * 
     * Image If the result part of the expression is repeated, you can replace
     * the repeats one at a time.
     * 
     * • Compile and test.
     * 
     * • Repeat for other parts of the expression.
     * 
     * Example
     * I start with a simple calculation:
     * 
     * double price() {
     * // price is base price - quantity discount + shipping
     * return _quantity * _itemPrice -
     * Math.max(0, _quantity - 500) * _itemPrice * 0.05 +
     * Math.min(_quantity * _itemPrice * 0.1, 100.0);
     * }
     * 
     * Simple it may be, but I can make it easier to follow. First I identify
     * the base price as the quantity times the item price. I can turn that part
     * of the calculation into a temp:
     * 
     * double price() {
     * // price is base price - quantity discount + shipping
     * final double basePrice = _quantity * _itemPrice;
     * return basePrice -
     * Math.max(0, _quantity - 500) * _itemPrice * 0.05 +
     * Math.min(_quantity * _itemPrice * 0.1, 100.0);
     * }
     * 
     * Quantity times item price is also used later, so I can substitute with
     * the temp there as well:
     * 
     * double price() {
     * // price is base price - quantity discount + shipping
     * final double basePrice = _quantity * _itemPrice;
     * return basePrice -
     * Math.max(0, _quantity - 500) * _itemPrice * 0.05 +
     * Math.min(basePrice * 0.1, 100.0);
     * }
     * 
     * Next I take the quantity discount:
     * 
     * double price() {
     * // price is base price - quantity discount + shipping
     * final double basePrice = _quantity * _itemPrice;
     * final double quantityDiscount = Math.max(0, _quantity - 500) * _itemPrice
     * * 0.05;
     * return basePrice - quantityDiscount +
     * Math.min(basePrice * 0.1, 100.0);
     * }
     * 
     * Finally, I finish with the shipping. As I do that, I can remove the
     * comment, too, because now it doesn’t say anything the code doesn’t say:
     * 
     * double price() {
     * final double basePrice = _quantity * _itemPrice;
     * final double quantityDiscount = Math.max(0, _quantity - 500) * _itemPrice
     * * 0.05;
     * final double shipping = Math.min(basePrice * 0.1, 100.0);
     * return basePrice - quantityDiscount + shipping;
     * }
     */
}
