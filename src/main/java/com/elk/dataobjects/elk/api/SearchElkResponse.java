package com.elk.dataobjects.elk.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class SearchElkResponse {

    @SerializedName("took")
    @Expose
    private int took;
    @SerializedName("timed_out")
    @Expose
    private boolean timedOut;
    @SerializedName("_shards")
    @Expose
    private Shards shards;
    @SerializedName("hits")
    @Expose
    private Hits hits;

    @EqualsAndHashCode
    private class Hits {

        @SerializedName("total")
        @Expose
        private int total;
        @SerializedName("max_score")
        @Expose
        private double maxScore;
        @SerializedName("hits")
        @Expose
        private List<Hit> hits = null;
    }

    @EqualsAndHashCode
    private class Hit {

        @SerializedName("_index")
        @Expose
        private String index;
        @SerializedName("_type")
        @Expose
        private String type;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("_score")
        @Expose
        private double score;
        @SerializedName("_source")
        @Expose
        private Source source;
    }

    @EqualsAndHashCode
    private class Shards {

        @SerializedName("total")
        @Expose
        private int total;
        @SerializedName("successful")
        @Expose
        private int successful;
        @SerializedName("skipped")
        @Expose
        private int skipped;
        @SerializedName("failed")
        @Expose
        private int failed;
    }

    @EqualsAndHashCode
    private class Source {

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
}
