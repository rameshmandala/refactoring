package com.refactoring.stategies.A_ComposingMethods;

public class A1_ExtractMethod
{
    /*
     * 
     * You have a code fragment that can be grouped together.
     * 
     * Turn the fragment into a method whose name explains the purpose of the
     * method.
     * 
     * void printOwing(double amount) {
     * printBanner();
     * 
     * //print details
     * System.out.println ("name:" + _name);
     * System.out.println ("amount" + amount);
     * }
     * 
     * Image
     * void printOwing(double amount) {
     * printBanner();
     * printDetails(amount);
     * }
     * 
     * void printDetails (double amount) {
     * System.out.println ("name:" + _name);
     * System.out.println ("amount" + amount);
     * }
     * 
     * Motivation
     * Extract Method is one of the most common refactorings I do. I look at a
     * method that is too long or look at code that needs a comment to
     * understand its purpose. I then turn that fragment of code into its own
     * method.
     * 
     * I prefer short, well-named methods for several reasons. First, it
     * increases the chances that other methods can use a method when the method
     * is finely grained. Second, it allows the higher-level methods to read
     * more like a series of comments. Overriding also is easier when the
     * methods are finely grained.
     * 
     * It does take a little getting used to if you are used to seeing larger
     * methods. And small methods really work only when you have good names, so
     * you need to pay attention to naming. People sometimes ask me what length
     * I look for in a method. To me length is not the issue. The key is the
     * semantic distance between the method name and the method body. If
     * extracting improves clarity, do it, even if the name is longer than the
     * code you have extracted.
     * 
     * Mechanics
     * • Create a new method, and name it after the intention of the method
     * (name it by what it does, not by how it does it).
     * 
     * Image If the code you want to extract is very simple, such as a single
     * message or function call, you should extract it if the name of the new
     * method will reveal the intention of the code in a better way. If you
     * can’t come up with a more meaningful name, don’t extract the code.
     * 
     * • Copy the extracted code from the source method into the new target
     * method.
     * 
     * • Scan the extracted code for references to any variables that are local
     * in scope to the source method. These are local variables and parameters
     * to the method.
     * 
     * • See whether any temporary variables are used only within this extracted
     * code. If so, declare them in the target method as temporary variables.
     * 
     * • Look to see whether any of these local-scope variables are modified by
     * the extracted code. If one variable is modified, see whether you can
     * treat the extracted code as a query and assign the result to the variable
     * concerned. If this is awkward, or if there is more than one such
     * variable, you can’t extract the method as it stands. You may need to use
     * Split Temporary Variable (128) and try again. You can eliminate temporary
     * variables with Replace Temp with Query (120) (see the discussion in the
     * examples).
     * 
     * • Pass into the target method as parameters local-scope variables that
     * are read from the extracted code.
     * 
     * • Compile when you have dealt with all the locally-scoped variables.
     * 
     * • Replace the extracted code in the source method with a call to the
     * target method.
     * 
     * Image If you have moved any temporary variables over to the target
     * method, look to see whether they were declared outside of the extracted
     * code. If so, you can now remove the declaration.
     * 
     * • Compile and test.
     * 
     * Example: No Local Variables
     * In the simplest case, Extract Method (110) is trivially easy. Take the
     * following method:
     * 
     * void printOwing() {
     * 
     * Enumeration e = _orders.elements();
     * double outstanding = 0.0;
     * 
     * // print banner
     * System.out.println ("**************************");
     * System.out.println ("***** Customer Owes ******");
     * System.out.println ("**************************");
     * 
     * // calculate outstanding
     * while (e.hasMoreElements()) {
     * Order each = (Order) e.nextElement();
     * outstanding += each.getAmount();
     * }
     * 
     * //print details
     * System.out.println ("name:" + _name);
     * System.out.println ("amount" + outstanding);
     * }
     * 
     * It is easy to extract the code that prints the banner. I just cut, paste,
     * and put in a call:
     * 
     * void printOwing() {
     * 
     * Enumeration e = _orders.elements();
     * double outstanding = 0.0;
     * 
     * printBanner();
     * 
     * // calculate outstanding
     * while (e.hasMoreElements()) {
     * Order each = (Order) e.nextElement();
     * outstanding += each.getAmount();
     * }
     * 
     * //print details
     * System.out.println ("name:" + _name);
     * System.out.println ("amount" + outstanding);
     * }
     * 
     * void printBanner() {
     * // print banner
     * System.out.println ("**************************");
     * System.out.println ("***** Customer Owes ******");
     * System.out.println ("**************************");
     * }
     * 
     * Example: Using Local Variables
     * So what’s the problem? The problem is local variables: parameters passed
     * into the original method and temporaries declared within the original
     * method. Local variables are only in scope in that method, so when I use
     * Extract Method (110), these variables cause me extra work. In some cases
     * they even prevent me from doing the refactoring at all.
     * 
     * The easiest case with local variables is when the variables are read but
     * not changed. In this case I can just pass them in as a parameter. So if I
     * have the following method:
     * 
     * void printOwing() {
     * 
     * Enumeration e = _orders.elements();
     * double outstanding = 0.0;
     * 
     * printBanner();
     * 
     * // calculate outstanding
     * while (e.hasMoreElements()) {
     * Order each = (Order) e.nextElement();
     * outstanding += each.getAmount();
     * }
     * 
     * //print details
     * System.out.println ("name:" + _name);
     * System.out.println ("amount" + outstanding);
     * }
     * 
     * I can extract the printing of details with a method with one parameter:
     * 
     * void printOwing() {
     * 
     * Enumeration e = _orders.elements();
     * double outstanding = 0.0;
     * 
     * printBanner();
     * 
     * // calculate outstanding
     * while (e.hasMoreElements()){
     * Order each = (Order) e.nextElement();
     * outstanding += each.getAmount();
     * }
     * 
     * printDetails(outstanding);
     * }
     * 
     * void printDetails (double outstanding) {
     * System.out.println ("name:" + _name);
     * System.out.println ("amount" + outstanding);
     * }
     * 
     * You can use this with as many local variables as you need.
     * 
     * The same is true if the local variable is an object and you invoke a
     * modifying method on the variable. Again you can just pass the object in
     * as a parameter. You only have to do something different if you actually
     * assign to the local variable.
     * 
     * Example: Reassigning a Local Variable
     * It’s the assignment to local variables that becomes complicated. In this
     * case we’re only talking about temps. If you see an assignment to a
     * parameter, you should immediately use Remove Assignments to Parameters
     * (131).
     * 
     * For temps that are assigned to, there are two cases. The simpler case is
     * that in which the variable is a temporary variable used only within the
     * extracted code. When that happens, you can move the temp into the
     * extracted code. The other case is use of the variable outside the code.
     * If the variable is not used after the code is extracted, you can make the
     * change in just the extracted code. If it is used afterward, you need to
     * make the extracted code return the changed value of the variable. I can
     * illustrate this with the following method:
     * 
     * void printOwing() {
     * 
     * Enumeration e = _orders.elements();
     * double outstanding = 0.0;
     * 
     * printBanner();
     * 
     * // calculate outstanding
     * while (e.hasMoreElements()) {
     * Order each = (Order) e.nextElement();
     * outstanding += each.getAmount();
     * }
     * 
     * printDetails(outstanding);
     * }
     * 
     * Now I extract the calculation:
     * 
     * void printOwing() {
     * printBanner();
     * double outstanding = getOutstanding();
     * printDetails(outstanding);
     * }
     * 
     * double getOutstanding() {
     * Enumeration e = _orders.elements();
     * double outstanding = 0.0;
     * while (e.hasMoreElements()) {
     * Order each = (Order) e.nextElement();
     * outstanding += each.getAmount();
     * }
     * return outstanding;
     * }
     * 
     * The enumeration variable is used only in the extracted code, so I can
     * move it entirely within the new method. The outstanding variable is used
     * in both places, so I need to rerun it from the extracted method. Once
     * I’ve compiled and tested for the extraction, I rename the returned value
     * to follow my usual convention:
     * 
     * double getOutstanding() {
     * Enumeration e = _orders.elements();
     * double result = 0.0;
     * while (e.hasMoreElements()) {
     * Order each = (Order) e.nextElement();
     * result += each.getAmount();
     * }
     * return result;
     * }
     * 
     * In this case the outstanding variable is initialized only to an obvious
     * initial value, so I can initialize it only within the extracted method.
     * If something more involved happens to the variable, I have to pass in the
     * previous value as a parameter. The initial code for this variation might
     * look like this:
     * 
     * void printOwing(double previousAmount) {
     * 
     * Enumeration e = _orders.elements();
     * double outstanding = previousAmount * 1.2;
     * 
     * printBanner();
     * 
     * // calculate outstanding
     * while (e.hasMoreElements()) {
     * Order each = (Order) e.nextElement();
     * outstanding += each.getAmount();
     * }
     * 
     * printDetails(outstanding);
     * }
     * 
     * In this case the extraction would look like this:
     * 
     * void printOwing(double previousAmount) {
     * double outstanding = previousAmount * 1.2;
     * printBanner();
     * outstanding = getOutstanding(outstanding);
     * printDetails(outstanding);
     * }
     * 
     * double getOutstanding(double initialValue) {
     * double result = initialValue;
     * Enumeration e = _orders.elements();
     * while (e.hasMoreElements()) {
     * Order each = (Order) e.nextElement();
     * result += each.getAmount();
     * }
     * return result;
     * }
     * 
     * After I compile and test this, I clear up the way the outstanding
     * variable is initialized:
     * 
     * void printOwing(double previousAmount) {
     * printBanner();
     * double outstanding = getOutstanding(previousAmount * 1.2);
     * printDetails(outstanding);
     * }
     * 
     * At this point you may be wondering, “What happens if more than one
     * variable needs to be returned?”
     * 
     * Here you have several options. The best option usually is to pick
     * different code to extract. I prefer a method to return one value, so I
     * would try to arrange for multiple methods for the different values. (If
     * your language allows output parameters, you can use those. I prefer to
     * use single return values as much as possible.)
     * 
     * Temporary variables often are so plentiful that they make extraction very
     * awkward. In these cases I try to reduce the temps by using Replace Temp
     * with Query (120). If whatever I do things are still awkward, I resort to
     * Replace Method with Method Object (135). This refactoring doesn’t care
     * how many temporaries you have or what you do with them.
     */
}
