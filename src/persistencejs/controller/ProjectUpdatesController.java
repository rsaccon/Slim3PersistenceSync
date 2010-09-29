package persistencejs.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Iterator;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import persistencejs.meta.ProjectMeta;
import persistencejs.model.Project;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONStringer;


public class ProjectUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (isGet()) {
            ProjectMeta meta = ProjectMeta.get();
            Iterator<Project> projectIterator =
                Datastore
                    .query(meta)
                    .filter(meta.lastChange.greaterThan(asLong("since")))
                    .asList()
                    .iterator();

            JSONArray arr = new JSONArray();

            while (projectIterator.hasNext()) {
                arr.put(ProjectMeta.get().modelToJSON(projectIterator.next()));
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
                new BufferedReader(new InputStreamReader(
                    request.getInputStream()));
            String str = "";
            for (String line; (line = input.readLine()) != null; str += line);
            input.close();

            JSONArray arr = new JSONArray(str);
            long timestamp = new Date().getTime();

            for (int i = 0; i < arr.length(); i++) {
                Project project = ProjectMeta.get().JSONtoModel(
                    arr.getJSONObject(i), timestamp);
                project.setSyncDirty(true);
                Datastore.put(project);
                project.setSyncDirty(false);
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
