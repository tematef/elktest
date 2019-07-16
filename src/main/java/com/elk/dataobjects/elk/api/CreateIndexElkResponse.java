package com.elk.dataobjects.elk.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class CreateIndexElkResponse {

    @SerializedName("acknowledged")
    @Expose
    private boolean acknowledged;
    @SerializedName("shards_acknowledged")
    @Expose
    private boolean shardsAcknowledged;
    @SerializedName("index")
    @Expose
    private String index;
}
