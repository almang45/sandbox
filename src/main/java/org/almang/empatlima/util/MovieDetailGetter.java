package org.almang.empatlima.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.almang.empatlima.model.Constant;
import org.almang.empatlima.model.ImdbResponse;

/**
 * Created by Eleanor on 3/16/2015.
 */
public class MovieDetailGetter {

    public static final String IMDB_SEARCH = "http://www.omdbapi.com/";
    public static final String IMDB_LINK = "http://www.imdb.com/title/";
    public static final String SRT_EXT = ".srt";

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
                            Constant.DOCUMENTER_MOVIE.equals(movieType) ||
                            Constant.CONCERT_MOVIE.equals(movieType) ||
                            Constant.CARTOON_MOVIE.equals(movieType) ||
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

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            if ((output = br.readLine()) != null) {
                conn.disconnect();
                System.out.println("----- " + movieTitle + " " + movieYear);
                System.out.println(urlParams);
                return MapperUtil.getObjectMapper(true).readValue(output, ImdbResponse.class);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage() + Constant.PIPE + e.toString());
        }
        return new ImdbResponse(false);
    }

    public static void main(String[] args) throws Exception {

        String movieTitle;
        String movieYear;
        String movieType;
        String movieSize;
        String movieQuality;
        boolean isSubsExits;

        //        String fileDir = "K:\\temp";
        String fileDir = "K:\\Movie"; // movie archives
        //        String fileDir = "E:\\completed\\movie"; // finished movie torrent

        final String lastFolder = "west -- Spy (2015)";

        File dir = new File(fileDir);
        fileDir += "\\";

        FileOutputStream out = new FileOutputStream(fileDir + "list.txt");

        File[] subDirs = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        String prefix = Constant.EMPTY_STRING;
        boolean checkpoint = false;
        for (File subDir : subDirs) {
            if (!subDir.getPath().contains(lastFolder) && !checkpoint) {
                continue;
            } else if (subDir.getPath().contains(lastFolder)) {
                checkpoint = true;
                continue;
            }
            String[] str = subDir.getPath().split(Constant.SPLITTER);
            if (!prefix.equals(str[0])) {
                prefix = str[0];
            }
            str = subDir.getPath().replace(fileDir, Constant.EMPTY_STRING).split(Constant.SPLITTER);
            String tempStr;
            if (str.length > 1) {
                movieType = str[0];
                tempStr = str[1];
            } else {
                movieType = Constant.EMPTY_STRING;
                tempStr = str[0];
            }


            movieTitle = FolderUtil.getTitle(tempStr);
            movieYear = FolderUtil.getYear(tempStr);
            movieSize = FolderUtil.calculateFolderSizeString(subDir);
            movieQuality = new MovieDetailGetter().checkMovieQuality(subDir.getAbsolutePath());
            isSubsExits = new MovieDetailGetter().findSubtitlesFiles(subDir.getAbsolutePath());
            System.out.println(movieTitle);
            ImdbResponse response = getImdbResponse(movieTitle, movieYear, movieType);
            if (response != null) {
                if (Constant.EMPTY_STRING.equals(movieYear)) {
                    movieYear = response.getYear();
                }
                if (Constant.IMDB_ID.equals(movieType)) {
                    movieTitle = response.getTitle();
                }
            }

            String print;
            if (response.getResponse()) {
                print = movieTitle + Constant.TAB + movieYear + Constant.TAB + movieType +
                        Constant.TAB + movieQuality + Constant.TAB + movieSize + Constant.TAB +
                        response.getImdbRating() + Constant.TAB + response.getTomatoRating() +
                        Constant.TAB + response.getRuntime() + Constant.TAB + response.getGenre() +
                        Constant.TAB + response.getDirector() + Constant.TAB +
                        response.getActors() + Constant.TAB + IMDB_LINK + response.getImdbId() +
                        Constant.TAB + isSubsExits;
            } else {
                print = movieTitle + Constant.TAB + movieYear + Constant.TAB + movieType +
                        Constant.TAB + movieQuality + Constant.TAB + movieSize + Constant.TAB +
                        isSubsExits;
            }
            out.write(print.getBytes());
            out.write(System.getProperty("line.separator").getBytes());
        }
        out.close();
        System.out.println("DONE!!");
    }


    public class GenericExtFilter implements FilenameFilter {
        private String ext;

        public GenericExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }

    private String checkMovieQuality(String pathName) {
        File dir = new File(pathName);

        if (!dir.isDirectory()) {
            System.out.println("Directory does not exists : " + pathName);
            return Constant.EMPTY_STRING;
        }

        String[] list = dir.list();
        if (list[0].contains(Constant.HIGH_QUALITY)) {
            return Constant.HIGH_QUALITY;
        } else if (list[0].contains(Constant.MED_QUALITY)) {
            return Constant.MED_QUALITY;
        }
        return Constant.EMPTY_STRING;
    }

    public boolean findSubtitlesFiles(String pathName) {
        GenericExtFilter filter = new GenericExtFilter(SRT_EXT);

        File dir = new File(pathName);

        if (!dir.isDirectory()) {
            System.out.println("Directory does not exists : " + pathName);
            return false;
        }

        // list out all the file name and filter by the extension
        String[] list = dir.list(filter);

        if (list.length == 0) {
            System.out.println("no files end with : " + SRT_EXT);
            return false;
        } else {
            return true;
        }
    }
}
