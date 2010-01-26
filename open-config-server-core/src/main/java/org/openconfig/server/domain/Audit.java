package org.openconfig.server.domain;

import java.util.Date;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class Audit {

    private Date created;

    private AuditType type;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public AuditType getType() {
        return type;
    }

    public void setType(AuditType type) {
        this.type = type;
    }
}
