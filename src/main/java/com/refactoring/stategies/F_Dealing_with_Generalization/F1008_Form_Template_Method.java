// F1008_Form_Template_Method.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.F_Dealing_with_Generalization;

/**
 * You have two methods in subclasses that perform similar steps in the same
 * order, yet the steps are different.
 * 
 * Get the steps into methods with the same signature, so that the original
 * methods become the same. Then you can pull them up.
 * 
 * Image
 * Motivation
 * Inheritance is a powerful tool for eliminating duplicate behavior. Whenever
 * we see two similar method in a subclass, we want to bring them together in a
 * superclass. But what if they are not exactly the same? What do we do then? We
 * still need to eliminate all the duplication we can but keep the essential
 * differences.
 * 
 * A common case is two methods that seem to carry out broadly similar steps in
 * the same sequence, but the steps are not the same. In this case we can move
 * the sequence to the superclass and allow polymorphism to play its role in
 * ensuring the different steps do their things differently. This kind of method
 * is called a template method [Gang of Four].
 * 
 * Mechanics
 * • Decompose the methods so that all the extracted methods are either
 * identical or completely different.
 * 
 * • Use Pull Up Method (322) to pull the identical methods into the superclass.
 * 
 * • For the different methods use Rename Method (273) so the signatures for all
 * the methods at each step are the same.
 * 
 * Image This makes the original methods the same in that they all issue the
 * same set of method calls, but the subclasses handle the calls differently.
 * 
 * • Compile and test after each signature change.
 * 
 * • Use Pull Up Method (322) on one of the original methods. Define the
 * signatures of the different methods as abstract methods on the superclass.
 * 
 * • Compile and test.
 * 
 * • Remove the other methods, compile, and test after each removal.
 * 
 * Example
 * I finish where I left off in Chapter 1. I had a customer class with two
 * methods for printing statements. The statement method prints statements in
 * ASCII:
 * 
 * public String statement() {
 * Enumeration rentals = _rentals.elements();
 * String result = "Rental Record for " + getName() + "\n";
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * //show figures for this rental
 * result += "\t" + each.getMovie().getTitle()+ "\t" +
 * String.valueOf(each.getCharge()) + "\n";
 * }
 * 
 * //add footer lines
 * result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
 * result += "You earned " + String.valueOf(getTotalFrequentRenterPoints()) +
 * " frequent renter points";
 * return result;
 * }
 * 
 * while the htmlStatement does them in HTML:
 * 
 * public String htmlStatement() {
 * Enumeration rentals = _rentals.elements();
 * String result = "
 * <H1>Rentals for <EM>" + getName() + "</EM></H1>
 * <P>
 * \n";
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * //show figures for each rental
 * result += each.getMovie().getTitle()+ ": " +
 * String.valueOf(each.getCharge()) + "<BR>
 * \n";
 * }
 * //add footer lines
 * result += "
 * <P>
 * You owe <EM>" + String.valueOf(getTotalCharge()) + "</EM>
 * <P>
 * \n";
 * result += "On this rental you earned <EM>" +
 * String.valueOf(getTotalFrequentRenterPoints()) +
 * "</EM> frequent renter points
 * <P>
 * ";
 * return result;
 * }
 * 
 * Before I can use Form Template Method (345) I need to arrange things so that
 * the two methods are subclasses of some common superclass. I do this by using
 * a method object [Beck] to create a separate strategy hierarchy for printing
 * the statements (Figure 11.1).
 * 
 * Image
 * Figure 11.1. Using a strategy for statements
 * 
 * class Statement {}
 * class TextStatement extends Statement {}
 * class HtmlStatement extends Statement {}
 * 
 * Now I use Move Method (142) to move the two statement methods over to the
 * subclasses:
 * 
 * class Customer...
 * public String statement() {
 * return new TextStatement().value(this);
 * }
 * public String htmlStatement() {
 * return new HtmlStatement().value(this);
 * }
 * 
 * class TextStatement {
 * public String value(Customer aCustomer) {
 * Enumeration rentals = aCustomer.getRentals();
 * String result = "Rental Record for " + aCustomer.getName() + "\n";
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * //show figures for this rental
 * result += "\t" + each.getMovie().getTitle()+ "\t" +
 * String.valueOf(each.getCharge()) + "\n";
 * }
 * 
 * //add footer lines
 * result += "Amount owed is " + String.valueOf(aCustomer.getTotalCharge()) +
 * "\n";
 * result += "You earned " +
 * String.valueOf(aCustomer.getTotalFrequentRenterPoints()) +
 * " frequent renter points";
 * return result;
 * }
 * class HtmlStatement {
 * public String value(Customer aCustomer) {
 * Enumeration rentals = aCustomer.getRentals();
 * String result = "
 * <H1>Rentals for <EM>" + aCustomer.getName() + "</EM></H1>
 * <P>
 * \n";
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * //show figures for each rental
 * result += each.getMovie().getTitle()+ ": " +
 * String.valueOf(each.getCharge()) + "<BR>
 * \n";
 * }
 * //add footer lines
 * result += "
 * <P>
 * You owe <EM>" + String.valueOf(aCustomer.getTotalCharge()) +
 * "</EM>
 * <P>
 * \n";
 * result += "On this rental you earned <EM>"
 * String.valueOf(aCustomer.getTotalFrequentRenterPoints()) +
 * "</EM> frequent renter points
 * <P>
 * ";
 * return result;
 * }
 * 
 * As I moved them I renamed the statement methods to better fit the strategy. I
 * gave them the same name because the difference between the two now lies in
 * the class rather than the method. (For those trying this from the example, I
 * also had to add a getRentals method to customer and relax the visibility of
 * getTotalCharge and getTotalFrequentRenterPoints.)
 * 
 * With two similar methods on subclasses, I can start to use Form Template
 * Method (345). The key to this refactoring is to separate the varying code
 * from the similar code by using Extract Method (110) to extract the pieces
 * that are different between the two methods. Each time I extract I create
 * methods with different bodies but the same signature.
 * 
 * The first example is the printing of the header. Both methods use the
 * customer to obtain information, but the resulting string is formatted
 * differently. I can extract the formatting of this string into separate
 * methods with the same signature:
 * 
 * class TextStatement...
 * String headerString(Customer aCustomer) {
 * return "Rental Record for " + aCustomer.getName() + "\n";
 * }
 * public String value(Customer aCustomer) {
 * Enumeration rentals = aCustomer.getRentals();
 * String result = headerString(aCustomer);
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * 
 * //show figures for this rental
 * result += "\t" + each.getMovie().getTitle()+ "\t" +
 * String.valueOf(each.getCharge()) + "\n";
 * }
 * //add footer lines
 * result += "Amount owed is " + String.valueOf(aCustomer.getTotalCharge()) +
 * "\n";
 * result += "You earned " +
 * String.valueOf(aCustomer.getTotalFrequentRenterPoints()) +
 * " frequent renter points";
 * return result;
 * }
 * 
 * class HtmlStatement...
 * String headerString(Customer aCustomer) {
 * return "
 * <H1>Rentals for <EM>" + aCustomer.getName() + "</EM></H1>
 * <P>
 * \n";
 * }
 * public String value(Customer aCustomer) {
 * Enumeration rentals = aCustomer.getRentals();
 * String result = headerString(aCustomer);
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * //show figures for each rental
 * result += each.getMovie().getTitle()+ ": " +
 * String.valueOf(each.getCharge()) + "<BR>
 * \n";
 * }
 * //add footer lines
 * result += "
 * <P>
 * You owe <EM>" + String.valueOf(aCustomer.getTotalCharge()) + "</EM>
 * <P>
 * \n";
 * result += "On this rental you earned <EM>" +
 * String.valueOf(aCustomer.getTotalFrequentRenterPoints()) +
 * "</EM> frequent renter points
 * <P>
 * ";
 * return result;
 * }
 * 
 * I compile and test and then continue with the other elements. I did the steps
 * one at a time. Here is the result:
 * 
 * class TextStatement ...
 * public String value(Customer aCustomer) {
 * Enumeration rentals = aCustomer.getRentals();
 * String result = headerString(aCustomer);
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * result += eachRentalString(each);
 * }
 * result += footerString(aCustomer);
 * return result;
 * }
 * 
 * String eachRentalString (Rental aRental) {
 * return "\t" + aRental.getMovie().getTitle()+ "\t" +
 * String.valueOf(aRental.getCharge()) + "\n";
 * }
 * 
 * String footerString (Customer aCustomer) {
 * return "Amount owed is " + String.valueOf(aCustomer.getTotalCharge()) + "\n"
 * +
 * "You earned " + String.valueOf(aCustomer.getTotalFrequentRenterPoints()) +
 * " frequent renter points";
 * }
 * 
 * class HtmlStatement...
 * public String value(Customer aCustomer) {
 * Enumeration rentals = aCustomer.getRentals();
 * String result = headerString(aCustomer);
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * result += eachRentalString(each);
 * }
 * result += footerString(aCustomer);
 * return result;
 * }
 * 
 * String eachRentalString (Rental aRental) {
 * return aRental.getMovie().getTitle()+ ": " +
 * String.valueOf(aRental.getCharge()) + "<BR>
 * \n";
 * }
 * 
 * String footerString (Customer aCustomer) {
 * return "
 * <P>
 * You owe <EM>" + String.valueOf(aCustomer.getTotalCharge()) +
 * "</EM>
 * <P>
 * \n" + "On this rental you earned <EM>" +
 * String.valueOf(aCustomer.getTotalFrequentRenterPoints()) +
 * "</EM> frequent renter points
 * <P>
 * ";
 * }
 * 
 * Once these changes have been made, the two value methods look remarkably
 * similar. So I use Pull Up Method (322) on one of them, picking the text
 * version at random. When I pull up, I need to declare the subclass methods as
 * abstract:
 * 
 * class Statement...
 * public String value(Customer aCustomer) {
 * Enumeration rentals = aCustomer.getRentals();
 * String result = headerString(aCustomer);
 * while (rentals.hasMoreElements()) {
 * Rental each = (Rental) rentals.nextElement();
 * result += eachRentalString(each);
 * }
 * result += footerString(aCustomer);
 * return result;
 * }
 * abstract String headerString(Customer aCustomer);
 * abstract String eachRentalString (Rental aRental);
 * abstract String footerString (Customer aCustomer);
 * 
 * I remove the value method from text statement, compile, and test. When that
 * works I remove the value method from the HTML statement, compile, and test
 * again. The result is shown in Figure 11.2.
 * 
 * Image
 * Figure 11.2. Classes after forming the template method
 * 
 * After this refactoring, it is easy to add new kinds of statements. All you
 * have to do is create a subclass of statement that overrides the three
 * abstract methods.
 */
public class F1008_Form_Template_Method
{

}
