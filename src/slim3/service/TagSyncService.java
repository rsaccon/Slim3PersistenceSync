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

import slim3.meta.TagMeta;
import slim3.model.Tag;

import com.google.appengine.api.datastore.Key;


public class TagSyncService {
    
    ObjectMapper mapper = new ObjectMapper();
    TagMeta meta = TagMeta.get();

    public void pushUpdates(long since, OutputStream outputStream) throws IOException {
        JsonGenerator json =
            new JsonFactory().createJsonGenerator(
                outputStream,
                JsonEncoding.UTF8);
        json.writeStartObject();
        json.writeNumberField("now", new Date().getTime());
        json.writeFieldName("updates");
        
        List<Tag> tags =
            Datastore
                .query(meta)
                .filter(meta._lastChange.greaterThan(since))
                .asList();
        mapper.writeValue(json, tags);
        
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
                Key key = Datastore.createKey(Tag.class, hashmap.get("id").toString());
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
