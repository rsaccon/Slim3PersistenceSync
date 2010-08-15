package slim3.controller;

import java.util.Iterator;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import slim3.model.Task;

public class MarkAllDoneController extends Controller {

    @Override
    public Navigation run() throws Exception {
        Iterator<Task> iterator = Datastore.query(Task.class).asList().iterator();
        while(iterator.hasNext()){
            Task task = (Task) iterator.next(); 
            task.setDone(true);
            task.put();
        }
        
        response.setContentType("application/json; charset=UTF-8");
        JsonGenerator json =
            new JsonFactory().createJsonGenerator(
                response.getOutputStream(),
                JsonEncoding.UTF8);
        json.writeStartObject();
        json.writeStringField("status", "ok");
        json.writeEndObject();
        json.close();
        return null;
    }
}
