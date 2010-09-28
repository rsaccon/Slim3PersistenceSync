package persistencejs.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Iterator;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONStringer;

import persistencejs.meta.TagMeta;
import persistencejs.model.Tag;

public class TagUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");       

        if (isGet()) {
            TagMeta meta = TagMeta.get();
            Iterator<Tag> projectIterator =
                Datastore
                    .query(meta)
                    .filter(meta.lastChange.greaterThan(asLong("since")))
                    .asList()
                    .iterator();

            JSONArray arr = new JSONArray();

            while (projectIterator.hasNext()) {
                arr.put(TagMeta.get().modelToJSON(projectIterator.next()));
            }

            response.getWriter().write(
                new JSONStringer()
                    .object()
                    .key("now")
                    .value(new Date().getTime())
                    .key("updates")
                    .value(arr)
                    .endObject()
                    .toString());
        } else if (isPost()) {
            BufferedReader input =
                new BufferedReader(new InputStreamReader(request.getInputStream()));
            String str = "";
            for (String line; (line = input.readLine()) != null; str += line);
            input.close();
            
            JSONArray arr = new JSONArray(str);
            long timestamp = new Date().getTime();
            
            for (int i = 0; i < arr.length(); i++) {
                Tag tag = TagMeta.get().JSONtoModel(
                    arr.getJSONObject(i), timestamp);
                tag.setSyncDirty(false);
                Datastore.put(tag);
            }

            response.getWriter().write(
                new JSONStringer()
                    .object()
                    .key("status")
                    .value("ok")
                    .key("now")
                    .value(new Date().getTime())
                    .endObject()
                    .toString());
        }
        
        return null;
    }
}
