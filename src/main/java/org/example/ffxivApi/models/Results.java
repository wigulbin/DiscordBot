package org.example.ffxivApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Results<T> {
    @JsonProperty("Results")
    private List<T> results;

    public Results() {
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
