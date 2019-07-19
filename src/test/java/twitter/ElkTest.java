package twitter;

import com.elk.core.allure.StepAssertion;
import com.elk.core.api.ResponseFormat;
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
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.elk.core.api.ResponseResult.CREATED;
import static com.elk.core.api.ResponseResult.UPDATED;

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

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("com.elk.fixtures.TweetDocFixture#getRandomTweetAndResponseDocFormat")
    @Story("create new document")
    @Description("verify new document creation with valid data")
    void postDocumentTest(String fixtureName, TweetDTO tweetDTO, ResponseFormat format) {
        DocumentDataElkResponse postResponse = elkOperator.postDocument(indexElk, tweetDTO, format);

        StepAssertion.assertSoftly(softly -> {
            softly.assertThat(postResponse.getIndex()).as("Verify post index").isEqualTo(indexElk.getIndexName());
            softly.assertThat(postResponse.getType()).as("Verify post index type").isEqualTo(indexElk.getIndexType());
            softly.assertThat(postResponse.getResult()).as("Verify post result").isEqualTo(CREATED.getValue());
        });
    }

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("com.elk.fixtures.TweetDocFixture#getRandomTweetAndResponseDocFormat")
    @Story("search new document")
    @Description("verify if newly created document could be found by search")
    void searchDocumentTest(String fixtureName, TweetDTO tweetDTO, ResponseFormat format) {
        elkOperator.postDocument(indexElk, tweetDTO, format);

        SearchElkResponse searchElkResponse
                = elkOperator.searchDocument(indexElk.getIndexName(), "user", tweetDTO.getUser(), format);
        var hitList = searchElkResponse.getHits().getHits();

        StepAssertion.assertSoftly(softly -> softly.assertThat(hitList)
                .as("Verify search result contains one item").hasSize(1));

        TweetDTO searchedTweetDTO = customMapMapper.map(hitList.get(0).getSource(), TweetDTO.class);

        StepAssertion.assertSoftly(softly -> softly.assertThat(searchedTweetDTO)
                .as("Verify searched tweet the same as was posted").isEqualTo(tweetDTO));
    }

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("com.elk.fixtures.TweetDocFixture#getRandomTweetAndResponseDocFormat")
    @Story("update existing document")
    @Description("verify updated document")
    void updateDocumentTest(String fixtureName, TweetDTO tweetDTO, ResponseFormat format) {
        DocumentDataElkResponse documentDataElkResponse = elkOperator.postDocument(indexElk, tweetDTO, format);
        DocumentDataElkResponse updateDocumentResponse = elkOperator.updateDocument(indexElk, documentDataElkResponse.getId(), tweetDTO, format);

        StepAssertion.assertSoftly(softly -> {
            softly.assertThat(updateDocumentResponse.getIndex()).as("Verify post index")
                    .isEqualTo(indexElk.getIndexName());
            softly.assertThat(updateDocumentResponse.getId()).as("Verify doc id is the same as before update")
                    .isEqualTo(documentDataElkResponse.getId());
            softly.assertThat(updateDocumentResponse.getVersion()).as("Verify doc version incremented")
                    .isEqualTo(documentDataElkResponse.getVersion() + 1);
            softly.assertThat(updateDocumentResponse.getResult()).as("Verify operation result")
                    .isEqualTo(UPDATED.getValue());
        });
    }
}