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

import slim3.meta.TagMeta;
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
            // ReceiveUpdates
            long now = new Date().getTime();
            boolean ok = true;
            List<HashMap<String,Object>> untyped = mapper.readValue(request.getInputStream(), TypeFactory.collectionType(ArrayList.class, HashMap.class));
            Iterator<HashMap<String, Object>> untypedIter = untyped.iterator();
            while(untypedIter.hasNext()){
                HashMap<String, Object> hashmap = (HashMap<String, Object>) untypedIter.next(); 
                try {
                    Key key = Datastore.createKey(Tag.class, Long.parseLong((String) hashmap.get("id")));
                    Tag tag;
                    try {
                        tag = Datastore.get(Tag.class, key);
                    } catch (EntityNotFoundRuntimeException e) {
                        tag = new Tag();
                        tag.setKey(key);
                    }
                    hashmap.remove("id");
                    BeanUtil.copy(hashmap, tag);
                    tag.set_lastChange(now);
                    Datastore.put(tag);   
                } catch (NumberFormatException nfe) {
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
