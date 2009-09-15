package org.openconfig.example.commandline;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public abstract class Database {

    abstract String getPassword();

    abstract String getUsername();

    abstract String getUrl();

    @Override
    public String toString() {
        return "Database{" +
                "password='" + getPassword() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", url='" + getUrl() + '\'' +
                '}';
    }
    
}
