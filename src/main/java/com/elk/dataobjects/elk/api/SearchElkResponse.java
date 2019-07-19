package com.elk.dataobjects.elk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchElkResponse {

    @JsonProperty("took")
    private int took;
    @JsonProperty("timed_out")
    private boolean timedOut;
    @JsonProperty("_shards")
    private Shards shards;
    @JsonProperty("hits")
    private Hits hits;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hits {

        @JsonProperty("total")
        private int total;
        @JsonProperty("max_score")
        private double maxScore;
        @JsonProperty("hits")
        private List<Hit> hits = null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hit {

        @JsonProperty("_index")
        private String index;
        @JsonProperty("_type")
        private String type;
        @JsonProperty("_id")
        private String id;
        @JsonProperty("_score")
        private double score;
        @JsonProperty("_source")
        private Object source;
    }

    @Data
    private class Shards {

        @JsonProperty("total")
        private int total;
        @JsonProperty("successful")
        private int successful;
        @JsonProperty("skipped")
        private int skipped;
        @JsonProperty("failed")
        private int failed;
    }
}
