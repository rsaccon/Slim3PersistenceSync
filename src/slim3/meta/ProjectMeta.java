package slim3.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2010-08-22 00:47:20")
/** */
public final class ProjectMeta extends org.slim3.datastore.ModelMeta<slim3.model.Project> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Project, java.lang.Long> _lastChange = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Project, java.lang.Long>(this, "_lastChange", "_lastChange", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Project, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Project, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<slim3.model.Project> name = new org.slim3.datastore.StringAttributeMeta<slim3.model.Project>(this, "name", "name");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<slim3.model.Project, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<slim3.model.Project, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final ProjectMeta slim3_singleton = new ProjectMeta();

    /**
     * @return the singleton
     */
    public static ProjectMeta get() {
       return slim3_singleton;
    }

    /** */
    public ProjectMeta() {
        super("Project", slim3.model.Project.class);
    }

    @Override
    public slim3.model.Project entityToModel(com.google.appengine.api.datastore.Entity entity) {
        slim3.model.Project model = new slim3.model.Project();
        model.set_lastChange((java.lang.Long) entity.getProperty("_lastChange"));
        model.setKey(entity.getKey());
        model.setName((java.lang.String) entity.getProperty("name"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        slim3.model.Project m = (slim3.model.Project) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("_lastChange", m.get_lastChange());
        entity.setProperty("name", m.getName());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        slim3.model.Project m = (slim3.model.Project) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        slim3.model.Project m = (slim3.model.Project) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        slim3.model.Project m = (slim3.model.Project) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void incrementVersion(Object model) {
        slim3.model.Project m = (slim3.model.Project) model;
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