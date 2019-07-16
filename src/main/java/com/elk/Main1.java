package com.elk;

import com.elk.apiclients.ApiClientManager;
import com.elk.apiclients.ElkOperator;
import com.elk.core.helper.TwitterHelper;
import com.elk.dataobjects.elk.api.DeleteIndexElkResponse;
import com.elk.dataobjects.elk.IndexSettingsElk;
import com.elk.dataobjects.twitter.TweetDTO;
import com.elk.dataobjects.elk.IndexElk;

public class Main1 {

    public static void main(String[] args) {
        ElkOperator elkOperator = ApiClientManager.getElkOperator();
        IndexSettingsElk settingsElk = IndexSettingsElk.builder()
                .numberOfReplicas(1)
                .numberOfShards(1)
                .build();
        TweetDTO tweetDTO = TwitterHelper.generateTweet();
        DeleteIndexElkResponse response = elkOperator.deleteIndex("test");
        int a=1;
    }
}
