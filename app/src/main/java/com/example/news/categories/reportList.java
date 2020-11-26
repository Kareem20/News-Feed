package com.example.news.categories;

public class reportList {

    private String deaths;
    private String cases;
    private String recovered;

    public reportList(String deaths, String cases, String recovered) {
        this.deaths = deaths;
        this.cases = cases;
        this.recovered = recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }
}
