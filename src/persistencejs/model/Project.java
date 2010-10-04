package persistencejs.model;

import java.io.Serializable;
import java.util.Date;

import org.persistencejs.Sync;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;

import persistencejs.meta.ProjectMeta;
import persistencejs.utils.CreatedDate;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    @Attribute(listener = CreatedDate.class)
    private Date createdDate;
    
    @Sync(timestamp = true)
    private Long lastChange;
    
    @Sync
    private String name;
    
    @Attribute(persistent = false)
    private boolean dirty = false;
    
    @Attribute(persistent = false)
    private boolean syncDirty = false;
    
    @Attribute(persistent = false)
    private InverseModelListRef<Task, Project> projectListRef;

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Project other = (Project) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public void setName(String name) {
        ProjectMeta.get().setName(this, name);   
    }

    public String getName() {
        return name;
    }

    public InverseModelListRef<Task, Project> getProjectListRef() {
        return projectListRef;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setSyncDirty(boolean syncDirty) {
        this.syncDirty = syncDirty;
    }

    public boolean isSyncDirty() {
        return syncDirty;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
