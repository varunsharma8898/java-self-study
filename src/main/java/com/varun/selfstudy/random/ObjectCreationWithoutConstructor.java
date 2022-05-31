package com.varun.selfstudy.random;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectCreationWithoutConstructor {

    public static void main(String[] args) throws Exception {
        ObjectCreationWithoutConstructor obj = new ObjectCreationWithoutConstructor();
        obj.testObjectCreationDifferentWays();

    }

    private void testObjectCreationDifferentWays() throws Exception {
        // 1. Using constructor
        MyObject obj1 = new MyObject();
        obj1.display();

        // 2. Using class.newInstance() method (aka Reflection Utils - that's how spring DI works)
        Class objClass = Class.forName("com.varun.selfstudy.random.MyObject");
        MyObject obj2 = (MyObject) objClass.newInstance();
        obj2.display();

        // 3. Using clone() method - Class must implement Cloneable
        MyObject obj3 = (MyObject) obj2.clone();
        obj3.display();

        // 4. Using serialize-deserialize
        FileOutputStream fileOutputStream = new FileOutputStream("delete-me.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(obj3);
        objectOutputStream.flush();

        FileInputStream fileInputStream = new FileInputStream("delete-me.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        MyObject obj4 = (MyObject) objectInputStream.readObject();
        obj4.display();

    }

}

/**
 * Cannot create instance of an inner class through class.newInstance()  as it results in InstantiationException
 * Hence, it is outside the test class.
 * Also zero-arg constructor cant be private, as it will result in InstantiationException.
 * */
class MyObject implements Cloneable, Serializable {

    MyObject() {
    }

    public void display() throws Exception {
        System.out.println("MyObject instance - " + this.toString());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}