package slim3.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;

import slim3.meta.ProjectMeta;
import slim3.model.Project;

public class ProjectUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        ProjectMeta meta = ProjectMeta.get();
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
            List<Project> projects =
                Datastore
                    .query(meta)
                    .filter(meta._lastChange.greaterThan(since))
                    .asList();
            mapper.writeValue(json, projects);
        } else if (request.getMethod().equals("POST")) {
            long now = new Date().getTime();
            Collection<Key> keys = new HashSet<Key>();
            HashMap<String,Object> map = new HashMap<String,Object>();
            List<HashMap<String,Object>> untyped = mapper.readValue(request.getInputStream(), TypeFactory.collectionType(ArrayList.class, HashMap.class));
            Iterator<HashMap<String, Object>> untypedIter = untyped.iterator();
            while(untypedIter.hasNext()){
                HashMap<String, Object> obj = (HashMap<String, Object>) untypedIter.next(); 
                Key key = Datastore.createKey(Project.class, obj.get("id").toString());
                keys.add(key);
                obj.remove("id");
                map.put(key.toString(), obj);
            }
            
            // existing elements
            Iterator<Project> projectIter = Datastore.query(meta).filter(meta.key.in(keys)).asList().iterator();
            while(projectIter.hasNext()){
                Project project = projectIter.next();
                BeanUtil.copy(map.get(project.getKey().toString()), project);
                project.set_lastChange(now);
                Datastore.put(project);
                map.remove(project.getKey().toString());
            }
            
            // new elements
            Iterator<String> newKeysIter = map.keySet().iterator();
             while (newKeysIter.hasNext()) {
                String keyStr = newKeysIter.next();
                HashMap<String, Object> obj = (HashMap<String, Object>)map.get(keyStr);
                Project project = new Project();
                project.setKey(Datastore.createKey(Project.class, keyStr));
                obj.remove("id");
                BeanUtil.copy(obj, project);
                project.set_lastChange(now);
                Datastore.put(project);
            }

            json.writeStringField("status", "ok");
            json.writeNumberField("now", now);
        }
        json.writeEndObject();
        json.close();

        return null;
    }
}
