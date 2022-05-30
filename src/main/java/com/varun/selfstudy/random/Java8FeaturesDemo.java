package com.varun.selfstudy.random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Java8FeaturesDemo {

    /**
     * Big reason - to introduce conciseness in the code
     * With lambda expression, brought functional programming into the picture - for conciseness
     * <p>
     * <p>
     * 1. Lambda Expressions
     * 2. Functional Interfaces
     * 3. Default and Static Methods
     * 4. Predicates
     * 5. Functions, Supplier, Consumer
     * 6. Double colon operator (Constructor and Method references)
     * 7. Stream API
     * 8. Date and Time API (Joda Time API)
     */

    public static void main(String[] args) {
        Java8FeaturesDemo demo = new Java8FeaturesDemo();
        demo.lambdaDemo();

    }

    /**
     * 1. Lambda Expressions => anonymous functions (aka closures)
     * - no name
     * - no return type
     * - no modifiers
     * <p>
     * - if there is only one statement, skip braces {}
     * - if there is only one input arg, skip parentheses ()
     * - if there are more than one input args, parentheses is must
     * - similarly if there are more than one statements, braces is must
     * - ex. (o1, o2) -> {
     * sout("something");
     * o1.compareTo(o2);
     * }
     * <p>
     * NOTE: to use lambda expression, functional interface is a must.
     */

    private void lambdaDemo() {
        List<Integer> arr = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        arr.stream().forEach(i -> System.out.println(i));
    }

    /**
     * 2. Functional Interface
     * <p>
     * - Annotation: @FunctionalInterface
     * - There MUST be only one single abstract method (SAM) inside a functional interface.
     * - If there are more than one abstract method in a functional interface, compiler will throw error.
     * - If there are no SAM inside a functional interface, compiler will throw error.
     * - There can be default methods and static methods.
     * -
     */
    private void functionalIntefaceDemo() {

    }

    /**
     * 3 a. Default Methods
     *
     * Compile time error if any method added to interface and not to impl classes
     * i.e. backword compatibility
     *
     * default methods are dummy implementations
     * override them in impl classes if you need to do more
     *
     * default - not an access modifier (public/private/protected)
     * - all methods inside interface are public till java 1.8 (1.9 onwards methods in interfaces can be pvt as well)
     * - cannot give default impl of hashCode and equals inside interface
     *
     * how do default methods handle diamond problem?
     * - if 2 interfaces being implemented by a class have same default methods, compile time error
     * - to fix this, implement the default method in impl class and call chosen interface's method using super - IntefaceName.super.MethodName()
     *
     * public class MyClass implements MyInterface1, MyInteface2 {
     *   @Override
     *   public void defaultImpl() {
     *       MyInterface1.super.defaultImpl();
     *   }
     * }
     *
     * */

    /**
     * 3 b. Static methods
     *
     * No need to create class with static method, you can simply create static method inside an interface
     * and use it like InterfaceName.staticClassName();
     *
     * good for utils
     * interfaces cannot contain constructors and static blocks, it's safe to have static methods in it.
     * introduced in java 8, before 1.8, you had to create your static classes
     *
     * not available to impl classes (must call InterfaceName.staticMethod() directly instead of InterfaceImpl.staticMethod())
     * not default methods, but static
     *
     * */

    /**
     *
     * Interfaces with default and static methods vs. Abstract classes:
     *
     * - The line of difference between the two seems to have blurred on the looks of it.
     * - but that's not the case.
     *
     * - Interfaces cannot have constructors, static blocks, instance variables.
     * - Abstract classes can.
     *
     *
     *
     * */

    /**
     * 4. Predicates
     *
     * - predefined functional interface
     * - SAM - public boolean test()
     * - used for checking conditions, returns boolean
     *
     * pros
     * - code re-usability
     *
     * Predicate Joining
     * - and()
     * - or()
     * - negate()
     *
     * Predicate<String> one = new SomePredicate1();
     * Predicate<String> two = new SomePredicate2();
     * Predicate<String> three = new SomePredicate3();
     *
     * one.and(two).and(three).test()
     * one.or(two).test()
     * one.negate().test() - only one predicate can be negated
     *
     * */

    /**
     * 5 a. Functions
     *
     * - predefined functional interface
     * - SAM - public R apply(T t)
     *
     * - similar to predicate, but takes 1 input (T t) and generates 1 output (R)
     * - diff with predicate (P)
     *      - P returns boolean, Func returns Object
     *      - P sam test(), Func sam apply()
     *
     * - Example:
     *  Function<Integer, Integer> squareMe = i -> i * i;
     *  sout( squareMe.apply(5) );
     *
     *  *** Functional Chaining ***
     *  - andThen()
     *  - compose()
     *
     *  firstFunction.andThen(secondFunc).apply();
     *
     *  f1.andThen(f2).apply(input) - first f1 and then f2
     *  f1.compose(f2).apply(input) - first f2 and then f1
     *
     * */

    /**
     * 5 b. Consumer
     *
     * - predefined functional interface
     * - sam - void accept(T t)
     *
     * - doesnt return anything, just consumes using accept()
     * - takes one type T
     *
     * Consumer chaining
     * - only andThen(), no compose()
     *
     * c1.andThen(c2).andThen(c3).apply(someInput);
     *
     * */

    /**
     * 5 c. Supplier
     * - predefined functional interface
     * - sam - T get()
     *
     * no chaining in suppliers as no input is given to a supplier
     *
     * pros
     * - code re-usability
     * */

    /**
     * Above functional interfaces all take just one input. But if we need to pass 2 inputs, use -
     * BiFunction, BiConsumer, BiPredicate - all take 2 inputs
     * NO BiSupplier - because supplier doesnt take any inputs
     *
     * No Tri or Quad XYZ, as inbuilt functional interfaces take only 1 or 2 inputs, no more.
     * */
}
