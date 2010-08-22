package slim3.service;

import java.util.Date;
import java.util.Iterator;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;

import slim3.meta.ProjectMeta;
import slim3.model.Project;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONStringer;

public class ProjectSyncService {
    
    public String pushUpdates(long since) throws JSONException {
        ProjectMeta meta = ProjectMeta.get();
        Iterator<Project> projectIterator =
            Datastore
                .query(meta)
                .filter(meta._lastChange.greaterThan(since))
                .asList()
                .iterator();

        JSONArray arr = new JSONArray();

        while (projectIterator.hasNext()) {
            Project project = projectIterator.next();
            Key key = project.getKey();
            JSONObject obj = new JSONObject(project);

            obj.remove("class");
            obj.remove("key");
            obj.remove("version");
            obj.put(
                "id",
                (key.getName() == null) ? Long.toString(key.getId()) : key
                    .getName());

            arr.put(obj);
        }

        return new JSONStringer()
            .object()
            .key("now")
            .value(new Date().getTime())
            .key("updates")
            .value(arr)
            .endObject()
            .toString();
    }
    
    public String receiveUpdates(String updates) throws JSONException {
        long now = new Date().getTime();
        boolean ok = true;
        
        JSONArray arr = new JSONArray(updates);
        
        for ( int i=0; i<arr.length(); i++ ) {
            JSONObject obj = arr.getJSONObject(i);
            Key key = Datastore.createKey(Project.class, obj.get("id").toString());
            Project project;
            try {
                project = Datastore.get(Project.class, key);
            } catch (EntityNotFoundRuntimeException e) {
                project = new Project();
                project.setKey(key);
            }
            obj.remove("id");
            project.copyFromJSON(obj);
            project.set_lastChange(now);
            Datastore.put(project);  
        }
        
        return new JSONStringer()
            .object()
            .key("status")
            .value((ok) ? "ok" : "error")
            .key("now")
            .value(now)
            .endObject()
            .toString();
    }

}
