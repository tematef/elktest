package com.elk.dataobjects.elk.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetIndicesElkResponse {

    private List<SingleIndexElkResponce> indices;

    @EqualsAndHashCode
    @Getter
    public class SingleIndexElkResponce {
        @SerializedName("health")
        @Expose
        private String health;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("index")
        @Expose
        private String index;
        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("pri")
        @Expose
        private String pri;
        @SerializedName("rep")
        @Expose
        private String rep;
        @SerializedName("docs.count")
        @Expose
        private String docsCount;
        @SerializedName("docs.deleted")
        @Expose
        private String docsDeleted;
        @SerializedName("store.size")
        @Expose
        private String storeSize;
        @SerializedName("pri.store.size")
        @Expose
        private String priStoreSize;
    }
}
