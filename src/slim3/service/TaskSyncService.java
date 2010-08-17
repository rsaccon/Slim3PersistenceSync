package slim3.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;
import org.slim3.util.BeanUtil;

import slim3.meta.TaskMeta;
import slim3.model.Task;

import com.google.appengine.api.datastore.Key;


public class TaskSyncService {
    
    ObjectMapper mapper = new ObjectMapper();
    TaskMeta meta = TaskMeta.get();

    public void pushUpdates(long since, OutputStream outputStream) throws IOException {
        JsonGenerator json =
            new JsonFactory().createJsonGenerator(
                outputStream,
                JsonEncoding.UTF8);
        json.writeStartObject();
        json.writeNumberField("now", new Date().getTime());
        json.writeFieldName("updates");
        
        List<Task> tasks =
            Datastore
                .query(meta)
                .filter(meta._lastChange.greaterThan(since))
                .asList();
        mapper.writeValue(json, tasks);
        
        json.writeEndObject();
        json.close();
    }
    
    public void receiveUpdates(InputStream inputStream, OutputStream outputStream) throws IOException {
        JsonGenerator json =
            new JsonFactory().createJsonGenerator(
                outputStream,
                JsonEncoding.UTF8);
        json.writeStartObject(); 
        
        long now = new Date().getTime();
        boolean ok = true;
        List<HashMap<String,Object>> untyped = mapper.readValue(inputStream, TypeFactory.collectionType(ArrayList.class, HashMap.class));
        Iterator<HashMap<String, Object>> untypedIter = untyped.iterator();
        while(untypedIter.hasNext()){
            HashMap<String, Object> hashmap = (HashMap<String, Object>) untypedIter.next(); 
            try {
                Key key = Datastore.createKey(Task.class, hashmap.get("id").toString());
                Task task;
                try {
                    task = Datastore.get(Task.class, key);
                } catch (EntityNotFoundRuntimeException e) {
                    task = new Task();
                    task.setKey(key);
                }
                hashmap.remove("id");
                BeanUtil.copy(hashmap, task);
                task.set_lastChange(now);
                Datastore.put(task);   
            } catch (NumberFormatException nfe) {
                ok = false;
            } catch (NullPointerException npe) {
                ok = false;
            }
        }
        json.writeStringField("status", (ok) ? "ok" : "error");
        json.writeNumberField("now", now);
        json.writeEndObject();
        json.close();
    }

}
