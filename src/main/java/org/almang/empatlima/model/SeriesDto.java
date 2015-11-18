package org.almang.empatlima.model;

/**
 * Created by Eleanor on 11/18/2015.
 */
public class SeriesDto {

    private String title;

    private String origin;

    private String year;

    private String runtime;

    private String imdbRating;

    private String genre;

    private String actors;

    private String totalSize;

    private String totalSeason;

    private String averageSizePerSeason;

    private String averageEpisodePerSeason;

    private String imdbId;

    public String getActors() {
        return actors;
    }

    public String getAverageEpisodePerSeason() {
        return averageEpisodePerSeason;
    }

    public String getAverageSizePerSeason() {
        return averageSizePerSeason;
    }

    public String getGenre() {
        return genre;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getOrigin() {
        return origin;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getTitle() {
        return title;
    }

    public String getTotalSeason() {
        return totalSeason;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public String getYear() {
        return year;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setAverageEpisodePerSeason(String averageEpisodePerSeason) {
        this.averageEpisodePerSeason = averageEpisodePerSeason;
    }

    public void setAverageSizePerSeason(String averageSizePerSeason) {
        this.averageSizePerSeason = averageSizePerSeason;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = Constant.IMDB_LINK + imdbId;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalSeason(String totalSeason) {
        this.totalSeason = totalSeason;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public void setYear(String year) {
        if (year.endsWith("–")) {
            this.year = year + "ongoing";
        } else {
            this.year = year;
        }
    }

    @Override
    public String toString() {
        return title + Constant.TAB +
                year + Constant.TAB +
                origin + Constant.TAB +
                imdbRating + Constant.TAB +
                runtime + Constant.TAB +
                genre + Constant.TAB +
                actors + Constant.TAB +
                totalSeason + Constant.TAB +
                totalSize + Constant.TAB +
                averageSizePerSeason + Constant.TAB +
                averageEpisodePerSeason + Constant.TAB +
                imdbId;
    }
}
