package com.refactoring.stategies.A_ComposingMethods;

public class A2_SplitTemporaryVariable
{
    /*
     * You have a temporary variable assigned to more than once, but is not a
     * loop variable nor a collecting temporary variable.
     * 
     * Make a separate temporary variable for each assignment.
     * 
     * double temp = 2 * (_height + _width);
     * System.out.println (temp);
     * temp = _height * _width;
     * System.out.println (temp);
     * 
     * Image
     * final double perimeter = 2 * (_height + _width);
     * System.out.println (perimeter);
     * final double area = _height * _width;
     * System.out.println (area);
     * 
     * Motivation
     * Temporary variables are made for various uses. Some of these uses
     * naturally lead to the temp’s being assigned to several times. Loop
     * variables [Beck] change for each run around a loop (such as the i in for
     * (int i=0; i<10; i++)). Collecting temporary variables [Beck] collect
     * together some value that is built up during the method.
     * 
     * Many other temporaries are used to hold the result of a long-winded bit
     * of code for easy reference later. These kinds of variables should be set
     * only once. That they are set more than once is a sign that they have more
     * than one responsibility within the method. Any variable with more than
     * one responsibility should be replaced with a temp for each
     * responsibility. Using a temp for two different things is very confusing
     * for the reader.
     * 
     * Mechanics
     * • Change the name of a temp at its declaration and its first assignment.
     * 
     * Image If the later assignments are of the form i = i + some expression,
     * that indicates that it is a collecting temporary variable, so don’t split
     * it. The operator for a collecting temporary variable usually is addition,
     * string concatenation, writing to a stream, or adding to a collection.
     * 
     * • Declare the new temp as final.
     * 
     * • Change all references of the temp up to its second assignment.
     * 
     * • Declare the temp at its second assignment.
     * 
     * • Compile and test.
     * 
     * • Repeat in stages, each stage renaming at the declaration, and changing
     * references until the next assignment.
     * 
     * Example
     * For this example I compute the distance traveled by a haggis. From a
     * standing start, a haggis experiences an initial force. After a delayed
     * period a secondary force kicks in to further accelerate the haggis. Using
     * the common laws of motion, I can compute the distance traveled as
     * follows:
     * 
     * double getDistanceTravelled (int time) {
     * double result;
     * double acc = _primaryForce / _mass;
     * int primaryTime = Math.min(time, _delay);
     * result = 0.5 * acc * primaryTime * primaryTime;
     * int secondaryTime = time - _delay;
     * if (secondaryTime > 0) {
     * double primaryVel = acc * _delay;
     * acc = (_primaryForce + _secondaryForce) / _mass;
     * result += primaryVel * secondaryTime + 0.5 * acc * secondaryTime *
     * secondaryTime;
     * }
     * return result;
     * }
     * 
     * A nice awkward little function. The interesting thing for our example is
     * the way the variable acc is set twice. It has two responsibilities: one
     * to hold the initial acceleration caused by the first force and another
     * later to hold the acceleration with both forces. This I want to split.
     * 
     * I start at the beginning by changing the name of the temp and declaring
     * the new name as final. Then I change all references to the temp from that
     * point up to the next assignment. At the next assignment I declare it:
     * 
     * double getDistanceTravelled (int time) {
     * double result;
     * final double primaryAcc = _primaryForce / _mass;
     * int primaryTime = Math.min(time, _delay);
     * result = 0.5 * primaryAcc * primaryTime * primaryTime;
     * int secondaryTime = time - _delay;
     * if (secondaryTime > 0) {
     * double primaryVel = primaryAcc * _delay;
     * double acc = (_primaryForce + _secondaryForce) / _mass;
     * result += primaryVel * secondaryTime + 0.5 * acc * secondaryTime *
     * secondaryTime;
     * }
     * return result;
     * }
     * 
     * I choose the new name to represent only the first use of the temp. I make
     * it final to ensure it is only set once. I can then declare the original
     * temp at its second assignment. Now I can compile and test, and all should
     * work.
     * 
     * I continue on the second assignment of the temp. This removes the
     * original temp name completely, replacing it with a new temp named for the
     * second use.
     * 
     * double getDistanceTravelled (int time) {
     * double result;
     * final double primaryAcc = _primaryForce / _mass;
     * int primaryTime = Math.min(time, _delay);
     * result = 0.5 * primaryAcc * primaryTime * primaryTime;
     * int secondaryTime = time - _delay;
     * if (secondaryTime > 0) {
     * double primaryVel = primaryAcc * _delay;
     * final double secondaryAcc = (_primaryForce + _secondaryForce) / _mass;
     * result += primaryVel * secondaryTime + 0.5 *
     * secondaryAcc * secondaryTime * secondaryTime;
     * }
     * return result;
     * }
     * 
     * I’m sure you can think of a lot more refactoring to be done here. Enjoy
     * it. (I’m sure it’s better than eating the haggis—do you know what they
     * put in those things?)
     * 
     */
}
