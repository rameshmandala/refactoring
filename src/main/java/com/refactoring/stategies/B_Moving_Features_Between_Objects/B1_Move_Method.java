package com.refactoring.stategies.B_Moving_Features_Between_Objects;

public class B1_Move_Method
{
    /**
     * MOVE METHOD
     * A method is, or will be, using or used by more features of another class
     * than the class on which it is defined.
     * 
     * Image
     * Create a new method with a similar body in the class it uses most. Either
     * turn the old method into a simple delegation, or remove it altogether.
     * 
     * Motivation
     * Moving methods is the bread and butter of refactoring. I move methods
     * when classes have too much behavior or when classes are collaborating too
     * much and are too highly coupled. By moving methods around, I can make the
     * classes simpler and they end up being a more crisp implementation of a
     * set of responsibilities.
     * 
     * I usually look through the methods on a class to find a method that seems
     * to reference another object more than the object it lives on. A good time
     * to do this is after I have moved some fields. Once I see a likely method
     * to move, I take a look at the methods that call it, the methods it calls,
     * and any redefining methods in the hierarchy. I assess whether to go ahead
     * on the basis of the object with which the method seems to have more
     * interaction.
     * 
     * It’s not always an easy decision to make. If I am not sure whether to
     * move a method, I go on to look at other methods. Moving other methods
     * often makes the decision easier. Sometimes the decision still is hard to
     * make. Actually it is then no big deal. If it is difficult to make the
     * decision, it probably does not matter that much. Then I choose according
     * to instinct; after all, I can always change it again later.
     * 
     * Mechanics
     * • Examine all features used by the source method that are defined on the
     * source class. Consider whether they also should be moved.
     * 
     * Image If a feature is used only by the method you are about to move, you
     * might as well move it, too. If the feature is used by other methods,
     * consider moving them as well. Sometimes it is easier to move a clutch of
     * methods than to move them one at a time.
     * 
     * • Check the sub- and superclasses of the source class for other
     * declarations of the method.
     * 
     * Image If there are any other declarations, you may not be able to make
     * the move, unless the polymorphism can also be expressed on the target.
     * 
     * • Declare the method in the target class.
     * 
     * Image You may choose to use a different name, one that makes more sense
     * in the target class.
     * 
     * • Copy the code from the source method to the target. Adjust the method
     * to make it work in its new home.
     * 
     * Image If the method uses its source, you need to determine how to
     * reference the source object from the target method. If there is no
     * mechanism in the target class, pass the source object reference to the
     * new method as a parameter.
     * 
     * Image If the method includes exception handlers, decide which class
     * should logically handle the exception. If the source class should be
     * responsible, leave the handlers behind.
     * 
     * • Compile the target class.
     * 
     * • Determine how to reference the correct target object from the source.
     * 
     * Image There may be an existing field or method that will give you the
     * target. If not, see whether you can easily create a method that will do
     * so. Failing that, you need to create a new field in the source that can
     * store the target. This may be a permanent change, but you can also make
     * it temporarily until you have refactored enough to remove it.
     * 
     * • Turn the source method into a delegating method.
     * 
     * • Compile and test.
     * 
     * • Decide whether to remove the source method or retain it as a delegating
     * method.
     * 
     * Image Leaving the source as a delegating method is easier if you have
     * many references.
     * 
     * • If you remove the source method, replace all the references with
     * references to the target method.
     * 
     * Image You can compile and test after changing each reference, although it
     * is usually easier to change all references with one search and replace.
     * 
     * • Compile and test.
     * 
     * Example
     * An account class illustrates this refactoring:
     * 
     * class Account...
     * double overdraftCharge() {
     * if (_type.isPremium()) {
     * double result = 10;
     * if (_daysOverdrawn > 7) result += (_daysOverdrawn - 7) * 0.85;
     * return result;
     * }
     * else return _daysOverdrawn * 1.75;
     * }
     * 
     * double bankCharge() {
     * double result = 4.5;
     * if (_daysOverdrawn > 0) result += overdraftCharge();
     * return result;
     * }
     * private AccountType _type;
     * private int _daysOverdrawn;
     * 
     * Let’s imagine that there are going to be several new account types, each
     * of which has its own rule for calculating the overdraft charge. So I want
     * to move the overdraft charge method over to the account type.
     * 
     * The first step is to look at the features that the overdraftCharge method
     * uses and consider whether it is worth moving a batch of methods together.
     * In this case I need the _daysOverdrawn field to remain on the account
     * class, because that will vary with individual accounts.
     * 
     * Next I copy the method body over to the account type and get it to fit.
     * 
     * class AccountType...
     * double overdraftCharge(int daysOverdrawn) {
     * if (isPremium()) {
     * double result = 10;
     * if (daysOverdrawn > 7) result += (daysOverdrawn - 7) * 0.85;
     * return result;
     * }
     * else return daysOverdrawn * 1.75;
     * }
     * 
     * In this case fitting means removing the _type from uses of features of
     * the account type, and doing something about the features of account that
     * I still need. When I need to use a feature of the source class I can do
     * one of four things: (1) move this feature to the target class as well,
     * (2) create or use a reference from the target class to the source, (3)
     * pass the source object as a parameter to the method, (4) if the feature
     * is a variable, pass it in as a parameter.
     * 
     * In this case I passed the variable as a parameter.
     * 
     * Once the method fits and compiles in the target class, I can replace the
     * source method body with a simple delegation:
     * 
     * class Account...
     * double overdraftCharge() {
     * return _type.overdraftCharge(_daysOverdrawn);
     * }
     * 
     * At this point I can compile and test.
     * 
     * I can leave things like this, or I can remove the method in the source
     * class. To remove the method I need to find all callers of the method and
     * redirect them to call the method in account type:
     * 
     * class Account...
     * double bankCharge() {
     * double result = 4.5;
     * if (_daysOverdrawn > 0) result += _type.overdraftCharge(_daysOverdrawn);
     * return result;
     * }
     * 
     * Once I’ve replaced all the callers, I can remove the method declaration
     * in account. I can compile and test after each removal, or do them in a
     * batch. If the method isn’t private, I need to look for other classes that
     * use this method. In a strongly typed language, the compilation after
     * removal of the source declaration finds anything I missed.
     * 
     * In this case the method referred only to a single field, so I could just
     * pass this field in as a variable. If the method called another method on
     * the account, I wouldn’t have been able to do that. In those cases I need
     * to pass in the source object:
     * 
     * class AccountType...
     * double overdraftCharge(Account account) {
     * if (isPremium()) {
     * double result = 10;
     * if (account.getDaysOverdrawn() > 7)
     * result += (account.getDaysOverdrawn() - 7) * 0.85;
     * return result;
     * }
     * else return account.getDaysOverdrawn() * 1.75;
     * }
     * 
     * I also pass in the source object if I need several features of the class,
     * although if there are too many, further refactoring is needed. Typically
     * I need to decompose and move some pieces back.
     */
}
