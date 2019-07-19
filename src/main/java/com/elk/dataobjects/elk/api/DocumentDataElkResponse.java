package com.elk.dataobjects.elk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentDataElkResponse {

    @JsonProperty("_index")
    public String index;
    @JsonProperty("_type")
    public String type;
    @JsonProperty("_id")
    public String id;
    @JsonProperty("_version")
    public int version;
    @JsonProperty("result")
    public String result;
    @JsonProperty("_shards")
    public Shards shards;
    @JsonProperty("_seq_no")
    public int seqNo;
    @JsonProperty("_primary_term")
    public int primaryTerm;

    @Data
    private class Shards {

        @JsonProperty("total")
        public int total;
        @JsonProperty("successful")
        public int successful;
        @JsonProperty("failed")
        public int failed;
    }
}
