package com.refactoring.stategies.A_ComposingMethods;

public class A7_Remove_Assignments_to_Parameters
{
    /**
     * REMOVE ASSIGNMENTS TO PARAMETERS
     * The code assigns to a parameter.
     * 
     * Use a temporary variable instead.
     * 
     * int discount (int inputVal, int quantity, int yearToDate) {
     * if (inputVal > 50) inputVal -= 2;
     * 
     * Image
     * int discount (int inputVal, int quantity, int yearToDate) {
     * int result = inputVal;
     * if (inputVal > 50) result -= 2;
     * 
     * Motivation
     * First let me make sure we are clear on the phrase “assigns to a
     * parameter.” This means that if you pass in an object named foo, in the
     * parameter, assigning to the parameter means to change foo to refer to a
     * different object. I have no problems with doing something to the object
     * that was passed in; I do that all the time. I just object to changing foo
     * to refer to another object entirely:
     * 
     * void aMethod(Object foo) {
     * foo.modifyInSomeWay(); // that's OK
     * foo = anotherObject; // trouble and despair will follow you
     * 
     * The reason I don’t like this comes down to lack of clarity and to
     * confusion between pass by value and pass by reference. Java uses pass by
     * value exclusively (see later), and this discussion is based on that
     * usage.
     * 
     * With pass by value, any change to the parameter is not reflected in the
     * calling routine. Those who have used pass by reference will probably find
     * this confusing.
     * 
     * The other area of confusion is within the body of the code itself. It is
     * much clearer if you use only the parameter to represent what has been
     * passed in, because that is a consistent usage.
     * 
     * In Java, don’t assign to parameters, and if you see code that does, apply
     * Remove Assignments to Parameters.
     * 
     * Of course this rule does not necessarily apply to other languages that
     * use output parameters, although even with these languages I prefer to use
     * output parameters as little as possible.
     * 
     * Mechanics
     * • Create a temporary variable for the parameter.
     * 
     * • Replace all references to the parameter, made after the assignment, to
     * the temporary variable.
     * 
     * • Change the assignment to assign to the temporary variable.
     * 
     * • Compile and test.
     * 
     * Image If the semantics are call by reference, look in the calling method
     * to see whether the parameter is used again afterward. Also see how many
     * call by reference parameters are assigned to and used afterward in this
     * method. Try to pass a single value back as the return value. If there is
     * more than one, see whether you can turn the data clump into an object, or
     * create separate methods.
     * 
     * Example
     * I start with the following simple routine:
     * 
     * int discount (int inputVal, int quantity, int yearToDate) {
     * if (inputVal > 50) inputVal -= 2;
     * if (quantity > 100) inputVal -= 1;
     * if (yearToDate > 10000) inputVal -= 4;
     * return inputVal;
     * }
     * 
     * Replacing with a temp leads to
     * 
     * int discount (int inputVal, int quantity, int yearToDate) {
     * int result = inputVal;
     * if (inputVal > 50) result -= 2;
     * if (quantity > 100) result -= 1;
     * if (yearToDate > 10000) result -= 4;
     * return result;
     * }
     * 
     * You can enforce this convention with the final keyword:
     * 
     * int discount (final int inputVal, final int quantity, final int
     * yearToDate) {
     * int result = inputVal;
     * if (inputVal > 50) result -= 2;
     * if (quantity > 100) result -= 1;
     * if (yearToDate > 10000) result -= 4;
     * return result;
     * }
     * 
     * I admit that I don’t use final much, because I don’t find it helps much
     * with clarity for short methods. I use it with a long method to help me
     * see whether anything is changing the parameter.
     */
}
