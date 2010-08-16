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

import slim3.meta.TagMeta;
import slim3.model.Project;
import slim3.model.Tag;

public class TagUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        TagMeta meta = TagMeta.get();
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
            List<Tag> tags =
                Datastore
                    .query(meta)
                    .filter(meta._lastChange.greaterThan(since))
                    .asList();
            mapper.writeValue(json, tags);
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
            Iterator<Tag> tagIter = Datastore.query(meta).filter(meta.key.in(keys)).asList().iterator();
            while(tagIter.hasNext()){
                Tag tag = tagIter.next();
                BeanUtil.copy(map.get(tag.getKey().toString()), tag);
                tag.set_lastChange(now);
                Datastore.put(tag);
                map.remove(tag.getKey().toString());
            }
            
            // new elements
            Iterator<String> newKeysIter = map.keySet().iterator();
             while (newKeysIter.hasNext()) {
                String keyStr = newKeysIter.next();
                HashMap<String, Object> obj = (HashMap<String, Object>)map.get(keyStr);
                Tag tag = new Tag();
                tag.setKey(Datastore.createKey(Tag.class, keyStr));
                obj.remove("id");
                BeanUtil.copy(obj, tag);
                tag.set_lastChange(now);
                Datastore.put(tag);
            }

            json.writeStringField("status", "ok");
            json.writeNumberField("now", new Date().getTime());
        }
        json.writeEndObject();
        json.close();

        return null;
    }
}
