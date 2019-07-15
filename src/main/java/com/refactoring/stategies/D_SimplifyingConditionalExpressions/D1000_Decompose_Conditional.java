// D1000_Decompose_Conditional.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.D_SimplifyingConditionalExpressions;

/**
 * You have a complicated conditional (if-then-else) statement.
 * 
 * Extract methods from the condition, then part, and else parts.
 * 
 * if (date.before (SUMMER_START) || date.after(SUMMER_END))
 * charge = quantity * _winterRate + _winterServiceCharge;
 * else charge = quantity * _summerRate;
 * 
 * Image
 * if (notSummer(date))
 * charge = winterCharge(quantity);
 * else charge = summerCharge (quantity);
 * 
 * Motivation
 * One of the most common areas of complexity in a program lies in complex
 * conditional logic. As you write code to test conditions and to do various
 * things depending on various conditions, you quickly end up with a pretty long
 * method. Length of a method is in itself a factor that makes it harder to
 * read, but conditions increase the difficulty. The problem usually lies in the
 * fact that the code, both in the condition checks and in the actions, tells
 * you what happens but can easily obscure why it happens.
 * 
 * As with any large block of code, you can make your intention clearer by
 * decomposing it and replacing chunks of code with a method call named after
 * the intention of that block of code. With conditions you can receive further
 * benefit by doing this for the conditional part and each of the alternatives.
 * This way you highlight the condition and make it clearly what you are
 * branching on. You also highlight the reason for the branching.
 * 
 * Mechanics
 * • Extract the condition into its own method.
 * 
 * • Extract the then part and the else part into their own methods.
 * 
 * If I find a nested conditional, I usually first look to see whether I should
 * use Replace Nested Conditional with Guard Clauses (250). If that does not
 * make sense, I decompose each of the conditionals.
 * 
 * Example
 * Suppose I’m calculating the charge for something that has separate rates for
 * winter and summer:
 * 
 * if (date.before (SUMMER_START) || date.after(SUMMER_END))
 * charge = quantity * _winterRate + _winterServiceCharge;
 * else charge = quantity * _summerRate;
 * 
 * I extract the conditional and each leg as follows:
 * 
 * if (notSummer(date))
 * charge = winterCharge(quantity);
 * else charge = summerCharge (quantity);
 * 
 * private boolean notSummer(Date date) {
 * return date.before (SUMMER_START) || date.after(SUMMER_END);
 * }
 * 
 * private double summerCharge(int quantity) {
 * return quantity * _summerRate;
 * }
 * 
 * private double winterCharge(int quantity) {
 * return quantity * _winterRate + _winterServiceCharge;
 * }
 * 
 * Here I show the result of the complete refactoring for clarity. In practice,
 * however, I do each extraction separately and compile and test after each one.
 * 
 * Many programmers don’t extract the condition parts in situations such as
 * this. The conditions often are quite short, so it hardly seems worth it.
 * Although the condition is often short, there often is a big gap between the
 * intention of the code and its body. Even in this little case, reading
 * notSummer(date) conveys a clearer message to me than does the original code.
 * With the original I have to look at the code and figure out what it is doing.
 * It’s not difficult to do that here, but even so the extracted method reads
 * more like a comment.
 */
public class D1000_Decompose_Conditional
{

}
