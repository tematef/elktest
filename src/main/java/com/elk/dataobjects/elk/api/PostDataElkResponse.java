package com.elk.dataobjects.elk.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PostDataElkResponse {

    @SerializedName("_index")
    @Expose
    private String index;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_version")
    @Expose
    private int version;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("_shards")
    @Expose
    private Shards shards;
    @SerializedName("_seq_no")
    @Expose
    private int seqNo;
    @SerializedName("_primary_term")
    @Expose
    private int primaryTerm;

    @Getter
    @EqualsAndHashCode
    private class Shards {

        @SerializedName("total")
        @Expose
        private int total;
        @SerializedName("successful")
        @Expose
        private int successful;
        @SerializedName("failed")
        @Expose
        private int failed;
    }
}
