// E1000_Rename_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * The name of a method does not reveal its purpose.
 * 
 * Image
 * Change the name of the method.
 * 
 * Motivation
 * An important part of the code style I am advocating is small methods to
 * factor complex processes. Done badly, this can lead you on a merry dance to
 * find out what all the little methods do. The key to avoiding this merry dance
 * is naming the methods. Methods should be named in a way that communicates
 * their intention. A good way to do this is to think what the comment for the
 * method would be and turn that comment into the name of the method.
 * 
 * Life being what it is, you won’t get your names right the first time. In this
 * situation you may well be tempted to leave it after all it’s only a name.
 * That is the work of the evil demon Obfuscatis; don’t listen to him. If you
 * see a badly named method, it is imperative that you change it. Remember your
 * code is for a human first and a computer second. Humans need good names. Take
 * note of when you have spent ages trying to do something that would have been
 * easier if a couple of methods had been better named. Good naming is a skill
 * that requires practice; improving this skill is the key to being a truly
 * skillful programmer. The same applies to other aspects of the signature. If
 * reordering parameters clarifies matters, do it (see Add Parameter (275) and
 * Remove Parameter [277]).
 * 
 * Mechanics
 * • Check to see whether the method signature is implemented by a superclass or
 * subclass. If it is, perform these steps for each implementation.
 * 
 * • Declare a new method with the new name. Copy the old body of code over to
 * the new name and make any alterations to fit.
 * 
 * • Compile.
 * 
 * • Change the body of the old method so that it calls the new one.
 * 
 * Image If you only have a few references, you can reasonably skip this step.
 * 
 * • Compile and test.
 * 
 * • Find all references to the old method name and change them to refer to the
 * new one. Compile and test after each change.
 * 
 * • Remove the old method.
 * 
 * Image If the old method is part of the interface and you cannot remove it,
 * leave it in place and mark it as deprecated.
 * 
 * • Compile and test.
 * 
 * Example
 * I have a method to get a person’s telephone number:
 * 
 * public String getTelephoneNumber() {
 * return ("(" + _officeAreaCode + ") " + _officeNumber);
 * }
 * 
 * I want to rename the method to getOfficeTelephoneNumber. I begin by creating
 * the new method and copying the body over to the new method. The old method
 * now changes to call the new one:
 * 
 * class Person...
 * public String getTelephoneNumber(){
 * return getOfficeTelephoneNumber();
 * }
 * public String getOfficeTelephoneNumber() {
 * return ("(" + _officeAreaCode + ") " + _officeNumber);
 * }
 * 
 * Now I find the callers of the old method, and switch them to call the new
 * one. When I have switched them all, I can remove the old method.
 * 
 * The procedure is the same if I need to add or remove a parameter.
 * 
 * If there aren’t many callers, I change the callers to call the new method
 * without using the old method as a delegating method. If my tests throw a
 * wobbly, I back out and make the changes the slow way.
 */
public class E1000_Rename_Method
{

}
