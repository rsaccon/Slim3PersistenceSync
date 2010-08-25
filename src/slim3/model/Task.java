package slim3.model;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;


@Model(schemaVersion = 1)
public class Task implements Serializable {

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
    
    private boolean done;

    private ModelRef<Tag> tagRef = new ModelRef<Tag>(Tag.class);

    private ModelRef<Project> projectRef = new ModelRef<Project>(Project.class);

    
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
        Task other = (Task) obj;
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

    public void setDone(boolean done) {
        if (this.done != done) {
            _dirty = true;
        }
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public ModelRef<Project> getProjectRef() {
        return projectRef;
    }

    public ModelRef<Tag> getTagRef() {
        return tagRef;
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
            if (json.has("done")) {
                done = json.getBoolean("done");
            }
            if (json.has("project")) {
                Key projectKey = null;
                try {
                    projectKey = Datastore.createKey(Project.class, json.getLong("project"));
                } catch (JSONException e) {
                    projectKey = Datastore.createKey(Project.class, json.getString("project"));
                }
                projectRef.setKey(projectKey);
            }
            if (json.has("tag")) {
                Key tagKey = null;
                try {
                    tagKey = Datastore.createKey(Tag.class, json.getLong("tag"));
                } catch (JSONException e) {
                    tagKey = Datastore.createKey(Tag.class, json.getString("tag"));
                }
                tagRef.setKey(tagKey);
            }
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
