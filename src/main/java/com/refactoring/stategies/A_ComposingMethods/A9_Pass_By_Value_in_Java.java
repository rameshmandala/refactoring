package com.refactoring.stategies.A_ComposingMethods;

public class A9_Pass_By_Value_in_Java
{
    /**
     * Pass By Value in Java
     * Use of pass by value often is a source of confusion in Java. Java
     * strictly uses pass by value in all places, thus the following program:
     * 
     * class Param {
     * public static void main(String[] args) {
     * int x = 5;
     * triple(x);
     * System.out.println ("x after triple: " + x);
     * }
     * private static void triple(int arg) {
     * arg = arg * 3;
     * System.out.println ("arg in triple: " + arg);
     * }
     * }
     * 
     * produces the following output:
     * 
     * arg in triple: 15
     * x after triple: 5
     * 
     * The confusion exists with objects. Say I use a date, then this program:
     * 
     * class Param {
     * 
     * public static void main(String[] args) {
     * Date d1 = new Date ("1 Apr 98");
     * nextDateUpdate(d1);
     * System.out.println ("d1 after nextDay: " + d1);
     * 
     * Date d2 = new Date ("1 Apr 98");
     * nextDateReplace(d2);
     * System.out.println ("d2 after nextDay: " + d2);
     * }
     * private static void nextDateUpdate (Date arg) {
     * arg.setDate(arg.getDate() + 1);
     * System.out.println ("arg in nextDay: " + arg);
     * }
     * private static void nextDateReplace (Date arg) {
     * arg = new Date (arg.getYear(), arg.getMonth(), arg.getDate() + 1);
     * System.out.println ("arg in nextDay: " + arg);
     * }
     * }
     * 
     * It produces this output
     * 
     * arg in nextDay: Thu Apr 02 00:00:00 EST 1998
     * d1 after nextDay: Thu Apr 02 00:00:00 EST 1998
     * arg in nextDay: Thu Apr 02 00:00:00 EST 1998
     * d2 after nextDay: Wed Apr 01 00:00:00 EST 1998
     * 
     * Essentially the object reference is passed by value. This allows me to
     * modify the object but does not take into account the reassigning of the
     * parameter.
     * 
     * Java 1.1 and later versions allow you to mark a parameter as final; this
     * prevents assignment to the variable. It still allows you to modify the
     * object the variable refers to. I always treat my parameters as final, but
     * I confess I rarely mark them so in the parameter list.
     */
}
