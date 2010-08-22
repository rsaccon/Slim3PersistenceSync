package slim3.service;

import java.util.Date;
import java.util.Iterator;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;

import slim3.meta.TagMeta;
import slim3.model.Tag;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONStringer;


public class TagSyncService {

    public String pushUpdates(long since) throws JSONException {
        TagMeta meta = TagMeta.get();
        Iterator<Tag> tagIterator =
            Datastore
                .query(meta)
                .filter(meta._lastChange.greaterThan(since))
                .asList()
                .iterator();

        JSONArray arr = new JSONArray();

        while (tagIterator.hasNext()) {
            Tag tag = tagIterator.next();
            Key key = tag.getKey();
            JSONObject obj = new JSONObject(tag);

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
            Key key = Datastore.createKey(Tag.class, obj.get("id").toString());
            Tag tag;
            try {
                tag = Datastore.get(Tag.class, key);
            } catch (EntityNotFoundRuntimeException e) {
                tag = new Tag();
                tag.setKey(key);
            }
            obj.remove("id");
            tag.copyFromJSON(obj);
            tag.set_lastChange(now);
            Datastore.put(tag);
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
