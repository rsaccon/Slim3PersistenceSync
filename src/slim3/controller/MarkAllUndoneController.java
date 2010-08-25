package slim3.controller;

import java.util.Iterator;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.repackaged.org.json.JSONObject;

import slim3.model.Task;

public class MarkAllUndoneController extends Controller {

    @Override
    public Navigation run() throws Exception {
        Iterator<Task> iterator = Datastore.query(Task.class).asList().iterator();
        while(iterator.hasNext()){
            Task task = (Task) iterator.next(); 
            task.setDone(false);
            task.put();
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new JSONObject().put("status", "ok").toString());
        
        return null;
    }
}
