package com.refactoring.stategies.A_ComposingMethods;

public class A6_INLINE_METHOD
{

    /**
     * INLINE METHOD
     * 
     * @return
     * 
     *         A method’s body is just as clear as its name.
     * 
     *         Put the method’s body into the body of its callers and remove the
     *         method.
     * 
     *         int getRating() {
     *         return (moreThanFiveLateDeliveries()) ? 2 : 1;
     *         }
     *         boolean moreThanFiveLateDeliveries(){
     *         return _numberOfLateDeliveries > 5;
     *         }
     * 
     *         Image
     *         int getRating() {
     *         return (_numberOfLateDeliveries > 5)? 2 : 1;
     *         }
     * 
     *         Motivation
     *         A theme of this book is to use short methods named to show their
     *         intention, because these methods lead to clearer and easier to
     *         read code. But sometimes you do come across a method in which the
     *         body is as clear as the name. Or you refactor the body of the
     *         code into something that is just as clear as the name. When this
     *         happens, you should then get rid of the method. Indirection can
     *         be helpful, but needless indirection is irritating.
     * 
     *         Another time to use Inline Method (117) is when you have a group
     *         of methods that seem badly factored. You can inline them all into
     *         one big method and then reextract the methods. Kent Beck finds it
     *         is often good to do this before using Replace Method with Method
     *         Object (135). You inline the various calls made by the method
     *         that have behavior you want to have in the method object. It’s
     *         easier to move one method than to move the method and its called
     *         methods.
     * 
     *         I commonly use Inline Method (117) when someone is using too much
     *         indirection and it seems that every method does simple delegation
     *         to another method, and I get lost in all the delegation. In these
     *         cases some of the indirection is worthwhile, but not all of it.
     *         By trying to inline I can flush out the useful ones and eliminate
     *         the rest.
     * 
     *         Mechanics
     *         • Check that the method is not polymorphic.
     * 
     *         Image Don’t inline if subclasses override the method; they cannot
     *         override a method that isn’t there.
     * 
     *         • Find all calls to the method.
     * 
     *         • Replace each call with the method body.
     * 
     *         • Compile and test.
     * 
     *         • Remove the method definition.
     * 
     *         Written this way, Inline Method (117) is simple. In general it
     *         isn’t. I could write pages on how to handle recursion, multiple
     *         return points, inlining into another object when you don’t have
     *         accessors, and the like. The reason I don’t is that if you
     *         encounter these complexities, you shouldn’t do this refactoring.
     */
}
