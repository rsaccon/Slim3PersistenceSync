package slim3.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.service.TagSyncService;

public class TagUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        
        TagSyncService sync =  new TagSyncService();
        
        if (isGet()) {
            sync.pushUpdates(
                //new Long(param("since")).longValue(),
                asLong("since"),
                response.getOutputStream());
        } else if (isPost()) {
            sync.receiveUpdates(
                request.getInputStream(),
                response.getOutputStream());
        }
        
        return null;
    }
}
