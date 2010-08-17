package slim3.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-08-16 22:46:05")
/** */
public final class TaskMeta extends org.slim3.datastore.ModelMeta<slim3.model.Task> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Task, java.lang.Long> _lastChange = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Task, java.lang.Long>(this, "_lastChange", "_lastChange", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Task, java.lang.Boolean> done = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Task, java.lang.Boolean>(this, "done", "done", boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Task, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Task, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<slim3.model.Task> name = new org.slim3.datastore.StringAttributeMeta<slim3.model.Task>(this, "name", "name");

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<slim3.model.Task, org.slim3.datastore.ModelRef<slim3.model.Project>, slim3.model.Project> projectRef = new org.slim3.datastore.ModelRefAttributeMeta<slim3.model.Task, org.slim3.datastore.ModelRef<slim3.model.Project>, slim3.model.Project>(this, "projectRef", "projectRef", org.slim3.datastore.ModelRef.class, slim3.model.Project.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<slim3.model.Task, org.slim3.datastore.ModelRef<slim3.model.Tag>, slim3.model.Tag> tagRef = new org.slim3.datastore.ModelRefAttributeMeta<slim3.model.Task, org.slim3.datastore.ModelRef<slim3.model.Tag>, slim3.model.Tag>(this, "tagRef", "tagRef", org.slim3.datastore.ModelRef.class, slim3.model.Tag.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Task, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Task, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final TaskMeta slim3_singleton = new TaskMeta();

    /**
     * @return the singleton
     */
    public static TaskMeta get() {
       return slim3_singleton;
    }

    /** */
    public TaskMeta() {
        super("Task", slim3.model.Task.class);
    }

    @Override
    public slim3.model.Task entityToModel(com.google.appengine.api.datastore.Entity entity) {
        slim3.model.Task model = new slim3.model.Task();
        model.set_lastChange((java.lang.Long) entity.getProperty("_lastChange"));
        model.setDone(booleanToPrimitiveBoolean((java.lang.Boolean) entity.getProperty("done")));
        model.setKey(entity.getKey());
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
        slim3.model.Task m = (slim3.model.Task) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("_lastChange", m.get_lastChange());
        entity.setProperty("done", m.isDone());
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
        slim3.model.Task m = (slim3.model.Task) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        slim3.model.Task m = (slim3.model.Task) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        slim3.model.Task m = (slim3.model.Task) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        slim3.model.Task m = (slim3.model.Task) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        assignKeyIfNecessary(model);
        incrementVersion(model);
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

}