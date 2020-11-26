package com.example.news.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Worldwide {

    @SerializedName("updated")
    @Expose
    private String updated;

    @SerializedName("cases")
    @Expose
    private String cases;

    @SerializedName("todayCases")
    @Expose
    private String todayCases;

    @SerializedName("deaths")
    @Expose
    private String deaths;

    @SerializedName("todayDeaths")
    @Expose
    private String todayDeaths;

    @SerializedName("recovered")
    @Expose
    private String recovered;

    @SerializedName("todayRecovered")
    @Expose
    private String todayRecovered;

    @SerializedName("active")
    @Expose
    private String active;

    @SerializedName("critical")
    @Expose
    private String critical;

    @SerializedName("casesPerOneMillion")
    @Expose
    private String casesPerOneMillion;

    @SerializedName("deathsPerOneMillion")
    @Expose
    private String deathsPerOneMillion;

    @SerializedName("tests")
    @Expose
    private String tests;

    @SerializedName("testsPerOneMillion")
    @Expose
    private String testsPerOneMillion;

    @SerializedName("population")
    @Expose
    private String population;

    @SerializedName("oneCasePerPeople")
    @Expose
    private String oneCasePerPeople;

    @SerializedName("oneDeathPerPeople")
    @Expose
    private String oneDeathPerPeople;
    @SerializedName("oneTestPerPeople")
    @Expose
    private String oneTestPerPeople;

    @SerializedName("activePerOneMillion")
    @Expose
    private String activePerOneMillion;

    @SerializedName("recoveredPerOneMillion")
    @Expose
    private String recoveredPerOneMillion;

    @SerializedName("criticalPerOneMillion")
    @Expose
    private String criticalPerOneMillion;

    @SerializedName("affectedCountries")
    @Expose
    private String affectedCountries;


    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(String casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public String getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(String deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(String testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(String oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public String getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(String oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public String getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public void setOneTestPerPeople(String oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public String getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(String activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public String getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(String recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public String getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(String criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public String getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(String affectedCountries) {
        this.affectedCountries = affectedCountries;
    }

}