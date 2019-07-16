package com.elk.dataobjects.elk.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DeleteIndexElkResponse {

    @SerializedName("acknowledged")
    @Expose
    private boolean acknowledged;
}