package org.example.ffxivApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResultContainer<T> {
    @JsonProperty("Pagination")
    private Pagination pagination;
    @JsonProperty("Results")
    private List<T> results;

    public ResultContainer() {
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
