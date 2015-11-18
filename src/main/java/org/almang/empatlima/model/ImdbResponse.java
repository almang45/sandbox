package org.almang.empatlima.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Eleanor on 3/18/2015.
 */
public class ImdbResponse {

    @JsonProperty(value = "Response")
    private boolean response;

    @JsonProperty(value = "Title")
    private String title = Constant.EMPTY_STRING;

    @JsonProperty(value = "Year")
    private String year = Constant.EMPTY_STRING;

    @JsonProperty(value = "Runtime")
    private String runtime = Constant.EMPTY_STRING;

    @JsonProperty(value = "Genre")
    private String genre = Constant.EMPTY_STRING;

    @JsonProperty(value = "Director")
    private String director = Constant.EMPTY_STRING;

    @JsonProperty(value = "Actors")
    private String actors = Constant.EMPTY_STRING;

    @JsonProperty(value = "Country")
    private String countryOfOrigin = Constant.EMPTY_STRING;

    private String imdbRating = Constant.EMPTY_STRING;

    private String tomatoRating = Constant.EMPTY_STRING;

    @JsonProperty(value = "imdbID")
    private String imdbId = Constant.EMPTY_STRING;

    @JsonProperty(value = "Type")
    private String type = Constant.EMPTY_STRING;

    public ImdbResponse() {
    }

    public ImdbResponse(boolean response) {
        this.response = response;
    }

    public String getActors() {
        return actors;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public String getDirector() {
        return director;
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

    public boolean getResponse() {
        return response;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getTitle() {
        return title;
    }

    public String getTomatoRating() {
        return tomatoRating;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setResponse(String response) {
        this.response = Boolean.valueOf(response.toUpperCase());
    }

    public void setRuntime(String runtime) {
        if (!Constant.EMPTY_STRING.equals(runtime) || !Constant.NOT_AVAILABLE.equals(runtime)) {
            this.runtime = runtime.replace(" min", Constant.EMPTY_STRING);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTomatoRating(String tomatoRating) {
        this.tomatoRating = tomatoRating;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "ImdbResponse{" +
                "response=" + response +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
