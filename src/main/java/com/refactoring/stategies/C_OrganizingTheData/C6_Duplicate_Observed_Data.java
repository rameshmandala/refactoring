// C6_Duplicate_Observed_Data.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * DUPLICATE OBSERVED DATA
 * You have domain data available only in a GUI control, and domain methods need
 * access.
 * 
 * Image
 * Copy the data to a domain object. Set up an observer to synchronize the two
 * pieces of data.
 * 
 * Motivation
 * A well-layered system separates code that handles the user interface from
 * code that handles the business logic. It does this for several reasons. You
 * may want several interfaces for similar business logic; the user interface
 * becomes too complicated if it does both; it is easier to maintain and evolve
 * domain objects separate from the GUI; or you may have different developers
 * handling the different pieces.
 * 
 * Although the behavior can be separated easily, the data often cannot. Data
 * needs to be embedded in GUI control that has the same meaning as data that
 * lives in the domain model. User interface frameworks, from
 * model-view-controller (MVC) onward, used a multitiered system to provide
 * mechanisms to allow you to provide this data and keep everything in sync.
 * 
 * If you come across code that has been developed with a two-tiered approach in
 * which business logic is embedded into the user interface, you need to
 * separate the behaviors. Much of this is about decomposing and moving methods.
 * For the data, however, you cannot just move the data, you have to duplicate
 * it and provide the synchronization mechanism.
 * 
 * Mechanics
 * • Make the presentation class an observer of the domain class [Gang of Four].
 * 
 * Image If there is no domain class yet, create one.
 * 
 * Image If there is no link from the presentation class to the domain class,
 * put the domain class in a field of the presentation class.
 * 
 * • Use Self Encapsulate Field (171) on the domain data within the GUI class.
 * 
 * • Compile and test.
 * 
 * • Add a call to the setting method in the event handler to update the
 * component with its current value using direct access.
 * 
 * Image Put a method in the event handler that updates the value of the
 * component on the basis of its current value. Of course this is completely
 * unnecessary; you are just setting the value to its current value, but by
 * using the setting method, you allow any behavior there to execute.
 * 
 * Image When you make this change, don’t use the getting method for the
 * component; use direct access to the component. Later the getting method will
 * pull the value from the domain, which does not change until the setting
 * method executes.
 * 
 * Image Make sure the event-handling mechanism is triggered by the test code.
 * 
 * • Compile and test.
 * 
 * • Define the data and accessor methods in the domain class.
 * 
 * Image Make sure the setting method on the domain triggers the notify
 * mechanism in the observer pattern.
 * 
 * Image Use the same data type in the domain as is on the presentation (usually
 * a string). Convert the data type in a later refactoring.
 * 
 * • Redirect the accessors to write to the domain field.
 * 
 * • Modify the observer’s update method to copy the data from the domain field
 * to the GUI control.
 * 
 * • Compile and test.
 * 
 * Example
 * I start with the window in Figure 8.1. The behavior is very simple. Whenever
 * you change the value in one of the text fields, the other ones update. If you
 * change the start or end fields, the length is calculated; if you change the
 * length field, the end is calculated.
 * 
 * Image
 * Figure 8.1. A simple GUI window
 * 
 * All the methods are on a single IntervalWindow class. The fields are set to
 * respond to the loss of focus from the field.
 * 
 * public class IntervalWindow extends Frame...
 * java.awt.TextField _startField;
 * java.awt.TextField _endField;
 * java.awt.TextField _lengthField;
 * 
 * class SymFocus extends java.awt.event.FocusAdapter
 * {
 * public void focusLost(java.awt.event.FocusEvent event)
 * {
 * Object object = event.getSource();
 * if (object == _startField)
 * StartField_FocusLost(event);
 * else if (object == _endField)
 * EndField_FocusLost(event);
 * else if (object == _lengthField)
 * LengthField_FocusLost(event);
 * }
 * }
 * 
 * The listener reacts by calling StartField_FocusLost when focus is lost on the
 * start field and EndField_FocusLost and LengthField_FocusLost for the other
 * fields. These event-handling methods look like this:
 * 
 * void StartField_FocusLost(java.awt.event.FocusEvent event) {
 * if (isNotInteger(_startField.getText()))
 * _startField.setText("0");
 * calculateLength();
 * }
 * 
 * void EndField_FocusLost(java.awt.event.FocusEvent event) {
 * if (isNotInteger(_endField.getText()))
 * _endField.setText("0");
 * calculateLength();
 * }
 * 
 * void LengthField_FocusLost(java.awt.event.FocusEvent event) {
 * if (isNotInteger(_lengthField.getText()))
 * _lengthField.setText("0");
 * calculateEnd();
 * }
 * 
 * If you are wondering why I did the window this way, I just did it the easiest
 * way my IDE (Cafe) encouraged me to.
 * 
 * All fields insert a zero if any noninteger characters appear and call the
 * relevant calculation routine:
 * 
 * void calculateLength(){
 * try {
 * int start = Integer.parseInt(_startField.getText());
 * int end = Integer.parseInt(_endField.getText());
 * int length = end - start;
 * _lengthField.setText(String.valueOf(length));
 * } catch (NumberFormatException e) {
 * throw new RuntimeException ("Unexpected Number Format Error");
 * }
 * }
 * void calculateEnd() {
 * try {
 * int start = Integer.parseInt(_startField.getText());
 * int length = Integer.parseInt(_lengthField.getText());
 * int end = start + length;
 * _endField.setText(String.valueOf(end));
 * } catch (NumberFormatException e) {
 * throw new RuntimeException ("Unexpected Number Format Error");
 * }
 * }
 * 
 * My overall task, should I choose to accept it, is to separate the non-visual
 * logic from the GUI. Essentially this means moving calculateLength and
 * calculateEnd to a separate domain class. To do this I need to refer to the
 * start, end, and length data without referring to the window class. The only
 * way I can do this is to duplicate this data in the domain class and
 * synchronize the data with the GUI. This task is described by Duplicate
 * Observed Data (189).
 * 
 * I don’t currently have a domain class, so I create an (empty) one:
 * 
 * class Interval extends Observable {}
 * 
 * The interval window needs a link to this new domain class.
 * 
 * private Interval _subject;
 * 
 * I then need to properly initialize this field and make interval window an
 * observer of the interval. I can do this by putting the following code in
 * interval window’s constructor:
 * 
 * _subject = new Interval();
 * _subject.addObserver(this);
 * update(_subject, null);
 * 
 * I like to put this code at the end of construction process. The call to
 * update ensures that as I duplicate the data in the domain class, the GUI is
 * initialized from the domain class. To do this I need to declare that interval
 * window implements Observable:
 * 
 * public class IntervalWindow extends Frame implements Observer
 * 
 * To implement observer I need to create an update method. For the moment this
 * can be blank:
 * 
 * public void update(Observable observed, Object arg) {
 * }
 * 
 * I can compile and test at this point. I haven’t made any real changes yet,
 * but I can make mistakes in the simplest places.
 * 
 * Now I can turn my attention to moving fields. As usual I make the changes one
 * field at a time. To demonstrate my command of the English language, I’ll
 * start with the end field. The first task is to apply Self Encapsulate Field
 * (171). Text fields are updated with getText and setText methods. I create
 * accessors that call these
 * 
 * String getEnd() {
 * return _endField.getText();
 * }
 * void setEnd (String arg) {
 * _endField.setText(arg);
 * }
 * 
 * I find every reference to _endField and replace them with the appropriate
 * accessors:
 * 
 * void calculateLength(){
 * try {
 * int start = Integer.parseInt(_startField.getText());
 * int end = Integer.parseInt(getEnd());
 * int length = end - start;
 * _lengthField.setText(String.valueOf(length));
 * } catch (NumberFormatException e) {
 * throw new RuntimeException ("Unexpected Number Format Error");
 * }
 * }
 * 
 * void calculateEnd() {
 * try {
 * int start = Integer.parseInt(_startField.getText());
 * int length = Integer.parseInt(_lengthField.getText());
 * int end = start + length;
 * setEnd(String.valueOf(end));
 * } catch (NumberFormatException e) {
 * throw new RuntimeException ("Unexpected Number Format Error");
 * }
 * }
 * 
 * void EndField_FocusLost(java.awt.event.FocusEvent event) {
 * if (isNotInteger(getEnd()))
 * setEnd("0");
 * calculateLength();
 * }
 * 
 * That’s the normal process for Self Encapsulate Field (171). However, when you
 * are working with a GUI, there is a complication. The user can change the
 * field value directly without calling setEnd. So I need to put a call to
 * setEnd into the event handler for the GUI. This call changes the value of the
 * end field to the current value of the end field. Of course this does nothing
 * at the moment, but it does ensure the user input goes through the setting
 * method:
 * 
 * void EndField_FocusLost(java.awt.event.FocusEvent event) {
 * setEnd(_endField.getText());
 * if (isNotInteger(getEnd()))
 * setEnd("0");
 * calculateLength();
 * }
 * 
 * In this call I don’t use getEnd; instead I access the field directly. I do
 * this because later in the refactoring getEnd gets a value from the domain
 * object, not from the field. At that point using it would mean that every time
 * the user were to change the value of the field, this code would change it
 * back again, so here I must use direct access. At this point I can compile and
 * test the encapsulated behavior.
 * 
 * Now I add the end field to the domain class:
 * 
 * class Interval...
 * private String _end = "0";
 * 
 * I initialize it to the same value it is initialized to in the GUI. I now add
 * getting and setting methods:
 * 
 * class Interval...
 * 
 * String getEnd() {
 * return _end;
 * }
 * void setEnd (String arg) {
 * _end = arg;
 * setChanged();
 * notifyObservers();
 * }
 * 
 * Because I’m using the observer pattern, I have to add the notification code
 * into the setting method. I use a string, not a (more logical) number. This is
 * because I want to make the smallest possible change. Once I’ve successfully
 * duplicated the data, I can change the internal data type to an integer.
 * 
 * I can now do one more compile and test before I perform the duplication. By
 * doing all this preparatory work, I’ve minimized the risk in this tricky step.
 * 
 * The first change is updating the accessors on IntervalWindow to use Interval.
 * 
 * class IntervalWindow...
 * String getEnd() {
 * return _subject.getEnd();
 * }
 * void setEnd (String arg) {
 * _subject.setEnd(arg);
 * }
 * 
 * I also need to update update to ensure the GUI reacts to the notification:
 * 
 * class IntervalWindow...
 * public void update(Observable observed, Object arg) {
 * _endField.setText(_subject.getEnd());
 * }
 * 
 * This is the other place where I have to use direct access. If I were to call
 * the setting method, I would get into an infinite recursion.
 * 
 * I can now compile and test, and the data is properly duplicated.
 * 
 * I can repeat for the other two fields. Once this is done I can apply Move
 * Method (142) to move calculateEnd and calculateLength over to the interval
 * class. At that point I have a domain class that contains all the domain
 * behavior and data and separates it from the GUI code.
 * 
 * If I’ve done this, I consider getting rid of the GUI class completely. If my
 * class is an older AWT class, I can get better looks by using Swing, and Swing
 * does a better job with coordination. I can build the Swing GUI on top of the
 * domain class. When I’m happy, I can remove the old GUI class.
 * 
 * Using Event Listeners
 * Duplicate Observed Data also applies if you use event listeners instead of
 * observer/observable. In this case you need to create a listener and event in
 * the domain model (or you can use classes from AWT if you don’t mind the
 * dependency). The domain object then needs to register the listeners in the
 * same way that observable does and send an event to them when it changes, as
 * in the update method. The interval window can then use an inner class to
 * implement the listener interface and call the appropriate update methods.
 */
public class C6_Duplicate_Observed_Data
{

}
