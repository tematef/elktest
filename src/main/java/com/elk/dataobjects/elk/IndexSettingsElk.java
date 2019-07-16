package com.elk.dataobjects.elk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class IndexSettingsElk {

    @SerializedName("number_of_shards")
    @Expose
    private int numberOfShards;
    @SerializedName("number_of_replicas")
    @Expose
    private int numberOfReplicas;
}