package org.openconfig.integration.configurators;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface PrimitiveConfiguration {

    String getName();

    int getAge();

    double getMoney();

    Person getAdministrator();
}
