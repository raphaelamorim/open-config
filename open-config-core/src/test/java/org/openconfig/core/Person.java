package org.openconfig.core;

/**
 * @author Richard L. Burton III
 */
public class Person {

    public String getName(){
        return "name";
    }

    public Person getChild(){
        System.out.println("Person.getChild");
        return null;
    }
}
