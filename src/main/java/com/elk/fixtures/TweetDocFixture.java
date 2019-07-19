package com.elk.fixtures;

import com.elk.core.helper.TwitterHelper;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.elk.core.api.ResponseFormat.JSON;
import static com.elk.core.api.ResponseFormat.YAML;

public final class TweetDocFixture {

    private TweetDocFixture() {}

    public static Stream<Arguments> getRandomTweetAndResponseDocFormat() {
        return Stream.of(
                Arguments.of("json return type", TwitterHelper.generateTweet(), JSON),
                Arguments.of("yaml return type",TwitterHelper.generateTweet(), YAML));
    }
}