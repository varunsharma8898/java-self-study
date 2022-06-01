package com.varun.selfstudy.random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsDemo {

    public static void main(String[] args) {
        StreamsDemo demo = new StreamsDemo();

        // filter
        List<Integer> test = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));
        System.out.println("-----------------------------------------------------------");
        System.out.println("Actual List = " + test);
        System.out.println(demo.filterEvenNumbersFromList(test));


        // map
        System.out.println("-----------------------------------------------------------");
        System.out.println("Add 10 to every element: ");
        System.out.println(test.stream().map(i -> i+10).collect(Collectors.toList()));


        // NOTE:
        //  min and max are based on the order of elements
        //  so a min is always the leftmost element
        //  and max is always the rightmost element

        // min
        System.out.println("-----------------------------------------------------------");
        System.out.println("Min element in ascending order");
        System.out.println(test.stream().min((i1, i2) -> i1.compareTo(i2)).get());

        System.out.println("Min element in descending order");
        System.out.println(test.stream().min((i1, i2) -> i2.compareTo(i1)).get());

        // max
        System.out.println("-----------------------------------------------------------");
        System.out.println("Max element in ascending order");
        System.out.println(test.stream().max((i1, i2) -> i1.compareTo(i2)).get());

        System.out.println("Max element in descending order");
        System.out.println(test.stream().max((i1, i2) -> i2.compareTo(i1)).get());


        // forEach example
        System.out.println("-----------------------------------------------------------");
        System.out.println("Print each element in the list");
        // 1: passing the function directly
        test.stream().forEach(System.out::println);
        System.out.println();
        // 2. passing the consumer obj (node that consumer is a @FunctionalInterface and has only accept() method
        Consumer<Integer> c = i -> {
            System.out.print(i + " ");
        };
        test.stream().forEach(c);
        System.out.println();
        // 3. directly writing consumer code inside forEach
        test.stream().forEach(i -> {
            System.out.print(i + " ");
        });
        System.out.println();

        System.out.println("-----------------------------------------------------------");
        // Consumers can be chained as well:
        Consumer<Integer> c1 = i -> {
            System.out.println("Square of i = " + i);
        };
        Consumer<Integer> c2 = i -> {
            System.out.println(i * i);
        };
        test.stream().forEach(c1.andThen(c2));


        System.out.println("-----------------------------------------------------------");
        System.out.println("Convert stream of objects to array and use Stream.of()");
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Integer[] arr = a.stream().toArray(Integer[]::new);
        // array can't stream(). so use Stream.of(array) instead
        Stream.of(arr).forEach(i -> System.out.print(i + " "));
        System.out.println();


        // more filter examples
        System.out.println("-----------------------------------------------------------");
        System.out.println("Students with name starting with a: ");
        System.out.println(demo.filterStudentsByName(demo.getTestData()));

        System.out.println("-----------------------------------------------------------");
        System.out.println("Students with any phone number starting with 0:");
        System.out.println(demo.filterStudentsByPhoneNumber(demo.getTestData()));

        System.out.println("-----------------------------------------------------------");
        System.out.println("Failed students - with marks < 40");
        System.out.println(demo.getTestData().stream().filter(i -> i.subjectList.stream().anyMatch(s -> s.marks < 40)).collect(Collectors.toList()));


        // count
        // no need to collect, just use count()
        System.out.println("-----------------------------------------------------------");
        System.out.println("Number of failed students - with marks < 40");
        System.out.println(demo.getTestData().stream().filter(i -> i.subjectList.stream().anyMatch(s -> s.marks < 40)).count());

        // collect and use size() - not efficient
        System.out.println("-----------------------------------------------------------");
        System.out.println("Number of failed students - with marks < 40 - alternate");
        System.out.println(demo.getTestData().stream().filter(i -> i.subjectList.stream().anyMatch(s -> s.marks < 40)).collect(Collectors.toList()).size());



        // sort
        System.out.println("-----------------------------------------------------------");
        System.out.println("Students - sorted by name (ascending order):");
        System.out.println(demo.getTestData().stream().sorted((o1, o2) -> o1.name.compareTo(o2.name)).collect(Collectors.toList()));
        System.out.println(demo.getTestData().stream().sorted(Comparator.comparing(o -> o.name)).collect(Collectors.toList()));

        System.out.println("-----------------------------------------------------------");
        System.out.println("Students - sorted by name (descending order):");
        System.out.println(demo.getTestData().stream().sorted((o1, o2) -> o2.name.compareTo(o1.name)).collect(Collectors.toList()));


        System.out.println("-----------------------------------------------------------");
    }


    private List<Integer> filterEvenNumbersFromList(List<Integer> numberList) {
        return numberList.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
    }

    // filter students that have name starting with a
    private List<Student> filterStudentsByName(List<Student> students) {
        return students.stream().filter(i -> i.name.startsWith("a")).collect(Collectors.toList());
    }

    // filter students that have any phone number starting with 0
    private List<Student> filterStudentsByPhoneNumber(List<Student> students) {
        return students.stream().filter(i -> i.numberList.stream().anyMatch(n -> n.number.startsWith("0"))).collect(Collectors.toList());
    }

    class Student {
        String name;
        List<PhoneNumber> numberList;
        List<Subject> subjectList;
        Student(String name) {
            this.name = name;
        }
        void setNumberList(List<PhoneNumber> numberList) {
            this.numberList = numberList;
        }
        void setSubjectList(List<Subject> subjectList) {
            this.subjectList = subjectList;
        }
        public String toString() {
            return "\n  Student: " + name + " - " + numberList + " - " + subjectList;
        }
    }

    class Subject {
        String name;
        int marks;
        Subject(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }
        public String toString() {
            return "Subject: " + name + " - " + marks;
        }
    }

    class PhoneNumber {
        String tag;
        String number;
        PhoneNumber(String tag, String number) {
            this.tag = tag;
            this.number = number;
        }
        public String toString() {
            return "PhoneNumber: " + tag + " - " + number;
        }
    }

    List<Student> getTestData() {
        List<Student> res = new ArrayList<>();

        Student s1 = new Student("aaaa");
        s1.setNumberList(Arrays.asList(new PhoneNumber("Home", "012345"), new PhoneNumber("Mobile", "987650")));
        s1.setSubjectList(Arrays.asList(new Subject("Maths", 35), new Subject("Eng", 50), new Subject("History", 60)));

        Student s2 = new Student("abbb");
        s2.setNumberList(Arrays.asList(new PhoneNumber("Home", "012345"), new PhoneNumber("Mobile", "123450")));
        s2.setSubjectList(Arrays.asList(new Subject("Maths", 30), new Subject("Eng", 50), new Subject("History", 60)));

        Student s3 = new Student("bccc");
        s3.setNumberList(Arrays.asList(new PhoneNumber("Home", "987651"), new PhoneNumber("Mobile", "012345")));
        s3.setSubjectList(Arrays.asList(new Subject("Maths", 45), new Subject("Eng", 50), new Subject("History", 60)));

        Student s4 = new Student("bddd");
        s4.setNumberList(Arrays.asList(new PhoneNumber("Home", "987652"), new PhoneNumber("Mobile", "987652")));
        s4.setSubjectList(Arrays.asList(new Subject("Maths", 80), new Subject("Eng", 80), new Subject("History", 80)));

        res.add(s1);
        res.add(s2);
        res.add(s3);
        res.add(s4);
        return res;
    }
}
