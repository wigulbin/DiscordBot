package org.example.ffxivApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {
    @JsonProperty("Page")
    private Integer page;
    @JsonProperty("PageNext")
    private Integer pageNext;
    @JsonProperty("PagePrev")
    private Integer pagePrev;
    @JsonProperty("PageTotal")
    private Integer pageTotal;
    @JsonProperty("Results")
    private Integer results;
    @JsonProperty("ResultsPerPage")
    private Integer resultsPerPage;
    @JsonProperty("ResultsTotal")
    private Integer resultsTotal;

    public Pagination() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageNext() {
        return pageNext;
    }

    public void setPageNext(Integer pageNext) {
        this.pageNext = pageNext;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getResults() {
        return results;
    }

    public void setResults(Integer results) {
        this.results = results;
    }

    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public Integer getResultsTotal() {
        return resultsTotal;
    }

    public void setResultsTotal(Integer resultsTotal) {
        this.resultsTotal = resultsTotal;
    }

    public Integer getPagePrev() {
        return pagePrev;
    }

    public void setPagePrev(Integer pagePrev) {
        this.pagePrev = pagePrev;
    }
}
