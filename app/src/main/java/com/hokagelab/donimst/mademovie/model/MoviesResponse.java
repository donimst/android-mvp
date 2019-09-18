package com.hokagelab.donimst.mademovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    @SerializedName("results")
    private List<Movies> results;
    @SerializedName("total_results")
    private int totalResults;

    public void setResults(List<Movies> results) {
        this.results = results;
    }

    public List<Movies> getResults() {
        return results;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

}
