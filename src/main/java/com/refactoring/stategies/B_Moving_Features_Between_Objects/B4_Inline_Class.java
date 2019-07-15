// B4_Inline_Class.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.B_Moving_Features_Between_Objects;

/**
 * INLINE CLASS
 * A class isn’t doing very much.
 * 
 * Image
 * Move all its features into another class and delete it.
 * 
 * Motivation
 * Inline Class is the reverse of Extract Class (149). I use Inline Class if a
 * class is no longer pulling its weight and shouldn’t be around any more. Often
 * this is the result of refactoring that moves other responsibilities out of
 * the class so there is little left. Then I want to fold this class into
 * another class, picking one that seems to use the runt class the most.
 * 
 * Mechanics
 * • Declare the public protocol of the source class onto the absorbing class.
 * Delegate all these methods to the source class.
 * 
 * Image If a separate interface makes sense for the source class methods, use
 * Extract Interface (341) before inlining.
 * 
 * • Change all references from the source class to the absorbing class.
 * 
 * Image Declare the source class private to remove out-of-package references.
 * Also change the name of the source class so the compiler catches any dangling
 * references to the source class.
 * 
 * • Compile and test.
 * 
 * • Use Move Method and Move Field to move features from the source class to
 * the absorbing class until there is nothing left.
 * 
 * • Hold a short, simple funeral service.
 * 
 * Example
 * Because I made a class out of telephone number, I now inline it back into
 * person. I start with separate classes:
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
 * 
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
 * I begin by declaring all the visible methods on telephone number on person:
 * 
 * class Person...
 * String getAreaCode() {
 * return _officeTelephone.getAreaCode();
 * }
 * void setAreaCode(String arg) {
 * _officeTelephone.setAreaCode(arg);
 * }
 * String getNumber() {
 * return _officeTelephone.getNumber();
 * }
 * void setNumber(String arg) {
 * _officeTelephone.setNumber(arg);
 * }
 * 
 * Now I find clients of telephone number and switch them to use the person’s
 * interface. So
 * 
 * Person martin = new Person();
 * martin.getOfficeTelephone().setAreaCode ("781");
 * 
 * becomes
 * 
 * Person martin = new Person();
 * martin.setAreaCode ("781");
 * 
 * Now I can use Move Method (142) and Move Field (146) until the telephone
 * class is no more.
 */
public class B4_Inline_Class
{

}
