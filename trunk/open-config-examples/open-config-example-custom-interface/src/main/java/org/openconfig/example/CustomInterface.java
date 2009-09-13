package org.openconfig.example;

import org.openconfig.example.domain.Administrator;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface CustomInterface {

    Administrator getAdministrator();

    String getDataSourceName();

}
