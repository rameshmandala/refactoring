package com.refactoring.stategies.A_ComposingMethods;

public class A8_Substitute_Algorithm
{
    /**
     * SUBSTITUTE ALGORITHM
     * You want to replace an algorithm with one that is clearer.
     * 
     * Replace the body of the method with the new algorithm.
     * 
     * String foundPerson(String[] people){
     * for (int i = 0; i < people.length; i++) {
     * if (people[i].equals ("Don")){
     * return "Don";
     * }
     * if (people[i].equals ("John")){
     * return "John";
     * }
     * if (people[i].equals ("Kent")){
     * return "Kent";
     * }
     * }
     * return "";
     * }
     * 
     * Image
     * String foundPerson(String[] people){
     * List candidates = Arrays.asList(new String[] {"Don", "John", "Kent"});
     * for (int i=0; i<people.length; i++)
     * if (candidates.contains(people[i]))
     * return people[i];
     * return "";
     * }
     * 
     * Motivation
     * I’ve never tried to skin a cat. I’m told there are several ways to do it.
     * I’m sure some are easier than others. So it is with algorithms. If you
     * find a clearer way to do something, you should replace the complicated
     * way with the clearer way. Refactoring can break down something complex
     * into simpler pieces, but sometimes you just reach the point at which you
     * have to remove the whole algorithm and replace it with something simpler.
     * This occurs as you learn more about the problem and realize that there’s
     * an easier way to do it. It also happens if you start using a library that
     * supplies features that duplicate your code.
     * 
     * Sometimes when you want to change the algorithm to do something slightly
     * different, it is easier to subtitute the algorithm first into something
     * easier for the change you need to make.
     * 
     * When you have to take this step, make sure you have decomposed the method
     * as much as you can. Substituting a large, complex algorithm is very
     * difficult; only by making it simple can you make the substitution
     * tractable.
     * 
     * Mechanics
     * • Prepare your alternative algorithm. Get it so that it compiles.
     * 
     * • Run the new algorithm against your tests. If the results are the same,
     * you’re finished.
     * 
     * • If the results aren’t the same, use the old algorithm for comparison in
     * testing and debugging.
     * 
     * Image Run each test case with old and new algorithms and watch both
     * results. That will help you see which test cases are causing trouble, and
     * how.
     */
}
