// B3_Extract_Class.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.B_Moving_Features_Between_Objects;

/**
 * EXTRACT CLASS
 * You have one class doing work that should be done by two.
 * 
 * Image
 * Create a new class and move the relevant fields and methods from the old
 * class into the new class.
 * 
 * Motivation
 * You’ve probably heard that a class should be a crisp abstraction, handle a
 * few clear responsibilities, or some similar guideline. In practice, classes
 * grow. You add some operations here, a bit of data there. You add a
 * responsibility to a class feeling that it’s not worth a separate class, but
 * as that responsibility grows and breeds, the class becomes too complicated.
 * Soon your class is as crisp as a microwaved duck.
 * 
 * Such a class is one with many methods and quite a lot of data. A class that
 * is too big to understand easily. You need to consider where it can be split,
 * and you split it. A good sign is that a subset of the data and a subset of
 * the methods seem to go together. Other good signs are subsets of data that
 * usually change together or are particularly dependent on each other. A useful
 * test is to ask yourself what would happen if you removed a piece of data or a
 * method. What other fields and methods would become nonsense?
 * 
 * One sign that often crops up later in development is the way the class is
 * subtyped. You may find that subtyping affects only a few features or that
 * some features need to be subtyped one way and other features a different way.
 * 
 * Mechanics
 * • Decide how to split the responsibilities of the class.
 * 
 * • Create a new class to express the split-off responsibilities.
 * 
 * Image If the responsibilities of the old class no longer match its name,
 * rename the old class.
 * 
 * • Make a link from the old to the new class.
 * 
 * Image You may need a two-way link. But don’t make the back link until you
 * find you need it.
 * 
 * • Use Move Field (146) on each field you wish to move.
 * 
 * • Compile and test after each move.
 * 
 * • Use Move Method (142) to move methods over from old to new. Start with
 * lower-level methods (called rather than calling) and build to the higher
 * level.
 * 
 * • Compile and test after each move.
 * 
 * • Review and reduce the interfaces of each class.
 * 
 * Image If you did have a two-way link, examine to see whether it can be made
 * one way.
 * 
 * • Decide whether to expose the new class. If you do expose the class, decide
 * whether to expose it as a reference object or as an immutable value object.
 * 
 * Example
 * I start with a simple person class:
 * 
 * class Person...
 * public String getName() {
 * return _name;
 * }
 * public String getTelephoneNumber() {
 * return ("(" + _officeAreaCode + ") " + _officeNumber);
 * }
 * String getOfficeAreaCode() {
 * return _officeAreaCode;
 * }
 * void setOfficeAreaCode(String arg) {
 * _officeAreaCode = arg;
 * }
 * String getOfficeNumber() {
 * return _officeNumber;
 * }
 * void setOfficeNumber(String arg) {
 * _officeNumber = arg;
 * }
 * 
 * private String _name;
 * private String _officeAreaCode;
 * private String _officeNumber;
 * 
 * In this case I can separate the telephone number behavior into its own class.
 * I start by defining a telephone number class:
 * 
 * class TelephoneNumber {
 * }
 * 
 * That was easy! I next make a link from the person to the telephone number:
 * 
 * class Person
 * private TelephoneNumber _officeTelephone = new TelephoneNumber();
 * 
 * Now I use Move Field (146) on one of the fields:
 * 
 * class TelephoneNumber {
 * String getAreaCode() {
 * return _areaCode;
 * }
 * void setAreaCode(String arg) {
 * _areaCode = arg;
 * }
 * private String _areaCode;
 * }
 * class Person...
 * public String getTelephoneNumber() {
 * return ("(" + getOfficeAreaCode() + ") " + _officeNumber);
 * }
 * String getOfficeAreaCode() {
 * return _officeTelephone.getAreaCode();
 * }
 * void setOfficeAreaCode(String arg) {
 * _officeTelephone.setAreaCode(arg);
 * }
 * 
 * I can then move the other field and use Move Method (142) on the telephone
 * number:
 * 
 * class Person...
 * public String getName() {
 * return _name;
 * }
 * public String getTelephoneNumber(){
 * return _officeTelephone.getTelephoneNumber();
 * }
 * TelephoneNumber getOfficeTelephone() {
 * return _officeTelephone;
 * }
 * 
 * private String _name;
 * private TelephoneNumber _officeTelephone = new TelephoneNumber();
 * class TelephoneNumber...
 * public String getTelephoneNumber() {
 * return ("(" + _areaCode + ") " + _number);
 * }
 * String getAreaCode() {
 * return _areaCode;
 * }
 * void setAreaCode(String arg) {
 * _areaCode = arg;
 * }
 * String getNumber() {
 * return _number;
 * }
 * void setNumber(String arg) {
 * _number = arg;
 * }
 * private String _number;
 * private String _areaCode;
 * 
 * The decision then is how much to expose the new class to my clients. I can
 * completely hide it by providing delegating methods for its interface, or I
 * can expose it. I may choose to expose it to some clients (such as those in my
 * package) but not to others.
 * 
 * If I choose to expose the class, I need to consider the dangers of aliasing.
 * If I expose the telephone number and a client changes the area code in that
 * object, how do I feel about it? It may not be a direct client that makes this
 * change. It might be the client of a client of a client.
 * 
 * I have the following options:
 * 
 * 1. I accept that any object may change any part of the telephone number. This
 * makes the telephone number a reference object, and I should consider Change
 * Value to Reference (179). In this case the person would be the access point
 * for the telephone number.
 * 
 * 2. I don’t want anybody to change the value of the telephone number without
 * going through the person. I can either make the telephone number immutable,
 * or I can provide an immutable interface for the telephone number.
 * 
 * 3. Another possibility is to clone the telephone number before passing it
 * out. But this can lead to confusion because people think they can change the
 * value. It also may lead to aliasing problems between clients if the telephone
 * number is passed around a lot.
 * 
 * Extract Class (149) is a common technique for improving the liveness of a
 * concurrent program because it allows you to have separate locks on the two
 * resulting classes. If you don’t need to lock both objects you don’t have to.
 * For more on this see section 3.3 in Lea [Lea].
 * 
 * However, there is a danger there. If you need to ensure that both objects are
 * locked together, you get into the area of transactions and other kinds of
 * shared locks. As discussed in Lea in section 8.1 [Lea], this is complex
 * territory and requires heavier machinery than it is typically worth.
 * Transactions are very useful when you use them, but writing transaction
 * managers is more than most programmers should attempt.
 */
public class B3_Extract_Class
{

}
