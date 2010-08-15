package slim3.controller;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.meta.TaskMeta;
import slim3.model.Task;

public class TaskUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
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
            TaskMeta p = TaskMeta.get();
            List<Task> tasks =
                Datastore
                    .query(p)
                    .filter(p._lastChange.greaterThan(since))
                    .asList();
            mapper.writeValue(json, tasks);
        } else if (request.getMethod().equals("POST")) {

            // Parse for: Array of entity instance
            // [{"id":"BDDF85807155497490C12D6DA3A833F1",
            // "fieldName":"foo"}]

            // The server is supposed to persist these changes (if valid).
            // Internally the items must be assigned a `_lastChange` timestamp
            // `TS`. If OK, the server will return a JSON object with "ok" as
            // `status` and `TS` as `now`. _Note:_ it is important that the
            // timestamp of all items and the one returned are the same.

            json.writeStringField("status", "ok");
            json.writeNumberField("now", new Date().getTime());
        }
        json.writeEndObject();
        json.close();

        return null;
    }
}
