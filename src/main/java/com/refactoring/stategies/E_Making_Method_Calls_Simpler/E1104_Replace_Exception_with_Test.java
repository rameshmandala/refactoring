// E1104_Replace_Exception_with_Test.java - (insert one line description here)
// (C) Copyright 2019 Hewlett Packard Enterprise Development LP

package com.refactoring.stategies.E_Making_Method_Calls_Simpler;

/**
 * You are throwing an exception on a condition the caller could have checked
 * first.
 * 
 * Change the caller to make the test first.
 * 
 * double getValueForPeriod (int periodNumber) {
 * try {
 * return _values[periodNumber];
 * } catch (ArrayIndexOutOfBoundsException e) {
 * return 0;
 * }
 * }
 * 
 * Image
 * double getValueForPeriod (int periodNumber) {
 * if (periodNumber >= _values.length) return 0;
 * return _values[periodNumber];
 * }
 * 
 * Motivation
 * Exceptions are an important advance in programming languages. They allow us
 * to avoid complex codes by use of Replace Error Code with Exception (310).
 * Like so many pleasures, exceptions can be used to excess, and they cease to
 * be pleasurable. (Even I can tire of Aventinus [Jackson].) Exceptions should
 * be used for exceptional behavior behavior that is an unexpected error. They
 * should not act as a substitute for conditional tests. If you can reasonably
 * expect the caller to check the condition before calling the operation, you
 * should provide a test, and the caller should use it.
 * 
 * Mechanics
 * • Put a test up front and copy the code from the catch block into the
 * appropriate leg of the if statement.
 * 
 * • Add an assertion to the catch block to notify you whether the catch block
 * is executed.
 * 
 * • Compile and test.
 * 
 * • Remove the catch block and the try block if there are no other catch
 * blocks.
 * 
 * • Compile and test.
 * 
 * Example
 * For this example I use an object that manages resources that are expensive to
 * create but can be reused. Database connections are a good example of this.
 * Such a manager has two pools of resources, one that is available for use and
 * one that is allocated. When a client wants a resource, the pool hands it out
 * and transfers it from the available pool to the allocated pool. When a client
 * releases a resource, the manager passes it back. If a client requests a
 * resource and none is available, the manager creates a new one.
 * 
 * The method for giving out resources might look like this:
 * 
 * class ResourcePool
 * Resource getResource() {
 * Resource result;
 * try {
 * result = (Resource) _available.pop();
 * _allocated.push(result);
 * return result;
 * } catch (EmptyStackException e) {
 * result = new Resource();
 * _allocated.push(result);
 * return result;
 * }
 * }
 * Stack _available;
 * Stack _allocated;
 * 
 * In this case running out of resources is not an unexpected occurrence, so I
 * should not use an exception.
 * 
 * To remove the exception I first add an appropriate up-front test and do the
 * empty behavior there:
 * 
 * Resource getResource() {
 * Resource result;
 * if (_available.isEmpty()) {
 * result = new Resource();
 * _allocated.push(result);
 * return result;
 * }
 * else {
 * try {
 * result = (Resource) _available.pop();
 * _allocated.push(result);
 * return result;
 * } catch (EmptyStackException e) {
 * result = new Resource();
 * _allocated.push(result);
 * return result;
 * }
 * }
 * }
 * 
 * With this the exception should never occur. I can add an assertion to check
 * this:
 * 
 * Resource getResource() {
 * Resource result;
 * if (_available.isEmpty()) {
 * result = new Resource();
 * _allocated.push(result);
 * return result;
 * }
 * else {
 * try {
 * result = (Resource) _available.pop();
 * _allocated.push(result);
 * return result;
 * } catch (EmptyStackException e) {
 * Assert.shouldNeverReachHere("available was empty on pop");
 * result = new Resource();
 * _allocated.push(result);
 * return result;
 * }
 * }
 * }
 * 
 * class Assert...
 * static void shouldNeverReachHere(String message) {
 * throw new RuntimeException (message);
 * }
 * 
 * Now I can compile and test. If all goes well, I can remove the try block
 * completely:
 * 
 * Resource getResource() {
 * Resource result;
 * if (_available.isEmpty()) {
 * result = new Resource();
 * _allocated.push(result);
 * return result;
 * }
 * else {
 * result = (Resource) _available.pop();
 * _allocated.push(result);
 * return result;
 * }
 * }
 * 
 * After this I usually find I can clean up the conditional code. Here I can use
 * Consolidate Duplicate Conditional Fragments (243):
 * 
 * Resource getResource() {
 * Resource result;
 * if (_available.isEmpty())
 * result = new Resource();
 * else
 * result = (Resource) _available.pop();
 * _allocated.push(result);
 * return result;
 * }
 */
public class E1104_Replace_Exception_with_Test
{

}
