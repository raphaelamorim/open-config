package org.openconfig;

import org.openconfig.core.Person;

/**
 * @author Richard L. Burton III
 */
public interface MyConfigurator {

    String getName();
    int getAge();
    int getAge2();
    void setName(String name);
    Person getPerson();

}
