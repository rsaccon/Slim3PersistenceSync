package slim3.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.service.TagSyncService;

public class TagUpdatesController extends Controller {

    @Override
    public Navigation run() throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");       
        
        TagSyncService sync =  new TagSyncService();
        
        if (isGet()) {
            response.getWriter().write(sync.pushUpdates(asLong("since")));
        } else if (isPost()) {
            BufferedReader input =
                new BufferedReader(new InputStreamReader(request.getInputStream()));
            String str = "";
            for (String line; (line = input.readLine()) != null; str += line);
            input.close();
            
            response.getWriter().write(sync.receiveUpdates(str));
        }
        
        return null;
    }
}
