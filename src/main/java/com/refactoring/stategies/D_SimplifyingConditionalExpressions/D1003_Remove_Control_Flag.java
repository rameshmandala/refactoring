// D1003_Remove_Control_Flag.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.D_SimplifyingConditionalExpressions;

/**
 * You have a variable that is acting as a control flag for a series of boolean
 * expressions.
 * 
 * Use a break or return instead.
 * 
 * Motivation
 * When you have a series of conditional expressions, you often see a control
 * flag used to determine when to stop looking:
 * 
 * set done to false
 * while not done
 * if (condition)
 * do something
 * set done to true
 * next step of loop
 * 
 * Such control flags are more trouble than they are worth. They come from rules
 * of structured programming that call for routines with one entry and one exit
 * point. I agree with (and modern languages enforce) one entry point, but the
 * one exit point rule leads you to very convoluted conditionals with these
 * awkward flags in the code. This is why languages have break and continue
 * statements to get out of a complex conditional. It is often surprising what
 * you can do when you get rid of a control flag. The real purpose of the
 * conditional becomes so much more clear.
 * 
 * Mechanics
 * The obvious way to deal with control flags is to use the break or continue
 * statements present in Java.
 * 
 * • Find the value of the control flag that gets you out of the logic
 * statement.
 * 
 * • Replace assignments of the break-out value with a break or continue
 * statement.
 * 
 * • Compile and test after each replacement.
 * 
 * Another approach, also usable in languages without break and continue, is as
 * follows:
 * 
 * • Extract the logic into a method.
 * 
 * • Find the value of the control flag that gets you out of the logic
 * statement.
 * 
 * • Replace assignments of the break-out value with a return.
 * 
 * • Compile and test after each replacement.
 * 
 * Even in languages with a break or continue, I usually prefer use of an
 * extraction and of a return. The return clearly signals that no more code in
 * the method is executed. If you have that kind of code, you often need to
 * extract that piece anyway.
 * 
 * Keep an eye on whether the control flag also indicates result information. If
 * it does, you still need the control flag if you use the break, or you can
 * return the value if you have extracted a method.
 * 
 * Example: Simple Control Flag Replaced with Break
 * The following function checks to see whether a list of people contains a
 * couple of hard-coded suspicious characters:
 * 
 * void checkSecurity(String[] people) {
 * boolean found = false;
 * for (int i = 0; i < people.length; i++) {
 * if (! found) {
 * if (people[i].equals ("Don")){
 * sendAlert();
 * found = true;
 * }
 * if (people[i].equals ("John")){
 * sendAlert();
 * found = true;
 * }
 * }
 * }
 * }
 * 
 * In a case like this, it is easy to see the control flag. It’s the piece that
 * sets the found variable to true. I can introduce the breaks one at a time:
 * 
 * void checkSecurity(String[] people) {
 * boolean found = false;
 * for (int i = 0; i < people.length; i++) {
 * if (! found) {
 * if (people[i].equals ("Don")){
 * sendAlert();
 * break;
 * }
 * if (people[i].equals ("John")){
 * sendAlert();
 * found = true;
 * }
 * }
 * }
 * }
 * 
 * until I have them all:
 * 
 * void checkSecurity(String[] people) {
 * boolean found = false;
 * for (int i = 0; i < people.length; i++) {
 * if (! found) {
 * if (people[i].equals ("Don")){
 * sendAlert();
 * break;
 * }
 * if (people[i].equals ("John")){
 * sendAlert();
 * break;
 * }
 * }
 * }
 * }
 * 
 * Then I can remove all references to the control flag:
 * 
 * void checkSecurity(String[] people) {
 * for (int i = 0; i < people.length; i++) {
 * if (people[i].equals ("Don")){
 * sendAlert();
 * break;
 * }
 * if (people[i].equals ("John")){
 * sendAlert();
 * break;
 * }
 * }
 * }
 * 
 * Example: Using Return with a Control Flag Result
 * The other style of this refactoring uses a return. I illustrate this with a
 * variant that uses the control flag as a result value:
 * 
 * void checkSecurity(String[] people) {
 * String found = "";
 * for (int i = 0; i < people.length; i++) {
 * if (found.equals("")) {
 * if (people[i].equals ("Don")){
 * sendAlert();
 * found = "Don";
 * }
 * if (people[i].equals ("John")){
 * sendAlert();
 * found = "John";
 * }
 * }
 * }
 * someLaterCode(found);
 * }
 * 
 * Here found is doing two things. It is indicating a result and acting as a
 * control flag. When I see this, I like to extract the code that is determining
 * found into its own method:
 * 
 * void checkSecurity(String[] people) {
 * String found = foundMiscreant(people);
 * someLaterCode(found);
 * }
 * String foundMiscreant(String[] people){
 * String found = "";
 * for (int i = 0; i < people.length; i++) {
 * if (found.equals("")) {
 * if (people[i].equals ("Don")){
 * sendAlert();
 * found = "Don";
 * }
 * if (people[i].equals ("John")){
 * sendAlert();
 * found = "John";
 * }
 * }
 * }
 * return found;
 * }
 * 
 * Then I can replace the control flag with a return:
 * 
 * String foundMiscreant(String[] people){
 * String found = "";
 * for (int i = 0; i < people.length; i++) {
 * if (found.equals("")) {
 * if (people[i].equals ("Don")){
 * sendAlert();
 * return "Don";
 * }
 * if (people[i].equals ("John")){
 * sendAlert();
 * found = "John";
 * }
 * }
 * }
 * return found;
 * }
 * 
 * until I have removed the control flag:
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
 * You can also use the return style when you’re not returning a value. Just use
 * return without the argument.
 * 
 * Of course this has the problem of a function with side effects. So I want to
 * use Separate Query from Modifier (279). You’ll find this example continued
 * there.
 */
public class D1003_Remove_Control_Flag
{

}
