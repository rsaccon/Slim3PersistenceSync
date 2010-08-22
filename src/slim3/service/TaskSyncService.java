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
            JSONObject obj = new JSONObject(task);

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

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Key key = Datastore.createKey(Task.class, obj.get("id").toString());
            Task task;
            try {
                task = Datastore.get(Task.class, key);
            } catch (EntityNotFoundRuntimeException e) {
                task = new Task();
                task.setKey(key);
            }
            obj.remove("id");
            task.copyFromJSON(obj);
            task.set_lastChange(now);
            Datastore.put(task);
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
