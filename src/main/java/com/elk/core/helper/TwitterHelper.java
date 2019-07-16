package com.elk.core.helper;

import com.elk.dataobjects.twitter.TweetDTO;
import com.elk.dataobjects.elk.IndexElk;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.elk.core.util.FakeUtil.faker;

public final class TwitterHelper {

    private static IndexElk indexElk;

    private TwitterHelper() {}

    public static IndexElk getTwitterIndex() {
        return Optional.ofNullable(indexElk).orElseGet(() -> indexElk = IndexElk.builder()
                .indexName("twitter")
                .indexType("tweet")
                .build());
    }

    public static TweetDTO generateTweet() {
        return TweetDTO.builder()
                .user(faker().name().firstName())
                .postDate(faker().date().past(5, TimeUnit.SECONDS).toInstant().toString())
                .message(faker().gameOfThrones().quote())
                .build();
    }
}
