// E1006_Preserve_Whole_Object.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * You are getting several values from an object and passing these values as
 * parameters in a method call.
 * 
 * Send the whole object instead.
 * 
 * int low = daysTempRange().getLow();
 * int high = daysTempRange().getHigh();
 * withinPlan = plan.withinRange(low, high);
 * 
 * Image
 * withinPlan = plan.withinRange(daysTempRange());
 * 
 * Motivation
 * This type of situation arises when an object passes several data values from
 * a single object as parameters in a method call. The problem with this is that
 * if the called object needs new data values later, you have to find and change
 * all the calls to this method. You can avoid this by passing in the whole
 * object from which the data came. The called object then can ask for whatever
 * it wants from the whole object.
 * 
 * In addition to making the parameter list more robust to changes, Preserve
 * Whole Object often makes the code more readable. Long parameter lists can be
 * hard to work with because both caller and callee have to remember which
 * values were there. They also encourage duplicate code because the called
 * object can’t take advantage of any other methods on the whole object to
 * calculate intermediate values.
 * 
 * There is a down side. When you pass in values, the called object has a
 * dependency on the values, but there isn’t any dependency to the object from
 * which the values were extracted. Passing in the required object causes a
 * dependency between the required object and the called object. If this is
 * going to mess up your dependency structure, don’t use Preserve Whole Object.
 * 
 * Another reason I have heard for not using Preserve Whole Object is that when
 * a calling object need only one value from the required object, it is better
 * to pass in the value than to pass in the whole object. I don’t subscribe to
 * that view. One value and one object amount to the same thing when you pass
 * them in, at least for clarity’s sake (there may be a performance cost with
 * pass by value parameters). The driving force is the dependency issue.
 * 
 * That a called method uses lots of values from another object is a signal that
 * the called method should really be defined on the object from which the
 * values come. When you are considering Preserve Whole Object, consider Move
 * Method (142) as an alternative.
 * 
 * You may not already have the whole object defined. In this case you need
 * Introduce Parameter Object (295).
 * 
 * A common case is that a calling object passes several of its own data values
 * as parameters. In this case you can make the call and pass in this instead of
 * these values, if you have the appropriate getting methods and you don’t mind
 * the dependency.
 * 
 * Mechanics
 * • Create a new parameter for the whole object from which the data comes.
 * 
 * • Compile and test.
 * 
 * • Determine which parameters should be obtained from the whole object.
 * 
 * • Take one parameter and replace references to it within the method body by
 * invoking an appropriate method on the whole object parameter.
 * 
 * • Delete the parameter.
 * 
 * • Compile and test.
 * 
 * • Repeat for each parameter that can be got from the whole object.
 * 
 * • Remove the code in the calling method that obtains the deleted parameters.
 * 
 * Image Unless, of course, the code is using these parameters somewhere else.
 * 
 * • Compile and test.
 * 
 * Example
 * Consider a room object that records high and low temperatures during the day.
 * It needs to compare this range with a range in a predefined heating plan:
 * 
 * class Room...
 * boolean withinPlan(HeatingPlan plan) {
 * int low = daysTempRange().getLow();
 * int high = daysTempRange().getHigh();
 * return plan.withinRange(low, high);
 * }
 * class HeatingPlan...
 * boolean withinRange (int low, int high) {
 * return (low >= _range.getLow() && high <= _range.getHigh());
 * }
 * private TempRange _range;
 * 
 * Rather than unpack the range information when I pass it, I can pass the whole
 * range object. In this simple case I can do this in one step. When more
 * parameters are involved, I can do it in smaller steps. First I add the whole
 * object to the parameter list:
 * 
 * class HeatingPlan...
 * boolean withinRange (TempRange roomRange, int low, int high) {
 * return (low >= _range.getLow() && high <= _range.getHigh());
 * }
 * 
 * class Room...
 * boolean withinPlan(HeatingPlan plan) {
 * int low = daysTempRange().getLow();
 * int high = daysTempRange().getHigh();
 * return plan.withinRange(daysTempRange(), low, high);
 * }
 * 
 * Then I use a method on the whole object instead of one of the parameters:
 * 
 * class HeatingPlan...
 * boolean withinRange (TempRange roomRange, int high) {
 * return (roomRange.getLow() >= _range.getLow() && high <= _range.getHigh());
 * }
 * 
 * class Room...
 * boolean withinPlan(HeatingPlan plan) {
 * int low = daysTempRange().getLow();
 * int high = daysTempRange().getHigh();
 * return plan.withinRange(daysTempRange(), high);
 * }
 * 
 * I continue until I’ve changed all I need:
 * 
 * class HeatingPlan...
 * boolean withinRange (TempRange roomRange) {
 * return (roomRange.getLow() >= _range.getLow() && roomRange.getHigh() <=
 * _range.getHigh());
 * }
 * class Room...
 * boolean withinPlan(HeatingPlan plan) {
 * int low = daysTempRange().getLow();
 * int high = daysTempRange().getHigh();
 * return plan.withinRange(daysTempRange());
 * }
 * 
 * Now I don’t need the temps anymore:
 * 
 * class Room...
 * boolean withinPlan(HeatingPlan plan) {
 * int low = daysTempRange().getLow();
 * int high = daysTempRange().getHigh();
 * return plan.withinRange(daysTempRange());
 * }
 * 
 * Using whole objects this way soon leads you to realize that you can usefully
 * move behavior into the whole object to make it easier to work with.
 * 
 * class HeatingPlan...
 * boolean withinRange (TempRange roomRange) {
 * return (_range.includes(roomRange));
 * }
 * class TempRange...
 * boolean includes (TempRange arg) {
 * return arg.getLow() >= this.getLow() && arg.getHigh() <= this.getHigh();
 * }
 */
public class E1006_Preserve_Whole_Object
{

}
