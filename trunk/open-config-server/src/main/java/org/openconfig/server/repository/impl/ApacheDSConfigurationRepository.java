package org.openconfig.server.repository.impl;

import org.apache.directory.server.core.DirectoryService;
import org.apache.directory.server.core.DefaultDirectoryService;
import org.apache.directory.server.core.entry.ServerEntry;
import org.apache.directory.server.core.partition.Partition;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmIndex;
import org.apache.directory.server.xdbm.Index;
import org.apache.directory.shared.ldap.exception.LdapNameNotFoundException;
import org.apache.directory.shared.ldap.name.LdapDN;
import org.apache.directory.shared.ldap.entry.Entry;
import org.openconfig.server.repository.ConfigurationRepository;

import java.util.Set;
import java.util.HashSet;

/**
 * @author Richard L. Burton III
 */
public class ApacheDSConfigurationRepository implements ConfigurationRepository {

    /**
     * The directory service
     */
    private DirectoryService service;

    private Partition addPartition(String partitionId, String partitionDn) throws Exception {
        Partition partition = new JdbmPartition();
        partition.setId(partitionId);
        partition.setSuffix(partitionDn);
        service.addPartition(partition);
        return partition;
    }


    private void addIndex(Partition partition, String... attrs) {
        Set indexedAttributes = new HashSet<Index<?, ServerEntry>>();
        for (String attribute : attrs) {
            indexedAttributes.add(new JdbmIndex<String, ServerEntry>(attribute));
        }

        ((JdbmPartition) partition).setIndexedAttributes(indexedAttributes);
    }


    /**
     * Initialize the server. It creates the partition, add the index, and
     * inject the context entries for the created partitions.
     *
     * @throws Exception if there were some problems why initializing the system
     */
    private void init() throws Exception {
        // Initialize the LDAP service
        service = new DefaultDirectoryService();

        // Disable the ChangeLog system
        service.getChangeLog().setEnabled(false);
        service.setDenormalizeOpAttrsEnabled(true);

        // Create some new partitions named 'foo', 'bar' and 'apache'.
        Partition fooPartition = addPartition("foo", "dc=foo,dc=com");
        Partition barPartition = addPartition("bar", "dc=bar,dc=com");
        Partition apachePartition = addPartition("apache", "dc=apache,dc=org");

        addIndex(apachePartition, "objectClass", "ou", "uid");

        // And start the service
        service.startup();


        // Inject the foo root entry if it does not already exist
        try {
            service.getAdminSession().lookup(fooPartition.getSuffixDn());
        }
        catch (LdapNameNotFoundException lnnfe) {
            LdapDN dnFoo = new LdapDN("dc=foo,dc=com");
            ServerEntry entryFoo = service.newEntry(dnFoo);
            entryFoo.add("objectClass", "top", "domain", "extensibleObject");
            entryFoo.add("dc", "foo");
            service.getAdminSession().add(entryFoo);
        }

        // Inject the bar root entry
        try {
            service.getAdminSession().lookup(barPartition.getSuffixDn());
        }
        catch (LdapNameNotFoundException lnnfe) {
            LdapDN dnBar = new LdapDN("dc=bar,dc=com");
            ServerEntry entryBar = service.newEntry(dnBar);
            entryBar.add("objectClass", "top", "domain", "extensibleObject");
            entryBar.add("dc", "bar");
            service.getAdminSession().add(entryBar);
        }

        // Inject the apache root entry
        try {
            service.getAdminSession().lookup(apachePartition.getSuffixDn());
        }
        catch (LdapNameNotFoundException lnnfe) {
            LdapDN dnApache = new LdapDN("dc=Apache,dc=Org");
            ServerEntry entryApache = service.newEntry(dnApache);
            entryApache.add("objectClass", "top", "domain", "extensibleObject");
            entryApache.add("dc", "Apache");
            service.getAdminSession().add(entryApache);
        }

        // We are all done !
    }


    /**
     * Creates a new instance of EmbeddedADS. It initializes the directory service.
     *
     * @throws Exception If something went wrong
     */
    public ApacheDSConfigurationRepository() throws Exception {
        init();
    }

    /**
     * Main class. We just do a lookup on the server to check that it's available.
     *
     * @param args Not used.
     */
    public static void main(String[] args) //throws Exception
    {
        try {
            // Create the server
            ApacheDSConfigurationRepository ads = new ApacheDSConfigurationRepository();

            // Read an entry
            Entry result = ads.service.getAdminSession().lookup(new LdapDN("dc=apache,dc=org"));

            // And print it if available
            System.out.println("Found entry : " + result);
        }
        catch (Exception e) {
            // Ok, we have something wrong going on ...
            e.printStackTrace();
        }
    }

}
