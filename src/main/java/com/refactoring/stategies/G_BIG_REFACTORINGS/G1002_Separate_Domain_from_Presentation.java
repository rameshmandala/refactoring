// G1002_Separate_Domain_from_Presentation.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.G_BIG_REFACTORINGS;

/**
 * You have GUI classes that contain domain logic.
 * 
 * Separate the domain logic into separate domain classes
 * 
 * Image
 * Motivation
 * Whenever you hear people talking about objects, you hear about
 * model-view-controller (MVC). This idea underpinned the relationship between
 * the graphical user interface (GUI) and domain objects in Smalltalk-80.
 * 
 * The gold at the heart of MVC is the separation between the user interface
 * code (the view, these days often called the presentation) and the domain
 * logic (the model). The presentation classes contain only the logic needed to
 * deal with the user interface. Domain objects contain no visual code but all
 * the business logic. This separates two complicated parts of the program into
 * pieces that are easier to modify. It also allows multiple presentations of
 * the same business logic. Those experienced in working with objects use this
 * separation instinctively, and it has proved its worth.
 * 
 * But this is not how most people who work with GUIs do their design. Most
 * environments with client-server GUIs use a logical two-tier design: the data
 * sits in the database and the logic sits in the presentation classes. The
 * environment often forces you toward this style of design, making it hard for
 * you to put the logic anywhere else.
 * 
 * Java is a proper object-oriented environment, so you can create nonvisual
 * domain objects that contain business logic. However, you’ll often come across
 * code written in the two-tier style.
 * 
 * Mechanics
 * • Create a domain class for each window.
 * 
 * • If you have a grid, create a class to represent the rows on the grid. Use a
 * collection on the domain class for the window to hold the row domain objects.
 * 
 * • Examine the data on the window. If it is used only for user interface
 * purposes, leave it on the window. If it is used within the domain logic but
 * is not actually displayed on the window, use Move Method (142) to move it to
 * the domain object. If it is used by both the user interface and the domain
 * logic, use Duplicate Observed Data (189) so that it is in both places and
 * kept in sync.
 * 
 * • Examine the logic in the presentation class. Use Extract Method (110) to
 * separate logic about the presentation from domain logic. As you isolate the
 * domain logic, use Move Method (142) to move it to the domain object.
 * 
 * • When you are finished, you will have presentation classes that handle the
 * GUI and domain objects that contain all the business logic. The domain
 * objects will not be well factored, but further refactorings will deal with
 * that.
 * 
 * Example
 * A program that allows users to enter and price orders. The GUI looks like
 * Figure 12.7. The presentation class interacts with a relational database laid
 * out like Figure 12.8.
 * 
 * Image
 * Figure 12.7. The user interface for a starting program
 * 
 * Image
 * Figure 12.8. The database for the order program
 * 
 * All the behavior, both for the GUI and for pricing the orders, is in a single
 * Order Window class.
 * 
 * We begin by creating a suitable order class. We link this to the order window
 * as in Figure 12.9. Because the window contains a grid to display the order
 * lines, we also create an order line class for the rows of the grid.
 * 
 * Image
 * Figure 12.9. Order Window and Order
 * 
 * We work from the window rather than the database. Basing an initial domain
 * model on the database is a reasonable strategy, but our biggest risk is
 * mixing presentation and domain logic. So we separate these on the basis of
 * the windows and refactor the rest later.
 * 
 * With this kind of program it’s useful to look at the structured query
 * language (SQL) statements embedded in the window. Data pulled back from SQL
 * statements is domain data.
 * 
 * The easiest domain data to deal with is that which isn’t directly displayed
 * in the GUI. In the example the database has a codes field in the customers
 * table. The code isn’t directly displayed on the GUI; it is converted to a
 * more human-readable phrase. As such the field is a simple class, such as
 * string, rather than an AWT component. We can safely use Move Field (146) to
 * move that field to the domain class.
 * 
 * We aren’t so lucky with the other fields. They contain AWT components that
 * are displayed on the window and used in the domain objects. For these we need
 * to use Duplicate Observed Data (189). This puts a domain field on the order
 * class with a corresponding AWT field on the order window.
 * 
 * This is a slow process, but by the end we can get all the domain logic fields
 * into the domain class. A good way to drive this process is to try to move all
 * the SQL calls to the domain class. You can do this to move the database logic
 * and the domain data to the domain class together. You can get a nice sense of
 * completion by removing the import of java.sql from the order window. This
 * means you do a lot of Extract Method (110) and Move Method (142).
 * 
 * The resulting classes, as in Figure 12.10, are a long way from being well
 * factored. But this model is enough to separate the domain logic. As you do
 * this refactoring you have to pay attention to where your risk is. If the
 * intermingling of presentation and domain logic is the biggest risk, get them
 * completely separated before you do much else. If other things are more
 * important, such as pricing strategies for the products, get the logic for the
 * important part out of the window and refactor around that logic to create a
 * suitable structure for the area of high risk. Chances are that most of the
 * domain logic will have to be moved out of the order window. If you can
 * refactor and leave some logic in the window, do so to address your biggest
 * risk first.
 * 
 * Image
 * Figure 12.10. Distributing the data to the domain classes
 */
public class G1002_Separate_Domain_from_Presentation
{

}
