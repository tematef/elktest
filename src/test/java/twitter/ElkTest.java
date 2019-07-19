package twitter;

import com.elk.core.allure.StepAssertion;
import com.elk.core.helper.TwitterHelper;
import com.elk.core.junit.ElkTestCase;
import com.elk.core.util.CustomMapMapper;
import com.elk.dataobjects.elk.IndexElk;
import com.elk.dataobjects.elk.api.DocumentDataElkResponse;
import com.elk.dataobjects.elk.api.SearchElkResponse;
import com.elk.dataobjects.twitter.TweetDTO;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static com.elk.core.api.ResponseFormat.JSON;
import static com.elk.core.api.ResponseFormat.YAML;

@Epic("Elastic Api Tests")
@Feature("Post / Update / Search calls")
@Execution(ExecutionMode.CONCURRENT)
class ElkTest extends ElkTestCase {

    private static IndexElk indexElk;
    private CustomMapMapper customMapMapper = new CustomMapMapper();

    @BeforeAll
    static void preconditions() {
        indexElk = TwitterHelper.getTwitterIndex();
        restHighLevelClient.getActions().createIndex(indexElk.getIndexName());
    }

    @AfterAll
    static void cleanUp() {
        restHighLevelClient.getActions().deleteIndex(indexElk.getIndexName());
    }

    @Test
    @Story("create new document with json return type")
    @Description("verify if new document creation with valid data")
    void postDocumentJsonTest() {
        TweetDTO tweetDTO = TwitterHelper.generateTweet();
        DocumentDataElkResponse postResponse = elkOperator.postDocument(indexElk, tweetDTO, JSON);

        StepAssertion.assertSoftly(softly -> {
            softly.assertThat(postResponse.getIndex()).as("Verify post index").isEqualTo(indexElk.getIndexName());
            softly.assertThat(postResponse.getType()).as("Verify post index type").isEqualTo(indexElk.getIndexType());
            softly.assertThat(postResponse.getResult()).as("Verify post result").isEqualTo("created");
        });
    }

    @Test
    @Story("create new document with yaml return type")
    @Description("verify if new document creation with valid data")
    void postDocumentYamlTest() {
        TweetDTO tweetDTO = TwitterHelper.generateTweet();
        DocumentDataElkResponse postResponse = elkOperator.postDocument(indexElk, tweetDTO, YAML);

        StepAssertion.assertSoftly(softly -> {
            softly.assertThat(postResponse.getIndex()).as("Verify post index").isEqualTo(indexElk.getIndexName());
            softly.assertThat(postResponse.getType()).as("Verify post index type").isEqualTo(indexElk.getIndexType());
            softly.assertThat(postResponse.getResult()).as("Verify post result").isEqualTo("created");
        });
    }

    @Test
    @Story("search new document with json return type")
    @Description("verify if newly created document could be found by search")
    void searchDocumentJsonTest() {
        TweetDTO tweetDTO = TwitterHelper.generateTweet();
        elkOperator.postDocument(indexElk, tweetDTO, JSON);

        SearchElkResponse searchElkResponse
                = elkOperator.searchDocument(indexElk.getIndexName(), "user", tweetDTO.getUser(), JSON);
        var hitList = searchElkResponse.getHits().getHits();

        StepAssertion.assertSoftly(softly -> softly.assertThat(hitList)
                .as("Verify search result contains one item").hasSize(1));

        TweetDTO searchedTweetDTO = customMapMapper.map(hitList.get(0).getSource(), TweetDTO.class);

        StepAssertion.assertSoftly(softly -> softly.assertThat(searchedTweetDTO)
                .as("Verify searched tweet the same as was posted").isEqualTo(tweetDTO));
    }

    @Test
    @Story("search new document with yaml return type")
    @Description("verify if newly created document could be found by search")
    void searchDocumentYamlTest() {
        TweetDTO tweetDTO = TwitterHelper.generateTweet();
        elkOperator.postDocument(indexElk, tweetDTO, JSON);

        SearchElkResponse searchElkResponse
                = elkOperator.searchDocument(indexElk.getIndexName(), "user", tweetDTO.getUser(), YAML);
        var hitList = searchElkResponse.getHits().getHits();

        StepAssertion.assertSoftly(softly -> softly.assertThat(hitList)
                .as("Verify search result contains one item").hasSize(1));

        TweetDTO searchedTweetDTO = customMapMapper.map(hitList.get(0).getSource(), TweetDTO.class);

        StepAssertion.assertSoftly(softly -> softly.assertThat(searchedTweetDTO)
                .as("Verify searched tweet the same as was posted").isEqualTo(tweetDTO));
    }

    @Test
    @Story("update existing document and json return type")
    @Description("verify updated document")
    void updateDocumentJsonTest() {
        TweetDTO tweetDTO = TwitterHelper.generateTweet();
        DocumentDataElkResponse documentDataElkResponse = elkOperator.postDocument(indexElk, tweetDTO, JSON);
        DocumentDataElkResponse updateDocumentResponse = elkOperator.updateDocument(indexElk, documentDataElkResponse.getId(), tweetDTO, JSON);

        StepAssertion.assertSoftly(softly -> {
            softly.assertThat(updateDocumentResponse.getIndex()).as("Verify post index").isEqualTo(indexElk.getIndexName());
            softly.assertThat(updateDocumentResponse.getId()).as("Verify doc id is the same as before update").isEqualTo(documentDataElkResponse.getId());
            softly.assertThat(updateDocumentResponse.getVersion()).as("Verify doc version incremented").isEqualTo(documentDataElkResponse.getVersion() + 1);
            softly.assertThat(updateDocumentResponse.getResult()).as("Verify operation result").isEqualTo("updated");
        });
    }

    @Test
    @Story("update existing document and yaml return type")
    @Description("verify updated document")
    void updateDocumentYamlTest() {
        TweetDTO tweetDTO = TwitterHelper.generateTweet();
        DocumentDataElkResponse documentDataElkResponse = elkOperator.postDocument(indexElk, tweetDTO, JSON);
        DocumentDataElkResponse updateDocumentResponse = elkOperator.updateDocument(indexElk, documentDataElkResponse.getId(), tweetDTO, YAML);

        StepAssertion.assertSoftly(softly -> {
            softly.assertThat(updateDocumentResponse.getIndex()).as("Verify post index").isEqualTo(indexElk.getIndexName());
            softly.assertThat(updateDocumentResponse.getId()).as("Verify doc id is the same as before update").isEqualTo(documentDataElkResponse.getId());
            softly.assertThat(updateDocumentResponse.getVersion()).as("Verify doc version incremented").isEqualTo(documentDataElkResponse.getVersion() + 1);
            softly.assertThat(updateDocumentResponse.getResult()).as("Verify operation result").isEqualTo("updated");
        });
    }
}