// G1001_Convert_Procedural_Design_to_Objects.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.G_BIG_REFACTORINGS;

/**
 * You have code written in a procedural style.Turn the data records into
 * objects, break up the behavior, and move the behavior to the
 * objects.MotivationA client of ours once started a project with two absolute
 * principles the developers had to follow: (1) you must use Java, (2) you must
 * not use objects.We may laugh, but although Java is an object-oriented
 * language, there is more to using objects than calling a constructor. Using
 * objects well takes time to learn. Often you’re faced with the problem of
 * procedure-like code that has to be more object oriented. The typical
 * situation is long procedural methods on a class with little data and dumb
 * data objects with nothing more than accessors. If you are converting from a
 * purely procedural program, you may not even have this, but it’s a good place
 * to start.We are not saying that you should never have objects with behavior
 * and little or no data. We often use small strategy objects when we need to
 * vary behavior. However, such procedural objects usually are small and are
 * used when we have a particular need for flexibility.Mechanics• Take each
 * record type and turn it into a dumb data object with accessors. If you have a
 * relational database, take each table and turn it into a dumb data object.•
 * Take all the procedural code and put it into a single class. You can either
 * make the class a singleton (for ease of reinitialization) or make the methods
 * static.• Take each long procedure and apply Extract Method (110) and the
 * related refactorings to break it down. As you break down the procedures, use
 * Move Method (142) to move each one to the appropriate dumb data class.•
 * Continue until you’ve removed all the behavior away from the original class.
 * If the original class was a purely procedural class, it’s very gratifying to
 * delete it.ExampleThe example in Chapter 1 is a good example of the need for
 * Convert Procedural Design to Objects, particularly the first stage, in which
 * the statement method is broken up and distributed. When you’re finished, you
 * can work on now-intelligent data objects with other refactorings.
 */
public class G1001_Convert_Procedural_Design_to_Objects
{

}
