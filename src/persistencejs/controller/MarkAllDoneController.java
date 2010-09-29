package persistencejs.controller;

import java.util.Iterator;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import persistencejs.model.Task;

import com.google.appengine.repackaged.org.json.JSONObject;


public class MarkAllDoneController extends Controller {

    @Override
    public Navigation run() throws Exception {
        Iterator<Task> iterator = Datastore.query(Task.class).asList().iterator();
        while(iterator.hasNext()){
            Task task = (Task) iterator.next(); 
            task.setDone(true);
            Datastore.put(task);
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new JSONObject().put("status", "ok").toString());
        
        return null;
    }
}
