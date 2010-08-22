package slim3.model;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;

@Model(schemaVersion = 1)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
  
    // TODO: create this automatically in meta data only
    @Attribute(persistent = false)
    private boolean _dirty = false;
    
    // TODO: create this automatically in meta data only
    private Long _lastChange;
    
    private String name;
    
    @Attribute(persistent = false)
    private InverseModelListRef<Task, Project> projectListRef;

    /**
     * Returns the key.
     *
     * @return the key
     */
    @JsonIgnore
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    @JsonIgnore
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    @JsonIgnore
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    @JsonIgnore
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
        if (((this.name == null) && (name != null)) || ((this.name != null) && !this.name.equals(name))) {
            _dirty = true;
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public InverseModelListRef<Task, Project> getProjectListRef() {
        return projectListRef;
    }

    public void set_lastChange(Long _lastChange) {
        this._lastChange = _lastChange;
    }

    public Long get_lastChange() {
        return _lastChange;
    }
    
    public Key put() throws NullPointerException {
        if (_dirty) {
            _lastChange = new Date().getTime();
            _dirty = false;
        }
        return Datastore.put(this);
    }
    
    @JsonProperty
    String id() {
        return (key.getName() == null) ? Long.toString(key.getId()) : key.getName();
    }
}
