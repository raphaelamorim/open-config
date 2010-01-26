package org.openconfig.factory;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface PrimitiveConfiguration {

    String getName();

    int getAge();

    double getMoney();

    Person getAdministrator();
}
