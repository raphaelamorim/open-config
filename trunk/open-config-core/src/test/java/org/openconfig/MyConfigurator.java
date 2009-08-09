package org.openconfig;

import org.openconfig.core.Person;

/**
 * @author Richard L. Burton III
 */
public interface MyConfigurator {

    String getName();
    String getAge();
    String getAge2();
    void setName(String name);
    Person getPerson();

}
