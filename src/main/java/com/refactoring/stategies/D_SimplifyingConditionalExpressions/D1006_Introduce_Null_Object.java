// D1006_Introduce_Null_Object.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.D_SimplifyingConditionalExpressions;

/**
 * You have repeated checks for a null value.
 * 
 * Replace the null value with a null object.
 * 
 * if (customer == null) plan = BillingPlan.basic();
 * else plan = customer.getPlan();
 * 
 * Image
 * Image
 * Motivation
 * The essence of polymorphism is that instead of asking an object what type it
 * is and then invoking some behavior based on the answer, you just invoke the
 * behavior. The object, depending on its type, does the right thing. One of the
 * less intuitive places to do this is where you have a null value in a field.
 * I’ll let Ron Jeffries tell the story:
 * 
 * —Ron Jeffries
 * 
 * We first started using the null object pattern when Rich Garzaniti found that
 * lots of code in the system would check objects for presence before sending a
 * message to the object. We might ask an object for its person, then ask the
 * result whether it was null. If the object was present, we would ask it for
 * its rate. We were doing this in several places, and the resulting duplicate
 * code was getting annoying.
 * 
 * So we implemented a missing-person object that answered a zero rate (we call
 * our null objects missing objects). Soon missing person knew a lot of methods,
 * such as rate. Now we have more than 80 null-object classes.
 * 
 * Our most common use of null objects is in the display of information. When we
 * display, for example, a person, the object may or may not have any of perhaps
 * 20 instance variables. If these were allowed to be null, the printing of a
 * person would be very complex. Instead we plug in various null objects, all of
 * which know how to display themselves in an orderly way. This got rid of huge
 * amounts of procedural code.
 * 
 * Our most clever use of null object is the missing Gemstone session. We use
 * the Gemstone database for production, but we prefer to develop without it and
 * push the new code to Gemstone every week or so. There are various points in
 * the code where we have to log in to a Gemstone session. When we are running
 * without Gemstone, we simply plug in a missing Gemstone session. It looks the
 * same as the real thing but allows us to develop and test without realizing
 * the database isn’t there.
 * 
 * Another helpful use of null object is the missing bin. A bin is a collection
 * of payroll values that often have to be summed or looped over. If a
 * particular bin doesn’t exist, we answer a missing bin, which acts just like
 * an empty bin. The missing bin knows it has zero balance and no values. By
 * using this approach, we eliminate the creation of tens of empty bins for each
 * of our thousands of employees.
 * 
 * An interesting characteristic of using null objects is that things almost
 * never blow up. Because the null object responds to all the same messages as a
 * real one, the system generally behaves normally. This can sometimes make it
 * difficult to detect or find a problem, because nothing ever breaks. Of
 * course, as soon as you being inspecting the objects, you’ll find the null
 * object somewhere where it shouldn’t be.
 * 
 * Remember, null objects are always constant: nothing about them ever changes.
 * Accordingly, we implement them using the Singleton pattern [Gang of Four].
 * Whenever you ask, for example, for a missing person, you always get the
 * single instance of that class.
 * 
 * You can find more details about the null object pattern in Woolf [Woolf].
 * 
 * Mechanics
 * • Create a subclass of the source class to act as a null version of the
 * class. Create an isNull operation on the source class and the null class. For
 * the source class it should return false, for the null class it should return
 * true.
 * 
 * Image You may find it useful to create an explicitly nullable interface for
 * the isNull method.
 * 
 * Image As an alternative you can use a testing interface to test for nullness.
 * 
 * • Compile.
 * 
 * • Find all places that can give out a null when asked for a source object.
 * Replace them to give out a null object instead.
 * 
 * • Find all places that compare a variable of the source type with null and
 * replace them with a call isNull.
 * 
 * Image You may be able to do this by replacing one source and its clients at a
 * time and compiling and testing between working on sources.
 * 
 * Image A few assertions that check for null in places where you should no
 * longer see it can be useful.
 * 
 * • Compile and test.
 * 
 * • Look for cases in which clients invoke an operation if not null and do some
 * alternative behavior if null.
 * 
 * • For each of these cases override the operation in the null class with the
 * alternative behavior.
 * 
 * • Remove the condition check for those that use the overriden behavior,
 * compile, and test.
 * 
 * Example
 * A utility company knows about sites: the houses and apartments that use the
 * utility’s services. At any time a site has a customer.
 * 
 * class Site...
 * Customer getCustomer() {
 * return _customer;
 * }
 * Customer _customer;
 * 
 * There are various features of a customer. I look at three of them.
 * 
 * class Customer...
 * public String getName() {...}
 * public BillingPlan getPlan() {...}
 * public PaymentHistory getHistory() {...}
 * 
 * The payment history has its own features:
 * 
 * public class PaymentHistory...
 * int getWeeksDelinquentInLastYear()
 * 
 * The getters I show allow clients to get at this data. However, sometimes I
 * don’t have a customer for a site. Someone may have moved out and I don’t yet
 * know who has moved in. Because this can happen we have to ensure that any
 * code that uses the customer can handle nulls. Here are a few example
 * fragments:
 * 
 * Customer customer = site.getCustomer();
 * BillingPlan plan;
 * if (customer == null) plan = BillingPlan.basic();
 * else plan = customer.getPlan();
 * ...
 * String customerName;
 * if (customer == null) customerName = "occupant";
 * else customerName = customer.getName();
 * ...
 * int weeksDelinquent;
 * if (customer == null) weeksDelinquent = 0;
 * else weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
 * 
 * In these situations I may have many clients of site and customer, all of
 * which have to check for nulls and all of which do the same thing when they
 * find one. Sounds like it’s time for a null object.
 * 
 * The first step is to create the null customer class and modify the customer
 * class to support a query for a null test:
 * 
 * class NullCustomer extends Customer {
 * public boolean isNull() {
 * return true;
 * }
 * }
 * 
 * class Customer...
 * public boolean isNull() {
 * return false;
 * }
 * 
 * protected Customer() {} //needed by the NullCustomer
 * 
 * If you aren’t able to modify the Customer class you can use a testing
 * interface (see page 266).
 * 
 * If you like, you can signal the use of null object by means of an interface:
 * 
 * interface Nullable {
 * boolean isNull();
 * }
 * class Customer implements Nullable
 * 
 * I like to add a factory method to create null customers. That way clients
 * don’t have to know about the null class:
 * 
 * class Customer...
 * static Customer newNull() {
 * return new NullCustomer();
 * }
 * 
 * Now comes the difficult bit. Now I have to return this new null object
 * whenever I expect a null and replace the tests of the form foo == null with
 * tests of the form foo.isNull(). I find it useful to look for all the places
 * where I ask for a customer and modify them so that they return a null
 * customer rather than null.
 * 
 * class Site...
 * Customer getCustomer() {
 * return (_customer == null) ?
 * Customer.newNull():
 * _customer;
 * }
 * 
 * I also have to alter all uses of this value so that they test with isNull()
 * rather than == null.
 * 
 * Customer customer = site.getCustomer();
 * BillingPlan plan;
 * if (customer.isNull()) plan = BillingPlan.basic();
 * else plan = customer.getPlan();
 * ...
 * String customerName;
 * if (customer.isNull()) customerName = "occupant";
 * else customerName = customer.getName();
 * ...
 * int weeksDelinquent;
 * if (customer.isNull()) weeksDelinquent = 0;
 * else weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
 * 
 * There’s no doubt that this is the trickiest part of this refactoring. For
 * each source of a null I replace, I have to find all the times it is tested
 * for nullness and replace them. If the object is widely passed around, these
 * can be hard to track. I have to find every variable of type customer and find
 * everywhere it is used. It is hard to break this process into small steps.
 * Sometimes I find one source that is used in only a few places, and I can
 * replace that source only. But most of the time, however, I have to make many
 * widespread changes. The changes aren’t too difficult to back out of, because
 * I can find calls of isNull without too much difficulty, but this is still a
 * messy step.
 * 
 * Once this step is done, and I’ve compiled and tested, I can smile. Now the
 * fun begins. As it stands I gain nothing from using isNull rather than ==
 * null. The gain comes as I move behavior to the null customer and remove
 * conditionals. I can make these moves one at a time. I begin with the name.
 * Currently I have client code that says
 * 
 * String customerName;
 * if (customer.isNull()) customerName = "occupant";
 * else customerName = customer.getName();
 * 
 * I add a suitable name method to the null customer:
 * 
 * class NullCustomer...
 * public String getName(){
 * return "occupant";
 * }
 * 
 * Now I can make the conditional code go away:
 * 
 * String customerName = customer.getName();
 * 
 * I can do the same for any other method in which there is a sensible general
 * response to a query. I can also do appropriate actions for modifiers. So
 * client code such as
 * 
 * if (! customer.isNull())
 * customer.setPlan(BillingPlan.special());
 * 
 * can be replaced with
 * 
 * customer.setPlan(BillingPlan.special());
 * 
 * class NullCustomer...
 * public void setPlan (BillingPlan arg) {}
 * 
 * Remember that this movement of behavior makes sense only when most clients
 * want the same response. Notice that I said most not all. Any clients who want
 * a different response to the standard one can still test using isNull. You
 * benefit when many clients want to do the same thing; they can simply rely on
 * the default null behavior.
 * 
 * The example contains a slightly different case—client code that uses the
 * result of a call to customer:
 * 
 * if (customer.isNull()) weeksDelinquent = 0;
 * else weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
 * 
 * I can handle this by creating a null payment history:
 * 
 * class NullPaymentHistory extends PaymentHistory...
 * int getWeeksDelinquentInLastYear() {
 * return 0;
 * }
 * 
 * I modify the null customer to return it when asked:
 * 
 * class NullCustomer...
 * public PaymentHistory getHistory() {
 * return PaymentHistory.newNull();
 * }
 * 
 * Again I can remove the conditional code:
 * 
 * int weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
 * 
 * You often find that null objects return other null objects.
 * 
 * Example: Testing Interface
 * The testing interface is an alternative to defining an isNull method. In this
 * approach I create a null interface with no methods defined:
 * 
 * interface Null {}
 * 
 * I then implement null in my null objects:
 * 
 * class NullCustomer extends Customer implements Null...
 * 
 * I then test for nullness with the instanceof operator:
 * 
 * aCustomer instanceof Null
 * 
 * I normally run away screaming from the instanceof operator, but in this case
 * it is okay to use it. It has the particular advantage that I don’t need to
 * change the customer class. This allows me to use the null object even when I
 * don’t have access to customer’s source code.
 * 
 * Other Special Cases
 * When carrying out this refactoring, you can have several kinds of null. Often
 * there is a difference between there is no customer (new building and not yet
 * moved in) and there is an unknown customer (we think there is someone there,
 * but we don’t know who it is). If that is the case, you can build separate
 * classes for the different null cases. Sometimes null objects actually can
 * carry data, such as usage records for the unknown customer, so that we can
 * bill the customers when we find out who they are.
 * 
 * In essence there is a bigger pattern here, called special case. A special
 * case class is a particular instance of a class with special behavior. So
 * UnknownCustomer and NoCustomer would both be special cases of Customer. You
 * often see special cases with numbers. Floating points in Java have special
 * cases for positive and negative infinity and for not a number (NaN). The
 * value of special cases is that they help reduce dealing with errors. Floating
 * point operations don’t throw exceptions. Doing any operation with NaN yields
 * another NaN in the same way that accessors on null objects usually result in
 * other null objects.
 */
public class D1006_Introduce_Null_Object
{

}
