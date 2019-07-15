package com.refactoring.stategies.A_ComposingMethods;

public class A5_Replace_Method_with_Method_Object
{
    /*
     * You have a long method that uses local variables in such a way that you
     * cannot apply Extract Method (110).
     * 
     * Turn the method into its own object so that all the local variables
     * become fields on that object. You can then decompose the method into
     * other methods on the same object.
     * 
     * class Order...
     * double price() {
     * double primaryBasePrice;
     * double secondaryBasePrice;
     * double tertiaryBasePrice;
     * // long computation;
     * ...
     * }
     * 
     * Image
     * Image
     * Motivation
     * In this book I emphasize the beauty of small methods. By extracting
     * pieces out of a large method, you make things much more comprehensible.
     * 
     * The difficulty in decomposing a method lies in local variables. If they
     * are rampant, decomposition can be difficult. Using Replace Temp with
     * Query (120) helps to reduce this burden, but occasionally you may find
     * you cannot break down a method that needs breaking. In this case you
     * reach deep into the tool bag and get out your method object [Beck].
     * 
     * Applying Replace Method with Method Object (135) turns all these local
     * variables into fields on the method object. You can then use Extract
     * Method (110) on this new object to create additional methods that break
     * down the original method.
     * 
     * Mechanics
     * Stolen shamelessly from Beck [Beck].
     * 
     * • Create a new class, name it after the method.
     * 
     * • Give the new class a final field for the object that hosted the
     * original method (the source object) and a field for each temporary
     * variable and each parameter in the method.
     * 
     * • Give the new class a constructor that takes the source object and each
     * parameter.
     * 
     * • Give the new class a method named “compute.”
     * 
     * • Copy the body of the original method into compute. Use the source
     * object field for any invocations of methods on the original object.
     * 
     * • Compile.
     * 
     * • Replace the old method with one that creates the new object and calls
     * compute.
     * 
     * Now comes the fun part. Because all the local variables are now fields,
     * you can freely decompose the method without having to pass any
     * parameters.
     * 
     * Example
     * A proper example of this requires a long chapter, so I’m showing this
     * refactoring for a method that doesn’t need it. (Don’t ask what the logic
     * of this method is, I made it up as I went along.)
     * 
     * Class Account
     * int gamma (int inputVal, int quantity, int yearToDate) {
     * int importantValue1 = (inputVal * quantity) + delta();
     * int importantValue2 = (inputVal * yearToDate) + 100;
     * if ((yearToDate - importantValue1) > 100)
     * importantValue2 -= 20;
     * int importantValue3 = importantValue2 * 7;
     * // and so on.
     * return importantValue3 - 2 * importantValue1;
     * }
     * 
     * To turn this into a method object, I begin by declaring a new class. I
     * provide a final field for the original object and a field for each
     * parameter and temporary variable in the method.
     * 
     * class Gamma...
     * private final Account _account;
     * private int inputVal;
     * private int quantity;
     * private int yearToDate;
     * private int importantValue1;
     * private int importantValue2;
     * private int importantValue3;
     * 
     * I usually use the underscore prefix convention for marking fields. But to
     * keep small steps I’ll leave the names as they are for the moment.
     * 
     * I add a constructor:
     * 
     * Gamma (Account source, int inputValArg, int quantityArg, int
     * yearToDateArg) {
     * _account = source;
     * inputVal = inputValArg;
     * quantity = quantityArg;
     * yearToDate = yearToDateArg;
     * }
     * 
     * Now I can move the original method over. I need to modify any calls of
     * features of account to use the _account field
     * 
     * int compute () {
     * importantValue1 = (inputVal * quantity) + _account.delta();
     * importantValue2 = (inputVal * yearToDate) + 100;
     * if ((yearToDate - importantValue1) > 100)
     * importantValue2 -= 20;
     * int importantValue3 = importantValue2 * 7;
     * // and so on.
     * return importantValue3 - 2 * importantValue1;
     * }
     * 
     * I then modify the old method to delegate to the method object:
     * 
     * int gamma (int inputVal, int quantity, int yearToDate) {
     * return new Gamma(this, inputVal, quantity, yearToDate).compute();
     * }
     * 
     * That’s the essential refactoring. The benefit is that I can now easily
     * use Extract Method (110) on the compute method without ever worrying
     * about the argument’s passing:
     * 
     * int compute () {
     * importantValue1 = (inputVal * quantity) + _account.delta();
     * importantValue2 = (inputVal * yearToDate) + 100;
     * importantThing();
     * int importantValue3 = importantValue2 * 7;
     * // and so on.
     * return importantValue3 - 2 * importantValue1;
     * }
     * 
     * void importantThing() {
     * if ((yearToDate - importantValue1) > 100)
     * importantValue2 -= 20;
     * }
     */
}
