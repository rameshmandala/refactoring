// B4_Encapsulate_Collection.java - (insert one line description here)
// (C) Copyright 2018 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.C_OrganizingTheData;

/**
 * A method returns a collection.
 * 
 * Image
 * Make it return a read-only view and provide add/remove methods.
 * 
 * Motivation
 * Often a class contains a collection of instances. This collection might be an
 * array, list, set, or vector. Such cases often have the usual getter and
 * setter for the collection.
 * 
 * However, collections should use a protocol slightly different from that for
 * other kinds of data. The getter should not return the collection object
 * itself, because that allows clients to manipulate the contents of the
 * collection without the owning class’s knowing what is going on. It also
 * reveals too much to clients about the object’s internal data structures. A
 * getter for a multivalued attribute should return something that prevents
 * manipulation of the collection and hides unnecessary details about its
 * structure. How you do this varies depending on the version of Java you are
 * using.
 * 
 * In addition there should not be a setter for collection: rather there should
 * be operations to add and remove elements. This gives the owning object
 * control over adding and removing elements from the collection.
 * 
 * With this protocol the collection is properly encapsulated, which reduces the
 * coupling of the owning class to its clients.
 * 
 * Mechanics
 * • Add an add and remove method for the collection.
 * 
 * • Initialize the field to an empty collection.
 * 
 * • Compile.
 * 
 * • Find callers of the setting method. Either modify the setting method to use
 * the add and remove operations or have the clients call those operations
 * instead.
 * 
 * Image Setters are used in two cases: when the collection is empty and when
 * the setter is replacing a nonempty collection.
 * 
 * Image You may wish to use Rename Method (273) to rename the setter. Change it
 * from set to initialize or replace.
 * 
 * • Compile and test.
 * 
 * • Find all users of the getter that modify the collection. Change them to use
 * the add and remove methods. Compile and test after each change.
 * 
 * • When all uses of the getter that modify have been changed, modify the
 * getter to return a read-only view of the collection.
 * 
 * Image In Java 2, this is the appropriate unmodifiable collection view.
 * 
 * Image In Java 1.1, you should return a copy of the collection.
 * 
 * • Compile and test.
 * 
 * • Find the users of the getter. Look for code that should be on the host
 * object. Use Extract Method (110) and Move Method (142) to move the code to
 * the host object.
 * 
 * For Java 2, you are done with that. For Java 1.1, however, clients may prefer
 * to use an enumeration. To provide the enumeration:
 * 
 * • Change the name of the current getter and add a new getter to return an
 * enumeration. Find users of the old getter and change them to use one of the
 * new methods.
 * 
 * Image If this is too big a jump, use Rename Method (273) on the old getter,
 * create a new method that returns an enumeration, and change callers to use
 * the new method.
 * 
 * • Compile and test.
 * 
 * Examples
 * Java 2 added a whole new group of classes to handle collections. It not only
 * added new classes but also altered the style of using collections. As a
 * result the way you encapsulate a collection is different depending on whether
 * you use the Java 2 collections or the Java 1.1 collections. I discuss the
 * Java 2 approach first, because I expect the more functional Java 2
 * collections to displace the Java 1.1 collections during the lifetime of this
 * book.
 * 
 * Example: Java 2
 * A person is taking courses. Our course is pretty simple:
 * 
 * class Course...
 * public Course (String name, boolean isAdvanced) {...};
 * public boolean isAdvanced() {...};
 * 
 * I’m not going to bother with anything else on the course. The interesting
 * class is the person:
 * 
 * class Person...
 * public Set getCourses() {
 * return _courses;
 * }
 * public void setCourses(Set arg) {
 * _courses = arg;
 * }
 * private Set _courses;
 * 
 * With this interface, clients adds courses with code such as
 * 
 * Person kent = new Person();
 * Set s = new HashSet();
 * s.add(new Course ("Smalltalk Programming", false));
 * s.add(new Course ("Appreciating Single Malts", true));
 * kent.setCourses(s);
 * Assert.equals (2, kent.getCourses().size());
 * Course refact = new Course ("Refactoring", true);
 * kent.getCourses().add(refact);
 * kent.getCourses().add(new Course ("Brutal Sarcasm", false));
 * Assert.equals (4, kent.getCourses().size());
 * kent.getCourses().remove(refact);
 * Assert.equals (3, kent.getCourses().size());
 * 
 * A client that wants to know about advanced courses might do it this way:
 * 
 * Iterator iter = person.getCourses().iterator();
 * int count = 0;
 * while (iter.hasNext()) {
 * Course each = (Course) iter.next();
 * if (each.isAdvanced()) count ++;
 * }
 * 
 * The first thing I want to do is to create the proper modifiers for the
 * collection and compile, as follows:
 * 
 * class Person
 * public void addCourse (Course arg) {
 * _courses.add(arg);
 * }
 * public void removeCourse (Course arg) {
 * _courses.remove(arg);
 * }
 * 
 * Life will be easier if I initialize the field as well:
 * 
 * private Set _courses = new HashSet();
 * 
 * I then look at the users of the setter. If there are many clients and the
 * setter is used heavily, I need to replace the body of the setter to use the
 * add and remove operations. The complexity of this process depends on how the
 * setter is used. There are two cases. In the simplest case the client uses the
 * setter to initialize the values, that is, there are no courses before the
 * setter is applied. In this case I replace the body of the setter to use the
 * add method:
 * 
 * class Person...
 * public void setCourses(Set arg) {
 * Assert.isTrue(_courses.isEmpty());
 * Iterator iter = arg.iterator();
 * while (iter.hasNext()) {
 * addCourse((Course) iter.next());
 * }
 * }
 * 
 * After changing the body this way, it is wise to use Rename Method (273) to
 * make the intention clearer.
 * 
 * public void initializeCourses(Set arg) {
 * Assert.isTrue(_courses.isEmpty());
 * Iterator iter = arg.iterator();
 * while (iter.hasNext()) {
 * addCourse((Course) iter.next());
 * }
 * }
 * 
 * In the more general case I have to use the remove method to remove every
 * element first and then add the elements. But I find that occurs rarely (as
 * general cases often do).
 * 
 * If I know that I don’t have any additional behavior when adding elements as I
 * initialize, I can remove the loop and use addAll.
 * 
 * public void initializeCourses(Set arg) {
 * Assert.isTrue(_courses.isEmpty());
 * _courses.addAll(arg);
 * }
 * 
 * I can’t just assign the set, even though the previous set was empty. If the
 * client were to modify the set after passing it in, that would violate
 * encapsulation. I have to make a copy.
 * 
 * If the clients simply create a set and use the setter, I can get them to use
 * the add and remove methods directly and remove the setter completely. Code
 * such as
 * 
 * Person kent = new Person();
 * Set s = new HashSet();
 * s.add(new Course ("Smalltalk Programming", false));
 * s.add(new Course ("Appreciating Single Malts", true));
 * kent.initializeCourses(s);
 * 
 * becomes
 * 
 * Person kent = new Person();
 * kent.addCourse(new Course ("Smalltalk Programming", false));
 * kent.addCourse(new Course ("Appreciating Single Malts", true));
 * 
 * Now I start looking at users of the getter. My first concern is cases in
 * which someone uses the getter to modify the underlying collection, for
 * example:
 * 
 * kent.getCourses().add(new Course ("Brutal Sarcasm", false));
 * 
 * I need to replace this with a call to the new modifier:
 * 
 * kent.addCourse(new Course ("Brutal Sarcasm", false));
 * 
 * Once I’ve done this for everyone, I can check that nobody is modifying
 * through the getter by changing the getter body to return an unmodifiable
 * view:
 * 
 * public Set getCourses() {
 * return Collections.unmodifiableSet(_courses);
 * }
 * 
 * At this point I’ve encapsulated the collection. No one can change the
 * elements of collection except through methods on the person.
 * 
 * Moving Behavior into the Class
 * I have the right interface. Now I like to look at the users of the getter to
 * find code that ought to be on person. Code such as
 * 
 * Iterator iter = person.getCourses().iterator();
 * int count = 0;
 * while (iter.hasNext()) {
 * Course each = (Course) iter.next();
 * if (each.isAdvanced()) count ++;
 * }
 * 
 * is better moved to person because it uses only person’s data. First I use
 * Extract Method (110) on the code:
 * 
 * int numberOfAdvancedCourses(Person person) {
 * Iterator iter = person.getCourses().iterator();
 * int count = 0;
 * while (iter.hasNext()) {
 * Course each = (Course) iter.next();
 * if (each.isAdvanced()) count ++;
 * }
 * return count;
 * }
 * 
 * And then I use Move Method (142) to move it to person:
 * 
 * class Person...
 * int numberOfAdvancedCourses() {
 * Iterator iter = getCourses().iterator();
 * int count = 0;
 * while (iter.hasNext()) {
 * Course each = (Course) iter.next();
 * if (each.isAdvanced()) count ++;
 * }
 * return count;
 * }
 * 
 * A common case is
 * 
 * kent.getCourses().size()
 * 
 * which can be changed to the more readable
 * 
 * kent.numberOfCourses()
 * 
 * class Person...
 * public int numberOfCourses() {
 * return _courses.size();
 * }
 * 
 * A few years ago I was concerned that moving this kind of behavior over to
 * person would lead to a bloated person class. In practice, I’ve found that
 * usually isn’t a problem.
 * 
 * Example: Java 1.1
 * In many ways the Java 1.1 case is pretty similar to the Java 2. I use the
 * same example but with a vector:
 * 
 * class Person...
 * public Vector getCourses() {
 * return _courses;
 * }
 * public void setCourses(Vector arg) {
 * _courses = arg;
 * }
 * private Vector _courses;
 * 
 * Again I begin by creating modifiers and initializing the field as follows:
 * 
 * class Person
 * public void addCourse(Course arg) {
 * _courses.addElement(arg);
 * }
 * public void removeCourse(Course arg) {
 * _courses.removeElement(arg);
 * }
 * private Vector _courses = new Vector();
 * 
 * I can modify the setCourses to initialize the vector:
 * 
 * public void initializeCourses(Vector arg) {
 * Assert.isTrue(_courses.isEmpty());
 * Enumeration e = arg.elements();
 * while (e.hasMoreElements()) {
 * addCourse((Course) e.nextElement());
 * }
 * }
 * 
 * I change users of the getter to use the modifiers, so
 * 
 * kent.getCourses().addElement(new Course ("Brutal Sarcasm", false));
 * 
 * becomes
 * 
 * kent.addCourse(new Course ("Brutal Sarcasm", false));
 * 
 * My final step changes because vectors do not have an unmodifiable version:
 * 
 * class Person...
 * Vector getCourses() {
 * return (Vector) _courses.clone();
 * }
 * 
 * At this point I’ve encapsulated the collection. No one can change the
 * elements of collection except through the person.
 * 
 * Example: Encapsulating Arrays
 * Arrays are commonly used, especially by programmers who are not familiar with
 * collections. I rarely use arrays, because I prefer the more behaviorally rich
 * collections. I often change arrays into collections as I do the
 * encapsulation.
 * 
 * This time I begin with a string array for skills:
 * 
 * String[] getSkills() {
 * return _skills;
 * }
 * void setSkills (String[] arg) {
 * _skills = arg;
 * }
 * String[] _skills;
 * 
 * Again I begin by providing a modifier operation. Because the client is likely
 * to change a value at a particular position, I need a set operation for a
 * particular element:
 * 
 * void setSkill(int index, String newSkill) {
 * _skills[index] = newSkill;
 * }
 * 
 * If I need to set the whole array, I can do so using the following operation:
 * 
 * void setSkills (String[] arg) {
 * _skills = new String[arg.length];
 * for (int i=0; i < arg.length; i++)
 * setSkill(i,arg[i]);
 * }
 * 
 * There are numerous pitfalls here if things have to be done with the removed
 * elements. The situation is complicated by what happens when the argument
 * array is different in length from the original array. That’s another reason
 * to prefer a collection.
 * 
 * At this point I can start looking at users of the getter. I can change
 * 
 * kent.getSkills()[1] = "Refactoring";
 * 
 * to
 * 
 * kent.setSkill(1,"Refactoring");
 * 
 * When I’ve made all the changes, I can modify the getter to return a copy:
 * 
 * String[] getSkills() {
 * String[] result = new String[_skills.length];
 * System.arraycopy(_skills, 0, result, 0, _skills.length);
 * return result;
 * }
 * 
 * This is a good point to replace the array with a list:
 * 
 * class Person...
 * String[] getSkills() {
 * return (String[]) _skills.toArray(new String[0]);
 * }
 * void setSkill(int index, String newSkill) {
 * _skills.set(index,newSkill);
 * }
 * List _skills = new ArrayList();
 */
public class C10_Encapsulate_Collection
{

}
