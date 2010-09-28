package persistencejs.controller;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import persistencejs.controller.MarkAllUndoneController;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class MarkAllUndoneControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/markAllUndone");
        MarkAllUndoneController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
