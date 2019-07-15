// F000_Description.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * Generalization produces its own batch of refactorings, mostly dealing with
 * moving methods around a hierarchy of inheritance. Pull Up Field (320) and
 * Pull Up Method (322) both promote function up a hierarchy, and Push Down
 * Method (328) and Push Down Field (329) push function downward. Constructors
 * are a little bit more awkward to pull up, so Pull Up Constructor Body (325)
 * deals with those issues. Rather than pushing down a constructor, it is often
 * useful to use Replace Constructor with Factory Method (304).
 * 
 * If you have methods that have a similar outline body but vary in details, you
 * can use Form Template Method (345) to separate the differences from the
 * similarities.
 * 
 * In addition to moving function around a hierarchy, you can change the
 * hierarchy by creating new classes. Extract Subclass (330), Extract Superclass
 * (336), and Extract Interface (341) all do this by forming new elements out of
 * various points. Extract Interface (341) is particularly important when you
 * want to mark a small amount of function for the type system. If you find
 * yourself with unnecessary classes in your hierarchy, you can use Collapse
 * Hierarchy (344) to remove them.
 * 
 * Sometimes you find that inheritance is not the best way of handling a
 * situation and that you need delegation instead. Replace Inheritance with
 * Delegation (352) helps make this change. Sometimes life is the other way
 * around and you have to use Replace Delegation with Inheritance (355).
 */
public class F000_Description
{

}
