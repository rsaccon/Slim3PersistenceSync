package persistencejs.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import persistencejs.model.Task;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TaskTest extends AppEngineTestCase {

    private Task model = new Task();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
