package org.openconfig.providers.ast;

/**
 * @author Richard L. Burton III
 */
public class Person {

    private String name;

    private int age;

    private Person child;

    public Person() {
        this.name = "Richard Burton";
        this.age = 30;
        this.child = new Person("Dushyanth Inguva", 31);
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getChild() {
        return child;
    }

    public void setChild(Person child) {
        this.child = child;
    }
}
