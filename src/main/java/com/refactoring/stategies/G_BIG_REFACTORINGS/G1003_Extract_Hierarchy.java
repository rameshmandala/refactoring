// G1003_Extract_Hierarchy.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.G_BIG_REFACTORINGS;

/**
 * You have a class that is doing too much work, at least in part through many
 * conditional statements.
 * 
 * Image
 * Create a hierarchy of classes in which each subclass represents a special
 * case.
 * 
 * Motivation
 * In evolutionary design, it is common to think of a class as implementing one
 * idea and come to realize later that it is really implementing two or three or
 * ten. You create the class simply at first. A few days or weeks later you see
 * that if only you add a flag and a couple of tests, you can use it in a new
 * case. A month later you have another such opportunity. A year later you have
 * a real mess: flags and conditional expressions all over the place.
 * 
 * When you encounter a Swiss-Army-knife class that has grown to open cans, cut
 * down small trees, shine a laser point at reluctant presentation bullet items,
 * and, oh yes, I suppose cut things, you need a strategy for teasing apart the
 * various strands. The strategy here works only if your conditional logic
 * remains static during the life of the object. If not, you may have to use
 * Extract Class (149) before you can begin separating the cases from each
 * other.
 * 
 * Don’t be discouraged if Extract Hierarchy is a refactoring that you can’t
 * finish in a day. It can take weeks or months to untangle a design that has
 * become snarled. Do the steps that are easy and obvious, then take a break. Do
 * some visibly productive work for a few days. When you’ve learned something,
 * come back and do a few more easy and obvious steps.
 * 
 * Mechanics
 * We’ve put in two sets of mechanics. In the first case you aren’t sure what
 * the variations should be. In this case you want to take one step at a time,
 * as follows:
 * 
 * • Identify a variation.
 * 
 * Image If the variations can change during the life of the object, use Extract
 * Class (149) to pull that aspect into a separate class.
 * 
 * • Create a subclass for that special case and use Replace Constructor with
 * Factory Method (304) on the original. Alter the factory method to return an
 * instance of the subclass where appropriate.
 * 
 * • One at a time, copy methods that contain conditional logic to the subclass,
 * then simplify the methods given what you can say for certain about instances
 * of the subclass that you can’t say about instances of the superclass.
 * 
 * Image Use Extract Method (110) in the superclass if necessary to isolate the
 * conditional parts of methods from the unconditional parts.
 * 
 * • Continue isolating special cases until you can declare the superclass
 * abstract.
 * 
 * • Delete the bodies of methods in the superclass that are overridden in all
 * subclasses and make the superclass declarations abstract.
 * 
 * When the variations are very clear from the outset, you can use a different
 * strategy, as follows:
 * 
 * • Create a subclass for each variation.
 * 
 * • Use Replace Constructor with Factory Method (304) to return the appropriate
 * subclass for each variation.
 * 
 * Image If the variations are marked with a type code, use Replace Type Code
 * with Subclasses (223). If the variations can change within the life of the
 * class, use Replace Type Code with State/Strategy (227).
 * 
 * • Take methods that have conditional logic and apply Replace Conditional with
 * Polymorphism (255). If the whole method does not vary, isolate the varying
 * part with Extract Method (110).
 * 
 * Example
 * The example is a nonobvious case. You can follow the refactorings for Replace
 * Type Code with Subclasses (223), Replace Type Code with State/Strategy (227),
 * and Replace Conditional with Polymorphism (255) to see how the obvious case
 * works.
 * 
 * We start with a program that calculates an electricity bill. The initial
 * objects look like Figure 12.11.
 * 
 * Image
 * Figure 12.11. Customer and billing scheme
 * 
 * The billing scheme contains a lot of conditional logic for billing in
 * different circumstances. Different charges are used for summer and winter,
 * and different billing plans are used for residential, small business,
 * customers receiving Social Security (lifeline), and those with a disability.
 * The resulting complex logic makes the Billing Scheme class rather complex.
 * 
 * Our first step is to pick a variant aspect that keeps cropping up in the
 * conditional logic. This might be various conditions that depend on whether
 * the customer is on a disability plan. This can be a flag in Customer, Billing
 * Scheme, or somewhere else.
 * 
 * We create a subclass for the variation. To use the subclass we need to make
 * sure it is created and used. So we look at the constructor for Billing
 * Scheme. First we use Replace Constructor with Factory Method (304). Then we
 * look at the factory method and see how the logic depends on disability. We
 * then create a clause that returns a disability billing scheme when
 * appropriate.
 * 
 * We look at the various methods on Billing Scheme and look for those that
 * contain conditional logic that varies on the basis of disability. CreateBill
 * is one of those methods, so we copy it to the subclass (Figure 12.12).
 * 
 * Image
 * Figure 12.12. Adding a subclass for disability
 * 
 * Now we examine the subclass copy of createBill and simplify it on the basis
 * that we know it is now within the context of a disability scheme. So code
 * that says
 * 
 * if (disabilityScheme()) doSomething
 * 
 * can be replaced with
 * 
 * doSomething
 * 
 * If disabilities are exclusive of the business scheme we can eliminate any
 * code that is conditional on the business scheme.
 * 
 * As we do this, we like to ensure that varying code is separated from code
 * that stays the same. We use Extract Method (110) and Decompose Conditional
 * (238) to do that. We continue doing this for various methods of Billing
 * Scheme until we feel we’ve dealt with most of the disability conditionals.
 * Then we pick another variation, say lifeline, and do the same for that.
 * 
 * As we do the second variation, however, we look at how the variations for
 * lifeline compare with those for disability. We want to identify cases in
 * which we can have methods that have the same intention but carry it out
 * differently in the two separate cases. We might have variation in the
 * calculation of taxes for the two cases. We want to ensure that we have two
 * methods on the subclasses that have the same signature. This may mean
 * altering disability so we can line up the subclasses. Usually we find that as
 * we do more variations, the pattern of similar and varying methods tends to
 * stabilize, making additional variations easier.
 */
public class G1003_Extract_Hierarchy
{

}
