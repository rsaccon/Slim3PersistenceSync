package slim3.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.repackaged.org.json.JSONObject;

import slim3.model.Project;
import slim3.model.Tag;
import slim3.model.Task;

public class ResetController extends Controller {

    @Override
    public Navigation run() throws Exception {
        Datastore.delete(Datastore.query(Project.class).asKeyList());
        Datastore.delete(Datastore.query(Tag.class).asKeyList());
        Datastore.delete(Datastore.query(Task.class).asKeyList());
        
        Project project = new Project();
        project.setName("Main project");
        for (int i = 0; i < 25; i++) {
            Task task = new Task();
            task.setName("Task "+ i);
            task.setDone(false);
            task.getProjectRef().setModel(project);
            task.syncAwarePut();
        }
        project.syncAwarePut();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new JSONObject().put("status", "ok").toString());

        return null;
    }
}