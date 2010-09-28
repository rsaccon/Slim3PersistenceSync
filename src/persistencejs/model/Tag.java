package persistencejs.model;

import java.io.Serializable;
import java.util.Date;

import org.persistencejs.Sync;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import persistencejs.utils.CreatedDate;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    @Attribute(persistent = false)
    private boolean syncDirty = false;
    
    @Attribute(listener = CreatedDate.class)
    private Date createdDate;
    
    @Sync(timestamp = true)
    private Long lastChange;
    
    @Sync
    private String name;
    
    @Attribute(persistent = false)
    private InverseModelListRef<Task, Tag> tagListRef;

    @Sync
    private ModelRef<Task> taskRef = new ModelRef<Task>(Task.class);
   
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
        Tag other = (Tag) obj;
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
        if (((this.name == null) && (name != null)) || ((this.name != null) && !this.name.equals(name))) {
            syncDirty = true;
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public InverseModelListRef<Task, Tag> getTagListRef() {
        return tagListRef;
    }

    public ModelRef<Task> getTaskRef() {
        return taskRef;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public boolean isSyncDirty() {
        return syncDirty;
    }

    public void setSyncDirty(boolean syncDirty) {
        this.syncDirty = syncDirty;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}