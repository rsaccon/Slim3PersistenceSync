package persistencejs.controller;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import persistencejs.controller.ProjectUpdatesController;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ProjectUpdatesControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/projectUpdates");
        ProjectUpdatesController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
