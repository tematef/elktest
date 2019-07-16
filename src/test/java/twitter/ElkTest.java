package twitter;

import com.elk.core.junit.ElkTestCase;
import com.elk.core.util.FakeUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ElkTest extends ElkTestCase {

    private String indexName = "twitter1" + FakeUtil.faker().number().randomDigit();

    @BeforeEach
    void preconditions() throws IOException {
        restHighLevelClient.getActions().createIndex(indexName);
    }

    @AfterEach
    void cleanUp() throws IOException {
        restHighLevelClient.getActions().deleteIndex(indexName);
    }

    @Test
    void test() throws IOException {
        System.out.println("hello");
//        restHighLevelClient.getActions().createIndex("test");
    }
}
