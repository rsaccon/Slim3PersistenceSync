package slim3.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-08-15 00:36:50")
/** */
public final class TagMeta extends org.slim3.datastore.ModelMeta<slim3.model.Tag> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Tag, java.lang.Long> _lastChange = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Tag, java.lang.Long>(this, "_lastChange", "_lastChange", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Tag, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Tag, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<slim3.model.Tag> name = new org.slim3.datastore.StringAttributeMeta<slim3.model.Tag>(this, "name", "name");

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<slim3.model.Tag, org.slim3.datastore.ModelRef<slim3.model.Task>, slim3.model.Task> taskRef = new org.slim3.datastore.ModelRefAttributeMeta<slim3.model.Tag, org.slim3.datastore.ModelRef<slim3.model.Task>, slim3.model.Task>(this, "taskRef", "taskRef", org.slim3.datastore.ModelRef.class, slim3.model.Task.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Tag, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Tag, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final TagMeta slim3_singleton = new TagMeta();

    /**
     * @return the singleton
     */
    public static TagMeta get() {
       return slim3_singleton;
    }

    /** */
    public TagMeta() {
        super("Tag", slim3.model.Tag.class);
    }

    @Override
    public slim3.model.Tag entityToModel(com.google.appengine.api.datastore.Entity entity) {
        slim3.model.Tag model = new slim3.model.Tag();
        model.set_lastChange((java.lang.Long) entity.getProperty("_lastChange"));
        model.setKey(entity.getKey());
        model.setName((java.lang.String) entity.getProperty("name"));
        if (model.getTaskRef() == null) {
            throw new NullPointerException("The property(taskRef) is null.");
        }
        model.getTaskRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("taskRef"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        slim3.model.Tag m = (slim3.model.Tag) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("_lastChange", m.get_lastChange());
        entity.setProperty("name", m.getName());
        if (m.getTaskRef() == null) {
            throw new NullPointerException("The property(taskRef) must not be null.");
        }
        entity.setProperty("taskRef", org.slim3.datastore.ModelMeta.assignKeyIfNecessary(m.getTaskRef().getModelMeta(), m.getTaskRef().getModel()));
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        slim3.model.Tag m = (slim3.model.Tag) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        slim3.model.Tag m = (slim3.model.Tag) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        slim3.model.Tag m = (slim3.model.Tag) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        slim3.model.Tag m = (slim3.model.Tag) model;
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