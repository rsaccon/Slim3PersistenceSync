package slim3.service;

import java.util.Date;
import java.util.Iterator;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;

import slim3.meta.TaskMeta;
import slim3.model.Task;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONStringer;


public class TaskSyncService {
    
    public String pushUpdates(long since) throws JSONException {
        TaskMeta meta = TaskMeta.get();
        Iterator<Task> taskIterator =
            Datastore
                .query(meta)
                .filter(meta._lastChange.greaterThan(since))
                .asList()
                .iterator();

        JSONArray arr = new JSONArray();

        while (taskIterator.hasNext()) {
            Task task = taskIterator.next();
            Key key = task.getKey();
            Key projectKey = task.getProjectRef().getKey();
            Key tagKey = task.getTagRef().getKey();
            JSONObject obj = new JSONObject(task);

            obj.remove("projectRef");
            obj.remove("tagRef");
            obj.remove("class");
            obj.remove("key");
            obj.remove("version");
            
            obj.put(
                "id",
                (key.getName() == null) ? Long.toString(key.getId()) : key
                    .getName());
            if (projectKey != null) {
                obj.put(
                    "project",
                    (projectKey.getName() == null) ? Long.toString(projectKey.getId()) : projectKey
                        .getName());
            }
            
            if (tagKey != null) {
                obj.put(
                    "tag",
                    (tagKey.getName() == null) ? Long.toString(tagKey.getId()) : tagKey
                        .getName());
            }
            
            arr.put(obj);
        }
        
        System.out.println(">> push tasks updates since: "+ since + " | "+ arr);

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
        JSONArray arr = new JSONArray(updates);  
        
        System.out.println(">> receive tasks updates: "+now);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            System.out.println(obj);
            Key key;
            try {
                key = Datastore.createKey(Task.class, obj.getLong("id"));
            } catch (JSONException e) {
                key = Datastore.createKey(Task.class, obj.getString("id"));
            }
            Task task;
            try {
                task = Datastore.get(Task.class, key);
            } catch (EntityNotFoundRuntimeException e) {
                task = new Task();
                task.setKey(key);
            }
            task.copyFromJSON(obj);
            task.set_lastChange(now);
            Datastore.put(task);
        }

        return new JSONStringer()
            .object()
            .key("status")
            .value("ok")
            .key("now")
            .value(now)
            .endObject()
            .toString();
    }
}
