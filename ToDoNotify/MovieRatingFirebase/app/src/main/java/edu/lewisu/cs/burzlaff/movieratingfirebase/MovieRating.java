package edu.lewisu.cs.burzlaff.movieratingfirebase;

public class MovieRating {
    private String id;
    private String movieTitle;
    private String directorName;
    private Boolean viewedAtTheater;
    private int rating;

    public MovieRating(String i, String mTitle, String dName, Boolean viewed, int r) {
        id = i;
        movieTitle = mTitle;
        directorName = dName;
        viewedAtTheater = viewed;
        rating = r;
    }

    public MovieRating(String i) { id = i; }
    public MovieRating() { }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public String getDirectorName() { return directorName; }
    public void setDirectorName(String directorName) { this.directorName = directorName; }

    public Boolean getViewedAtTheater() { return viewedAtTheater; }
    public void setViewedAtTheater(Boolean viewedAtTheater) { this.viewedAtTheater = viewedAtTheater; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    @Override
    public String toString() {
        return "movieTitle=" + movieTitle + "\n" +
                "directorName=" + directorName + "\n" +
                "viewedAtTheater=" + viewedAtTheater + "\n" +
                "rating=" + rating;
    }
}
