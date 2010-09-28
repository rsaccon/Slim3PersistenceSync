package persistencejs.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-09-28 14:45:58")
/** */
public final class ProjectMeta extends org.slim3.datastore.ModelMeta<persistencejs.model.Project> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Project, java.util.Date> createdDate = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Project, java.util.Date>(this, "createdDate", "createdDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Project, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Project, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Project, java.lang.Long> lastChange = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Project, java.lang.Long>(this, "lastChange", "lastChange", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<persistencejs.model.Project> name = new org.slim3.datastore.StringAttributeMeta<persistencejs.model.Project>(this, "name", "name");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Project, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Project, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final persistencejs.utils.CreatedDate slim3_createdDateAttributeListener = new persistencejs.utils.CreatedDate();

    private static final ProjectMeta slim3_singleton = new ProjectMeta();

    /**
     * @return the singleton
     */
    public static ProjectMeta get() {
       return slim3_singleton;
    }

    /** */
    public ProjectMeta() {
        super("Project", persistencejs.model.Project.class);
    }

    @Override
    public persistencejs.model.Project entityToModel(com.google.appengine.api.datastore.Entity entity) {
        persistencejs.model.Project model = new persistencejs.model.Project();
        model.setCreatedDate((java.util.Date) entity.getProperty("createdDate"));
        model.setKey(entity.getKey());
        model.setLastChange((java.lang.Long) entity.getProperty("lastChange"));
        model.setName((java.lang.String) entity.getProperty("name"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        persistencejs.model.Project m = (persistencejs.model.Project) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("createdDate", m.getCreatedDate());
        entity.setProperty("lastChange", m.getLastChange());
        entity.setProperty("name", m.getName());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        persistencejs.model.Project m = (persistencejs.model.Project) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        persistencejs.model.Project m = (persistencejs.model.Project) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        persistencejs.model.Project m = (persistencejs.model.Project) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        persistencejs.model.Project m = (persistencejs.model.Project) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
        persistencejs.model.Project m = (persistencejs.model.Project) model;
        m.setCreatedDate(slim3_createdDateAttributeListener.prePut(m.getCreatedDate()));
        if (m.isSyncDirty()) {
            m.setLastChange(new java.util.Date().getTime());
            m.setSyncDirty(false);
        }
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    public persistencejs.model.Project JSONtoModel(com.google.appengine.repackaged.org.json.JSONObject json, long timestamp) {
        persistencejs.model.Project model;
        try {
            com.google.appengine.api.datastore.Entity entity;
            com.google.appengine.api.datastore.Key key;
            try {
                try {
                    key = org.slim3.datastore.Datastore.createKey(this, json.getLong("id"));
                } catch (com.google.appengine.repackaged.org.json.JSONException e1) {
                    key = org.slim3.datastore.Datastore.createKey(this, json.getString("id"));
                }
                entity = org.slim3.datastore.Datastore.get(key);
            } catch (org.slim3.datastore.EntityNotFoundRuntimeException e) {
                try {
                    key = com.google.appengine.api.datastore.KeyFactory.createKey(kind, json.getLong("id"));
                } catch (com.google.appengine.repackaged.org.json.JSONException e2) {
                    key = com.google.appengine.api.datastore.KeyFactory.createKey(kind, json.getString("id"));
                }
                entity = new com.google.appengine.api.datastore.Entity(key);
            }
            model = entityToModel(entity);
            model.setLastChange(timestamp);
            if (json.has("name")) {
                model.setName(json.getString("name"));
            }
        } catch (com.google.appengine.repackaged.org.json.JSONException e) {
            return null;
        }
        return model;
    }

    public com.google.appengine.repackaged.org.json.JSONObject modelToJSON(persistencejs.model.Project model) {
        com.google.appengine.api.datastore.Key key = model.getKey();
        com.google.appengine.repackaged.org.json.JSONObject json = new com.google.appengine.repackaged.org.json.JSONObject();
        try {
            json.put("id", getNameOrId(key));
            json.put("_lastChange", model.getLastChange());
            json.put("name", model.getName());
        } catch (com.google.appengine.repackaged.org.json.JSONException e) {
            return null;
        }
        return json;
    }

    public String getNameOrId(com.google.appengine.api.datastore.Key key) {
        return (key.getName() == null) ? Long.toString(key.getId()) : key.getName();
    }

}