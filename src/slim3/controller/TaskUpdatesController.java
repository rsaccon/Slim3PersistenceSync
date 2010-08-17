package slim3.controller;

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
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;

import slim3.meta.TaskMeta;
import slim3.model.Task;

public class TaskUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        TaskMeta meta = TaskMeta.get();
        JsonGenerator json =
            new JsonFactory().createJsonGenerator(
                response.getOutputStream(),
                JsonEncoding.UTF8);
        json.writeStartObject();

        if (request.getMethod().equals("GET")) {
            // PushUpdates
            Long since = new Long(request.getParameter("since")).longValue();
            json.writeNumberField("now", new Date().getTime());
            json.writeFieldName("updates");
            List<Task> tasks =
                Datastore
                    .query(meta)
                    .filter(meta._lastChange.greaterThan(since))
                    .asList();
            mapper.writeValue(json, tasks);
        } else if (request.getMethod().equals("POST")) {
            // ReceiveUpdates
            long now = new Date().getTime();
            boolean ok = true;
            List<HashMap<String,Object>> untyped = mapper.readValue(request.getInputStream(), TypeFactory.collectionType(ArrayList.class, HashMap.class));
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
        }
        json.writeEndObject();
        json.close();

        return null;
    }
}
