// B7_Introduce_Foreign_Method.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.B_Moving_Features_Between_Objects;

/**
 * INTRODUCE FOREIGN METHOD
 * A server class you are using needs an additional method, but you can’t modify
 * the class.
 * 
 * Create a method in the client class with an instance of the server class as
 * its first argument.
 * 
 * Date newStart = new Date (previousEnd.getYear(),
 * previousEnd.getMonth(), previousEnd.getDate() + 1);
 * 
 * Image
 * Date newStart = nextDay(previousEnd);
 * 
 * private static Date nextDay(Date arg) {
 * return new Date (arg.getYear(),arg.getMonth(), arg.getDate() + 1);
 * }
 * 
 * Motivation
 * It happens often enough. You are using this really nice class that gives you
 * all these great services. Then there is one service it doesn’t give you but
 * should. You curse the class, saying, “Why don’t you do that?” If you can
 * change the source, you can add in the method. If you can’t change the source,
 * you have to code around the lack of the method in the client.
 * 
 * If you use the method only once in the client class then the extra coding is
 * no big deal and probably wasn’t needed on the original class anyway. If you
 * use the method several times, however, you have to repeat this coding around.
 * Because repetition is the root of all software evil, this repetitive code
 * should be factored into a single method. When you do this refactoring, you
 * can clearly signal that this method is really a method that should be on the
 * original by making it a foreign method.
 * 
 * If you find yourself creating many foreign methods on a server class, or you
 * find many of your classes need the same foreign method, you should use
 * Introduce Local Extension (164) instead.
 * 
 * Don’t forget that foreign methods are a work-around. If you can, try to get
 * the methods moved to their proper homes. If code ownership is the issue, send
 * the foreign method to the owner of the server class and ask the owner to
 * implement the method for you.
 * 
 * Mechanics
 * • Create a method in the client class that does what you need.
 * 
 * Image The method should not access any of the features of the client class.
 * If it needs a value, send it in as a parameter.
 * 
 * • Make an instance of the server class the first parameter.
 * 
 * • Comment the method as “foreign method; should be in server.”
 * 
 * Image This way you can use a text search to find foreign methods later if you
 * get the chance to move the method.
 * 
 * Example
 * I have some code that needs to roll over a billing period. The original code
 * looks like this:
 * 
 * Date newStart = new Date (previousEnd.getYear(),
 * previousEnd.getMonth(), previousEnd.getDate() + 1);
 * 
 * I can extract the code on the right-hand side of the assignment into a
 * method. This method is a foreign method for date:
 * 
 * Date newStart = nextDay(previousEnd);
 * 
 * private static Date nextDay(Date arg) {
 * // foreign method, should be on date
 * return new Date (arg.getYear(),arg.getMonth(), arg.getDate() + 1);
 * }
 */
public class B7_Introduce_Foreign_Method
{

}
