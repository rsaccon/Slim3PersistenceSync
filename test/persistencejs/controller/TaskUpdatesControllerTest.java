package persistencejs.controller;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import persistencejs.controller.TaskUpdatesController;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TaskUpdatesControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/taskUpdates");
        TaskUpdatesController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
