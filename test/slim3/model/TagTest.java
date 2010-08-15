package slim3.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TagTest extends AppEngineTestCase {

    private Tag model = new Tag();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
