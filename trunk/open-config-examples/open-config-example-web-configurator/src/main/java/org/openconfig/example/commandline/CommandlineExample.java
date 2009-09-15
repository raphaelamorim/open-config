package org.openconfig.example.commandline;

import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.SystemPropertyEnvironmentResolver;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class CommandlineExample {

    public static void main(String[] args) {
        System.setProperty(SystemPropertyEnvironmentResolver.DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE, "local");
        
        ConfiguratorFactory cf = new ConfigurationFactoryBuilder()
                .setOpenConfigContext(new BasicOpenConfigContext())
                .build();

        DatabaseConfigurator databaseConfigurator = cf.newInstance(DatabaseConfigurator.class);
        System.out.println("**********************************************************");
        System.out.println("databaseConfigurator.getDatabase().getUsername() = " + databaseConfigurator.getDatabase().getUsername());
        System.out.println("databaseConfigurator.getDatabase().getPassword() = " + databaseConfigurator.getDatabase().getPassword());
        System.out.println("databaseConfigurator.getDatabase().getUrl() = " + databaseConfigurator.getDatabase().getUrl());
        System.out.println("databaseConfigurator.getDatabase().toString() = " + databaseConfigurator.getDatabase().toString());
        System.out.println("databaseConfigurator.toString() = " + databaseConfigurator.toString());
        System.out.println("databaseConfigurator.getPoolSize() = " + databaseConfigurator.getPoolSize());
        System.out.println("**********************************************************");
    }

}
