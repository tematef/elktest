package twitter;

import com.elk.core.helper.TwitterHelper;
import com.elk.core.junit.ElkTestCase;
import com.elk.dataobjects.elk.IndexElk;
import com.elk.dataobjects.elk.api.PostDataElkResponse;
import com.elk.dataobjects.twitter.TweetDTO;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ElkTest extends ElkTestCase {

    private IndexElk indexElk;

    @BeforeEach
    void preconditions(){
        indexElk = TwitterHelper.getTwitterIndex();
        restHighLevelClient.getActions().createIndex(indexElk.getIndexName());
    }

    @AfterEach
    void cleanUp() {
        restHighLevelClient.getActions().deleteIndex(indexElk.getIndexName());
    }

//    @Test
//    void postDocumentTest() {
//        TweetDTO tweetDTO = TwitterHelper.generateTweet();
//        PostDataElkResponse postResponse = elkOperator.postDocument(indexName, indexType, tweetDTO);
//
//        SoftAssertions.assertSoftly(softly -> {
//            softly.assertThat(postResponse.getIndex()).as("Verify post index").isEqualTo(indexName);
//            softly.assertThat(postResponse.getType()).as("Verify post index type").isEqualTo(indexType);
//            softly.assertThat(postResponse.getResult()).as("Verify post result").isEqualTo("created");
//        });
//    }

    @Test
    void searchDocumentTest() {
        TweetDTO tweetDTO = TwitterHelper.generateTweet();
        PostDataElkResponse postResponse = elkOperator.postDocument(indexElk.getIndexName(), indexElk.getIndexType(), tweetDTO);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(postResponse.getIndex()).as("Verify post index").isEqualTo(indexElk.getIndexName());
            softly.assertThat(postResponse.getType()).as("Verify post index type").isEqualTo(indexElk.getIndexType());
            softly.assertThat(postResponse.getResult()).as("Verify post result").isEqualTo("created");
        });
    }
}