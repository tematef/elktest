package com.elk.dataobjects.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetDTO {

    @JsonProperty("user")
    private String user;
    @JsonProperty("post_date")
    private String postDate;
    @JsonProperty("message")
    private String message;
}
