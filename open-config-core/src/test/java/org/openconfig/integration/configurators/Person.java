package org.openconfig.integration.configurators;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class Person {

    private String name;

    private int age;

    private DayOfWeek dayOfWeek;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}
