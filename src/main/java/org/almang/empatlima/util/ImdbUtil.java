package org.almang.empatlima.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.almang.empatlima.model.Constant;
import org.almang.empatlima.model.ImdbResponse;

/**
 * Created by Eleanor on 11/18/2015.
 */
public class ImdbUtil {

    public static final String IMDB_SEARCH = "http://www.omdbapi.com/";

    public static ImdbResponse getImdbResponse(String movieTitle, String movieYear,
            String movieType) {

        try {
            movieTitle = movieTitle.replaceAll(" DC", Constant.EMPTY_STRING);
            movieTitle = movieTitle.replaceAll(" ECE", Constant.EMPTY_STRING);
            movieTitle = movieTitle.replaceAll(" ULTIMATE CUT", Constant.EMPTY_STRING);
            movieTitle = movieTitle.replaceAll(" Extended Cut", Constant.EMPTY_STRING);
            movieTitle = movieTitle.replaceAll(" EXTENDED", Constant.EMPTY_STRING);
            movieTitle = movieTitle.replaceAll(" ALTERNATE ENDING", Constant.EMPTY_STRING);
            movieTitle = movieTitle.replaceAll(" UNRATED", Constant.EMPTY_STRING);
            movieTitle = movieTitle.replaceAll("James Bond ", Constant.EMPTY_STRING);
            movieTitle = movieTitle.replaceAll("Indiana Jones and the ", Constant.EMPTY_STRING);
            String urlParams = "?tomatoes=true&plot=full&r=json&";
            if (Constant.EMPTY_STRING.equals(movieYear) && !Constant.IMDB_ID.equals(movieType)) {
                urlParams += "y=&t=" + movieTitle.replaceAll(" ", "+").replaceAll("&", "%26");
            } else if (!Constant.IMDB_ID.equals(movieType)) {
                urlParams += "y=" + movieYear + "&t=" +
                        movieTitle.replaceAll(" ", "+").replaceAll("&", "%26");
            } else {
                urlParams += "y=&i=" + movieTitle;
            }

            URL url;
            if (!Constant.EMPTY_STRING.equals(movieType) &&
                    (Constant.WEST_MOVIE.equals(movieType) ||
                            Constant.DOCUMENTARY_MOVIE.equals(movieType) ||
                            Constant.CONCERT_MOVIE.equals(movieType) ||
                            Constant.ANIMATION_MOVIE.equals(movieType) ||
                            Constant.ASIAN_MOVIE.equals(movieType))) {
                url = new URL(IMDB_SEARCH + urlParams + Constant.SEARCH_MOVIE);
            } else if (!Constant.EMPTY_STRING.equals(movieType) &&
                    Constant.WEST_SERIES.equals(movieType)) {
                url = new URL(IMDB_SEARCH + urlParams + Constant.SEARCH_SERIES);
            } else if (!Constant.EMPTY_STRING.equals(movieType) &&
                    Constant.IMDB_ID.equals(movieType)) {
                url = new URL(IMDB_SEARCH + urlParams);
            } else {
                return new ImdbResponse(false);
            }
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String output;
            if ((output = br.readLine()) != null) {
                conn.disconnect();
                System.out.println("----- " + movieTitle + " " + movieYear);
                return MapperUtil.getObjectMapper(true).readValue(output, ImdbResponse.class);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage() + Constant.PIPE + e.toString());
        }
        return new ImdbResponse(false);
    }
}
