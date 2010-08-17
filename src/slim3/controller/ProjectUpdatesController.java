package slim3.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.service.ProjectSyncService;

public class ProjectUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json; charset=UTF-8");        
        
        ProjectSyncService sync =  new ProjectSyncService();
        
        if (request.getMethod().equals("GET")) {
            sync.pushUpdates(
                new Long(request.getParameter("since")).longValue(),
                response.getOutputStream());
        } else if (request.getMethod().equals("POST")) {
            sync.receiveUpdates(
                request.getInputStream(),
                response.getOutputStream());
        }

        return null;
    }
}
