// G1000_Tease_Apart_Inheritance.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.G_BIG_REFACTORINGS;

/**
 * You have an inheritance hierarchy that is doing two jobs at once.
 * 
 * Create two hierarchies and use delegation to invoke one from the other.
 * 
 * Image
 * Motivation
 * Inheritance is great. It helps you write dramatically “compressed” code in
 * subclasses. A single method can take on importance out of proportion with its
 * size because of where it sits in the hierarchy.
 * 
 * Not surprisingly for such a powerful mechanism, it is easy to misuse
 * inheritance. And the misuse can easily creep up on you. One day you are
 * adding one little subclass to do a little job. The next day you are adding
 * other subclasses to do the same job in other parts of the hierarchy. A week
 * (or month or year) later you are swimming in spaghetti. Without a paddle.
 * 
 * Tangled inheritance is a problem because it leads to code duplication, the
 * bane of the programmer’s existence. It makes changes more difficult, because
 * the strategies for solving a certain kind of problem are spread around.
 * Finally, the resulting code is hard to understand. You can’t just say, “This
 * hierarchy here, it computes results.” You have to say, “Well, it computes
 * results, and there are subclasses for the tabular versions, and each of those
 * has subclasses for each of the countries.”
 * 
 * You can easily spot a single inheritance hierarchy that is doing two jobs. If
 * every class at a certain level in the hierarchy has subclasses that begin
 * with the same adjective, you probably are doing two jobs with one hierarchy.
 * 
 * Mechanics
 * • Identify the different jobs being done by the hierarchy. Create a
 * two-dimensional grid (or three- or four-dimensional, if your hierarchy is a
 * real mess and you have some really cool graph paper) and label the axes with
 * the different jobs. We assume two or more dimensions require repeated
 * applications of this refactoring (one at a time, of course).
 * 
 * • Decide which job is more important and is to be retained in the current
 * hierarchy and which is to be moved to another hierarchy.
 * 
 * • Use Extract Class (Chapter 6) at the common superclass to create an object
 * for the subsidiary job and add an instance variable to hold this object.
 * 
 * • Create subclasses of the extracted object for each of the subclasses in the
 * original hierarchy. Initialize the instance variable created in the previous
 * step to an instance of this subclass.
 * 
 * • Use Move Method (Chapter 7) in each of the subclasses to move the behavior
 * in the subclass to the extracted object.
 * 
 * • When the subclass has no more code, eliminate it.
 * 
 * • Continue until all the subclasses are gone. Look at the new hierarchy for
 * possible further refactorings such as Pull Up Method or Pull Up Field
 * (Chapter 11).
 * 
 * Examples
 * Let’s take the example of a tangled hierarchy (Figure 12.1).
 * 
 * Image
 * Figure 12.1. A tangled hierarchy
 * 
 * This hierarchy got the way it did because Deal was originally being used only
 * to display a single deal. Then someone got the bright idea of displaying a
 * table of deals. A little experiment with the quick subclass Active Deal shows
 * you can indeed display a table with little work. Oh, you want tables of
 * passive deals, too? No problem, another little subclass and away we go.
 * 
 * Two months later the table code has become complicated but there is no simple
 * place to put it, time is pressing, the usual story. Now adding a new kind of
 * deal is hard, because the deal logic is tangled with the presentation logic.
 * 
 * Following the recipe, the first step is to identify the jobs being done by
 * the hierarchy. One job is capturing variation according to type of deal.
 * Another job is capturing variation according to presentation style. So here’s
 * our grid:
 * 
 * Image
 * The next step tells us to decide which job is more important. The dealness of
 * the object is far more important than the presentation style, so we leave
 * Deal alone and extract the presentation style to its own hierarchy.
 * Practically speaking, we should probably leave alone the job that has the
 * most code associated with it, so there is less code to move.
 * 
 * The next step tells us to use Extract Class (149) to create a presentation
 * style (Figure 12.2).
 * 
 * Image
 * Figure 12.2. Adding a presentation style
 * 
 * The next step tells us to create subclasses of the extracted class or for
 * each of the subclasses in the original hierarchy (Figure 12.3) and to
 * initialize the instance variable to the appropriate subclass:
 * 
 * ActiveDeal constructor
 * ...presentation= new SingleActivePresentationStyle();...
 * 
 * Image
 * Figure 12.3. Adding subclasses of presentation style
 * 
 * You may well be saying, “Don’t we have more classes now than we did before?
 * How is this supposed to make my life better?” It is true that sometimes you
 * have to take a step backward before you can take two steps forward. In cases
 * such as this tangled hierarchy, the hierarchy of the extracted object can
 * almost always be dramatically simplified once the object has been extracted.
 * However, it is safer to take the refactoring one step at a time than to jump
 * ten steps ahead to the already simplified design.
 * 
 * Now we use Move Method (142) and Move Field (146) to move the
 * presentation-related methods and variables of the deal subclasses to the
 * presentation style subclasses. We don’t have a good way of simulating this
 * with the example as drawn, so we ask you to imagine it happening. When we’re
 * done, though, there should be no code left in the classes Tabular Active Deal
 * and Tabular Passive Deal, so we remove them (Figure 12.4).
 * 
 * Image
 * Figure 12.4. The tabular subclasses of Deal have been removed
 * 
 * Now that we’ve separated the two jobs, we can work to simplify each
 * separately. When we’ve done this refactoring, we’ve always been able to
 * dramatically simplify the extracted class and often further simplify the
 * original object. The next move will get rid of the active-passive distinction
 * in the presentation style in Figure 12.6.
 * 
 * Image
 * Figure 12.5. The hierarchies are now separated
 * 
 * Image
 * Figure 12.6. Presentation differences can be handled with a couple of
 * variables
 * 
 * Even the distinction between single and tabular can be captured by the values
 * of a few variables. You don’t need subclasses at all (Figure 12.6).
 */
public class G1000_Tease_Apart_Inheritance
{

}
