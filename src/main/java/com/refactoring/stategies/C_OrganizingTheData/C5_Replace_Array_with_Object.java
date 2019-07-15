// C5_Replace_Array_with_Object.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * REPLACE ARRAY WITH OBJECT
 * You have an array in which certain elements mean different things.
 * 
 * Replace the array with an object that has a field for each element.
 * 
 * String[] row = new String[3];
 * row [0] = "Liverpool";
 * row [1] = "15";
 * 
 * Image
 * Performance row = new Performance();
 * row.setName("Liverpool");
 * row.setWins("15");
 * 
 * Motivation
 * Arrays are a common structure for organizing data. However, they should be
 * used only to contain a collection of similar objects in some order.
 * Sometimes, however, you see them used to contain a number of different
 * things. Conventions such as “the first element on the array is the person’s
 * name” are hard to remember. With an object you can use names of fields and
 * methods to convey this information so you don’t have to remember it or hope
 * the comments are up to date. You can also encapsulate the information and use
 * Move Method (142) to add behavior to it.
 * 
 * Mechanics
 * • Create a new class to represent the information in the array. Give it a
 * public field for the array.
 * 
 * • Change all users of the array to use the new class.
 * 
 * • Compile and test.
 * 
 * • One by one, add getters and setters for each element of the array. Name the
 * accessors after the purpose of the array element. Change the clients to use
 * the accessors. Compile and test after each change.
 * 
 * • When all array accesses are replaced by methods, make the array private.
 * 
 * • Compile.
 * 
 * • For each element of the array, create a field in the class and change the
 * accessors to use the field.
 * 
 * • Compile and test after each element is changed.
 * 
 * • When all elements have been replaced with fields, delete the array.
 * 
 * Example
 * I start with an array that’s used to hold the name, wins, and losses of a
 * sports team. It would be declared as follows:
 * 
 * String[] row = new String[3];
 * 
 * It would be used with code such as the following:
 * 
 * row [0] = "Liverpool";
 * row [1] = "15";
 * 
 * String name = row[0];
 * int wins = Integer.parseInt(row[1]);
 * 
 * To turn this into an object, I begin by creating a class:
 * 
 * class Performance {}
 * 
 * For my first step I give the new class a public data member. (I know this is
 * evil and wicked, but I’ll reform in due course.)
 * 
 * public String[] _data = new String[3];
 * 
 * Now I find the spots that create and access the array. When the array is
 * created I use
 * 
 * Performance row = new Performance();
 * 
 * When it is used, I change to
 * 
 * row._data [0] = "Liverpool";
 * row._data [1] = "15";
 * 
 * String name = row._data[0];
 * int wins = Integer.parseInt(row._data[1]);
 * 
 * One by one, I add more meaningful getters and setters. I start with the name:
 * 
 * class Performance...
 * public String getName() {
 * return _data[0];
 * }
 * public void setName(String arg) {
 * _data[0] = arg;
 * }
 * 
 * I alter the users of that row to use the getters and setters instead:
 * 
 * row.setName("Liverpool");
 * row._data [1] = "15";
 * 
 * String name = row.getName();
 * int wins = Integer.parseInt(row._data[1]);
 * 
 * I can do the same with the second element. To make matters easier, I can
 * encapsulate the data type conversion:
 * 
 * class Performance...
 * public int getWins() {
 * return Integer.parseInt(_data[1]);
 * }
 * public void setWins(String arg) {
 * _data[1] = arg;
 * }
 * 
 * ....
 * client code...
 * row.setName("Liverpool");
 * row.setWins("15");
 * 
 * String name = row.getName();
 * int wins = row.getWins();
 * 
 * Once I’ve done this for each element, I can make the array private.
 * 
 * private String[] _data = new String[3];
 * 
 * The most important part of this refactoring, changing the interface, is now
 * done. It is also useful, however, to replace the array internally. I can do
 * this by adding a field for each array element and changing the accessors to
 * use it:
 * 
 * class Performance...
 * public String getName() {
 * return _name;
 * }
 * public void setName(String arg) {
 * _name = arg;
 * }
 * private String _name;
 * 
 * I do this for each element in the array. When I’ve done them all, I delete
 * the array.
 */
public class C5_Replace_Array_with_Object
{

}
