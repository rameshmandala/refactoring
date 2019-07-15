package com.refactoring.stategies.A_ComposingMethods;

public class A4_Separate_Query_from_Modifier
{
    /**
     *
     * You have a method that returns a value but also changes the state of an
     * object.
     * Motivation
     * When you have a function that gives you a value and has no observable
     * side effects, you have a very valuable thing. You can call this function
     * as often as you like. You can move the call to other places in the
     * method. In short, you have a lot less to worry about.
     * 
     * It is a good idea to clearly signal the difference between methods with
     * side effects and those without. A good rule to follow is to say that any
     * method that returns a value should not have observable side effects. Some
     * programmers treat this as an absolute rule [Meyer]. I’m not 100 percent
     * pure on this (as on anything), but I try to follow it most of the time,
     * and it has served me well.
     * 
     * If you come across a method that returns a value but also has side
     * effects, you should try to separate the query from the modifier.
     * 
     * You’ll note I use the phrase observable side effects. A common
     * optimization is to cache the value of a query in a field so that repeated
     * calls go quicker. Although this changes the state of the object with the
     * cache, the change is not observable. Any sequence of queries will always
     * return the same results for each query [Meyer].
     * 
     * Mechanics
     * • Create a query that returns the same value as the original method.
     * 
     * Image Look in the original method to see what is returned. If the
     * returned value is a temporary, look at the location of the temp
     * assignment.
     * 
     * • Modify the original method so that it returns the result of a call to
     * the query.
     * 
     * Image Every return in the original method should say return newQuery()
     * instead of returning anything else.
     * 
     * Image If the method used a temp to with a single assignment to capture
     * the return value, you should be able to remove it.
     * 
     * • Compile and test.
     * 
     * • For each call, replace the single call to the original method with a
     * call to the query. Add a call to the original method before the line that
     * calls the query. Compile and test after each change to a calling method.
     * 
     * • Make the original method have a void return type and remove the return
     * expressions.
     * 
     * Example
     * Here is a function that tells me the name of a miscreant for a security
     * system and sends an alert. The rule is that only one alert is sent even
     * if there is more than one miscreant:
     * 
     * String foundMiscreant(String[] people){
     * for (int i = 0; i < people.length; i++) {
     * if (people[i].equals ("Don")){
     * sendAlert();
     * return "Don";
     * }
     * if (people[i].equals ("John")){
     * sendAlert();
     * return "John";
     * }
     * }
     * return "";
     * }
     * 
     * It is called by
     * 
     * void checkSecurity(String[] people) {
     * String found = foundMiscreant(people);
     * someLaterCode(found);
     * }
     * 
     * To separate the query from the modifier, I first need to create a
     * suitable query that returns the same value as the modifier does but
     * without doing the side effects.
     * 
     * String foundPerson(String[] people){
     * for (int i = 0; i < people.length; i++) {
     * if (people[i].equals ("Don")){
     * return "Don";
     * }
     * if (people[i].equals ("John")){
     * return "John";
     * }
     * }
     * return "";
     * }
     * 
     * Then I replace every return in the original function, one at a time, with
     * calls to the new query. I test after each replacement. When I’m done the
     * original method looks like the following:
     * 
     * String foundMiscreant(String[] people){
     * for (int i = 0; i < people.length; i++) {
     * if (people[i].equals ("Don")){
     * sendAlert();
     * return foundPerson(people);
     * 
     * }
     * if (people[i].equals ("John")){
     * sendAlert();
     * return foundPerson(people);
     * 
     * }
     * }
     * return foundPerson(people);
     * }
     * 
     * Now I alter all the calling methods to do two calls: first to the
     * modifier and then to the query:
     * 
     * void checkSecurity(String[] people) {
     * foundMiscreant(people);
     * String found = foundPerson(people);
     * someLaterCode(found);
     * }
     * 
     * Once I have done this for all calls, I can alter the modifier to give it
     * a void return type:
     * 
     * void foundMiscreant (String[] people){
     * for (int i = 0; i < people.length; i++) {
     * if (people[i].equals ("Don")){
     * sendAlert();
     * return;
     * }
     * if (people[i].equals ("John")){
     * sendAlert();
     * return;
     * }
     * }
     * }
     * 
     * Now it seems better to change the name of the original:
     * 
     * void sendAlert (String[] people){
     * for (int i = 0; i < people.length; i++) {
     * if (people[i].equals ("Don")){
     * sendAlert();
     * return;
     * }
     * if (people[i].equals ("John")){
     * sendAlert();
     * return;
     * }
     * }
     * }
     * 
     * Of course in this case I have a lot of code duplication because the
     * modifier uses the body of the query to do its work. I can now use
     * Substitute Algorithm (139) on the modifier to take advantage of this:
     * 
     * void sendAlert(String[] people){
     * if (! foundPerson(people).equals(""))
     * sendAlert();
     * }
     * 
     * Concurrency Issues
     * If you are working in a multithreaded system, you’ll know that doing test
     * and set operations as a single action is an important idiom. Does this
     * conflict with Separate Query from Modifier (279)? I discussed this issue
     * with Doug Lea and concluded that it doesn’t, but you need to do some
     * additional things. It is still valuable to have separate query and
     * modifier operations. However, you need to retain a third method that does
     * both. The query-and-modify operation will call the separate query and
     * modify methods and be synchronized. If the query and modify operations
     * are not synchronized, you also might restrict their visibility to package
     * or private level. That way you have a safe, synchronized operation
     * decomposed into two easier-to-understand methods. These lower-level
     * methods then are available for other uses.
     * 
     * PARAMETERIZE METHOD
     * Several methods do similar things but with different values contained in
     * the method body.
     * 
     * Image
     * Create one method that uses a parameter for the different values.
     * 
     * Motivation
     * You may see a couple of methods that do similar things but vary depending
     * on a few values. In this case you can simplify matters by replacing the
     * separate methods with a single method that handles the variations by
     * parameters. Such a change removes duplicate code and increases
     * flexibility, because you can deal with other variations by adding
     * parameters.
     * 
     * Mechanics
     * • Create a parameterized method that can be substituted for each
     * repetitive method.
     * 
     * • Compile.
     * 
     * • Replace one old method with a call to the new method.
     * 
     * • Compile and test.
     * 
     * • Repeat for all the methods, testing after each one.
     * 
     * You may find that you cannot do this for the whole method, but you can
     * for a fragment of a method. In this case first extract the fragment into
     * a method, then parameterize that method.
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
}
