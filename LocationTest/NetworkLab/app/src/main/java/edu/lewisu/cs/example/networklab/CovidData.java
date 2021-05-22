package edu.lewisu.cs.example.networklab;

public class CovidData {
    private String date;
    private int cases;
    private int tested;
    private int deaths;

    public CovidData(String date, int cases, int tested, int deaths) {
        this.date = date;
        this.cases = cases;
        this.tested = tested;
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return "CovidData{" +
                "date='" + date + '\'' +
                ", cases=" + cases +
                ", tested=" + tested +
                ", deaths=" + deaths +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getTested() {
        return tested;
    }

    public void setTested(int tested) {
        this.tested = tested;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
