package persistencejs.controller;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import persistencejs.controller.TagUpdatesController;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TagUpdatesControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/tagUpdates");
        TagUpdatesController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
