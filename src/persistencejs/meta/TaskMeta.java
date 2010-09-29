package persistencejs.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-09-29 03:58:58")
/** */
public final class TaskMeta extends org.slim3.datastore.ModelMeta<persistencejs.model.Task> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, java.util.Date> createdDate = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, java.util.Date>(this, "createdDate", "createdDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, java.lang.Boolean> done = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, java.lang.Boolean>(this, "done", "done", boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, java.lang.Long> lastChange = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, java.lang.Long>(this, "lastChange", "lastChange", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<persistencejs.model.Task> name = new org.slim3.datastore.StringAttributeMeta<persistencejs.model.Task>(this, "name", "name");

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<persistencejs.model.Task, org.slim3.datastore.ModelRef<persistencejs.model.Project>, persistencejs.model.Project> projectRef = new org.slim3.datastore.ModelRefAttributeMeta<persistencejs.model.Task, org.slim3.datastore.ModelRef<persistencejs.model.Project>, persistencejs.model.Project>(this, "projectRef", "projectRef", org.slim3.datastore.ModelRef.class, persistencejs.model.Project.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<persistencejs.model.Task, org.slim3.datastore.ModelRef<persistencejs.model.Tag>, persistencejs.model.Tag> tagRef = new org.slim3.datastore.ModelRefAttributeMeta<persistencejs.model.Task, org.slim3.datastore.ModelRef<persistencejs.model.Tag>, persistencejs.model.Tag>(this, "tagRef", "tagRef", org.slim3.datastore.ModelRef.class, persistencejs.model.Tag.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<persistencejs.model.Task, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final persistencejs.utils.CreatedDate slim3_createdDateAttributeListener = new persistencejs.utils.CreatedDate();

    private static final TaskMeta slim3_singleton = new TaskMeta();

    /**
     * @return the singleton
     */
    public static TaskMeta get() {
       return slim3_singleton;
    }

    /** */
    public TaskMeta() {
        super("Task", persistencejs.model.Task.class);
    }

    @Override
    public persistencejs.model.Task entityToModel(com.google.appengine.api.datastore.Entity entity) {
        persistencejs.model.Task model = new persistencejs.model.Task();
        model.setCreatedDate((java.util.Date) entity.getProperty("createdDate"));
        model.setDone(booleanToPrimitiveBoolean((java.lang.Boolean) entity.getProperty("done")));
        model.setKey(entity.getKey());
        model.setLastChange((java.lang.Long) entity.getProperty("lastChange"));
        model.setName((java.lang.String) entity.getProperty("name"));
        if (model.getProjectRef() == null) {
            throw new NullPointerException("The property(projectRef) is null.");
        }
        model.getProjectRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("projectRef"));
        if (model.getTagRef() == null) {
            throw new NullPointerException("The property(tagRef) is null.");
        }
        model.getTagRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("tagRef"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        persistencejs.model.Task m = (persistencejs.model.Task) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("createdDate", m.getCreatedDate());
        entity.setProperty("done", m.isDone());
        entity.setProperty("lastChange", m.getLastChange());
        entity.setProperty("name", m.getName());
        if (m.getProjectRef() == null) {
            throw new NullPointerException("The property(projectRef) must not be null.");
        }
        entity.setProperty("projectRef", org.slim3.datastore.ModelMeta.assignKeyIfNecessary(m.getProjectRef().getModelMeta(), m.getProjectRef().getModel()));
        if (m.getTagRef() == null) {
            throw new NullPointerException("The property(tagRef) must not be null.");
        }
        entity.setProperty("tagRef", org.slim3.datastore.ModelMeta.assignKeyIfNecessary(m.getTagRef().getModelMeta(), m.getTagRef().getModel()));
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        persistencejs.model.Task m = (persistencejs.model.Task) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        persistencejs.model.Task m = (persistencejs.model.Task) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        persistencejs.model.Task m = (persistencejs.model.Task) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        persistencejs.model.Task m = (persistencejs.model.Task) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
        persistencejs.model.Task m = (persistencejs.model.Task) model;
        m.setCreatedDate(slim3_createdDateAttributeListener.prePut(m.getCreatedDate()));
        if (m.isDirty() && !m.isSyncDirty()) {
            m.setLastChange(new java.util.Date().getTime());
            m.setDirty(false);
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

    public persistencejs.model.Task JSONtoModel(com.google.appengine.repackaged.org.json.JSONObject json, long timestamp) {
        persistencejs.model.Task model;
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
            if (json.has("done")) {
                model.setDone(json.getBoolean("done"));
            }
            model.setLastChange(timestamp);
            if (json.has("name")) {
                model.setName(json.getString("name"));
            }
            String project = "project";
            if (json.has(project)) {
                com.google.appengine.api.datastore.Key ref;
                try {
                    ref = org.slim3.datastore.Datastore.createKey(ProjectMeta.get(), json.getLong(project));
                } catch (com.google.appengine.repackaged.org.json.JSONException e) {
                    ref = org.slim3.datastore.Datastore.createKey(ProjectMeta.get(), json.getString(project));
                }
                model.getProjectRef().setKey(ref);
            }
            String tag = "tag";
            if (json.has(tag)) {
                com.google.appengine.api.datastore.Key ref;
                try {
                    ref = org.slim3.datastore.Datastore.createKey(TagMeta.get(), json.getLong(tag));
                } catch (com.google.appengine.repackaged.org.json.JSONException e) {
                    ref = org.slim3.datastore.Datastore.createKey(TagMeta.get(), json.getString(tag));
                }
                model.getTagRef().setKey(ref);
            }
        } catch (com.google.appengine.repackaged.org.json.JSONException e) {
            return null;
        }
        return model;
    }

    public com.google.appengine.repackaged.org.json.JSONObject modelToJSON(persistencejs.model.Task model) {
        com.google.appengine.api.datastore.Key key = model.getKey();
        com.google.appengine.repackaged.org.json.JSONObject json = new com.google.appengine.repackaged.org.json.JSONObject();
        try {
            json.put("id", getNameOrId(key));
            json.put("done", model.isDone());
            json.put("_lastChange", model.getLastChange());
            json.put("name", model.getName());
            if (model.getProjectRef().getKey() != null) {
                json.put("project", getNameOrId(model.getProjectRef().getKey()));
            }
            if (model.getTagRef().getKey() != null) {
                json.put("tag", getNameOrId(model.getTagRef().getKey()));
            }
        } catch (com.google.appengine.repackaged.org.json.JSONException e) {
            return null;
        }
        return json;
    }

    public String getNameOrId(com.google.appengine.api.datastore.Key key) {
        return (key.getName() == null) ? Long.toString(key.getId()) : key.getName();
    }

}