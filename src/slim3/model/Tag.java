package slim3.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

@Model(schemaVersion = 1)
public class Tag implements Serializable {

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
    private InverseModelListRef<Task, Tag> tagListRef;

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
            _dirty = true;
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
    
    public void fromJSON(JSONObject json) {
        try {
            if (json.has("name")) {
                name = json.getString("name");
            }
            
            if (json.has("task")) {
                Key taskKey;
                try {
                    taskKey = Datastore.createKey(Task.class, json.getLong("task"));
                } catch (JSONException e) {
                    taskKey = Datastore.createKey(Task.class, json.getString("task"));
                }
                taskRef.setKey(taskKey);
            }
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
