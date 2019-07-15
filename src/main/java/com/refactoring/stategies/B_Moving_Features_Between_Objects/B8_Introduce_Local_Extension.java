// B8_Introduce_Local_Extension.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.B_Moving_Features_Between_Objects;

/**
 * INTRODUCE LOCAL EXTENSION
 * A server class you are using needs several additional methods, but you can’t
 * modify the class.
 * 
 * Image
 * Create a new class that contains these extra methods. Make this extension
 * class a subclass or a wrapper of the original.
 * 
 * Motivation
 * Authors of classes sadly are not omniscient, and they fail to provide useful
 * methods for you. If you can modify the source, often the best thing is to add
 * that method. However, you often cannot modify the source. If you need one or
 * two methods, you can use Introduce Foreign Method (162). Once you get beyond
 * a couple of these methods, however, they get out of hand. So you need to
 * group the methods together in a sensible place for them. The standard
 * object-oriented techniques of subclassing and wrapping are an obvious way to
 * do this. In these circumstances I call the subclass or wrapper a local
 * extension.
 * 
 * A local extension is a separate class, but it is a subtype of the class it is
 * extending. That means it supports all the things the original can do but also
 * adds the extra features. Instead of using the original class, you instantiate
 * the local extension and use it.
 * 
 * By using the local extension you keep to the principle that methods and data
 * should be packaged into well-formed units. If you keep putting code in other
 * classes that should lie in the extension, you end up complicating the other
 * classes, and making it harder to reuse these methods.
 * 
 * In choosing between subclass and wrapper, I usually prefer the subclass
 * because it is less work. The biggest roadblock to a subclass is that it needs
 * to apply at object-creation time. If I can take over the creation process
 * that’s no problem. The problem occurs if you apply the local extension later.
 * Subclassing forces me to create a new object of that subclass. If other
 * objects refer to the old one, I have two objects with the original’s data. If
 * the original is immutable, there is no problem; I can safely take a copy. But
 * if the original can change, there is a problem, because changes in one object
 * won’t change the other and I have to use a wrapper. That way changes made
 * through the local extension affect the original object and vice versa.
 * 
 * Mechanics
 * • Create an extension class either as a subclass or a wrapper of the
 * original.
 * 
 * • Add converting constructors to the extension.
 * 
 * Image A constructor takes the original as an argument. The subclass version
 * calls an appropriate superclass constructor; the wrapper version sets the
 * delegate field to the argument.
 * 
 * • Add new features to the extension.
 * 
 * • Replace the original with the extension where needed.
 * 
 * • Move any foreign methods defined for this class onto the extension.
 * 
 * Examples
 * I had to do this kind of thing quite a bit with Java 1.0.1 and the date
 * class. The calendar class in 1.1 gave me a lot of the behavior I wanted, but
 * before it arrived, it gave me quite a few opportunities to use extension. I
 * use it as an example here.
 * 
 * The first thing to decide is whether to use a subclass or a wrapper.
 * Subclassing is the more obvious way:
 * 
 * class MfDateSub extends Date {
 * public MfDateSub nextDay()...
 * public int dayOfYear()...
 * 
 * A wrapper uses delegation:
 * 
 * class mfDateWrap {
 * private Date _original;
 * 
 * Example: Using a Subclass
 * First I create the new date as a subclass of the original:
 * 
 * class MfDateSub extends Date
 * 
 * Next I deal with changing between dates and the extension. The constructors
 * of the original need to be repeated with simple delegation:
 * 
 * public MfDateSub (String dateString) {
 * super (dateString);
 * };
 * 
 * Now I add a converting constructor, one that takes an original as an
 * argument:
 * 
 * public MfDateSub (Date arg) {
 * super (arg.getTime());
 * }
 * 
 * I can now add new features to the extension and use Move Method (142) to move
 * any foreign methods over to the extension:
 * 
 * client class...
 * private static Date nextDay(Date arg) {
 * // foreign method, should be on date
 * return new Date (arg.getYear(),arg.getMonth(), arg.getDate() + 1);
 * }
 * 
 * becomes
 * 
 * class MfDate...
 * Date nextDay() {
 * return new Date (getYear(),getMonth(), getDate() + 1);
 * }
 * 
 * Example: Using a Wrapper
 * I start by declaring the wrapping class:
 * 
 * class mfDateWrap {
 * private Date _original;
 * }
 * 
 * With the wrapping approach, I need to set up the constructors differently.
 * The original constructors are implemented with simple delegation:
 * 
 * public MfDateWrap (String dateString) {
 * _original = new Date(dateString);
 * };
 * 
 * The converting constructor now just sets the instance variable:
 * 
 * public MfDateWrap (Date arg) {
 * _original = arg;
 * }
 * 
 * Then there is the tedious task of delegating all the methods of the original
 * class. I show only a couple.
 * 
 * public int getYear() {
 * return _original.getYear();
 * }
 * 
 * public boolean equals(Object arg) {
 * if (this == arg) return true;
 * if (! (arg instanceof MfDateWrap)) return false;
 * MfDateWrap other = ((MfDateWrap) arg);
 * return (_original.equals(other._original));
 * }
 * 
 * Once I’ve done this I can use Move Method (142) to put date-specific behavior
 * onto the new class:
 * 
 * client class...
 * private static Date nextDay(Date arg) {
 * // foreign method, should be on date
 * return new Date (arg.getYear(),arg.getMonth(), arg.getDate() + 1);
 * }
 * 
 * becomes
 * 
 * class MfDate...
 * Date nextDay() {
 * return new Date (getYear(),getMonth(), getDate() + 1);
 * }
 * 
 * A particular problem with using wrappers is how to deal with methods that
 * take an original as an argument, such as
 * 
 * public boolean after (Date arg)
 * 
 * Because I can’t alter the original, I can only do after in one direction:
 * 
 * aWrapper.after(aDate) // can be made to work
 * aWrapper.after(anotherWrapper) // can be made to work
 * aDate.after(aWrapper) // will not work
 * 
 * The purpose of this kind of overriding is to hide the fact I’m using a
 * wrapper from the user of the class. This is good policy because the user of
 * wrapper really shouldn’t care about the wrapper and should be able to treat
 * the two equally. However, I can’t completely hide this information. The
 * problem lies in certain system methods, such as equals. Ideally you would
 * think that you could override equals on MfDateWrap like this
 * 
 * public boolean equals (Date arg) // causes problems
 * 
 * This is dangerous because although I can make it work for my own purposes,
 * other parts of the java system assume that equals is symmetric: that if
 * a.equals(b) then b.equals(a). If I violate this rule I’ll run into a bevy of
 * strange bugs. The only way to avoid that would be to modify Date, and if I
 * could do that I wouldn’t be using this refactoring. So in situations like
 * this I just have to expose the fact that I’m wrapping. For equality tests
 * this means a new method name.
 * 
 * public boolean equalsDate (Date arg)
 * 
 * I can avoid testing the type of unknown objects by providing versions of this
 * method for both Date and MfDateWrap.
 * 
 * public boolean equalsDate (MfDateWrap arg)
 * 
 * The same problem is not an issue with subclassing, if I don’t override the
 * operation. If I do override, I become completely confused with the method
 * lookup. I usually don’t do override methods with extensions; I usually just
 * add methods.
 */
public class B8_Introduce_Local_Extension
{

}
