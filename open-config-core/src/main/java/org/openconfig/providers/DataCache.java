package org.openconfig.providers;

import org.openconfig.providers.ast.Node;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Richard L. Burton III
 */
public class DataCache {

    private long lastModified;

    private File source;

    private Set<Node> cache = new HashSet<Node>();

    public DataCache(String file){

    }
    
    public void setProperties(File source) {
        this.source = source;
        this.lastModified = source.lastModified();
    }

    public boolean isModified() {
        return lastModified != source.lastModified();
    }

    public File getSource() {
        return source;
    }

    public Set<Node> getCache() {
        return cache;
    }

    public void load() {
    }
}
