package org.almang.empatlima.model;

/**
 * Created by Eleanor on 11/18/2015.
 */
public class MovieDto {

    private String movieTitle;

    private String movieYear;

    private String movieType;

    private String movieSize;

    private String movieQuality;

    private boolean isSubsExits;

    public String getMovieQuality() {
        return movieQuality;
    }

    public String getMovieSize() {
        return movieSize;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieType() {
        return movieType;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public boolean isSubsExits() {
        return isSubsExits;
    }

    public void setMovieQuality(String movieQuality) {
        this.movieQuality = movieQuality;
    }

    public void setMovieSize(String movieSize) {
        this.movieSize = movieSize;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public void setSubsExits(boolean subsExits) {
        isSubsExits = subsExits;
    }
}
