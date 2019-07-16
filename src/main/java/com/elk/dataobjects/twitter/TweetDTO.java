package com.elk.dataobjects.twitter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class TweetDTO {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("message")
    @Expose
    private String message;
}
