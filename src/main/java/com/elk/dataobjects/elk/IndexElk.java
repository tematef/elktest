package com.elk.dataobjects.elk;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndexElk {

    private String indexName;
    private String indexType;
}