package org.almang.empatlima.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;

import org.almang.empatlima.model.Constant;
import org.almang.empatlima.model.ImdbResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Eleanor on 3/16/2015.
 */
public class MovieDetailGetter {

    public static void main(String[] args) throws Exception {

        String movieTitle;
        String movieYear;
        String movieType;
        String movieSize;
        String movieQuality;
        boolean isSubsExits;

        //String fileDir = "K:\\temp";
        String fileDir = "K:\\Movie"; // movie archives
        //String fileDir = "E:\\completed\\movie"; // finished movie torrent

        final String lastFolder = "west -- Spy (2015)";

        File dir = new File(fileDir);
        fileDir += "\\";

        FileOutputStream out = new FileOutputStream(fileDir + "movie-list.txt");

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
            isSubsExits = FolderUtil.checkSubtitlesFiles(subDir.getAbsolutePath());
            ImdbResponse response = ImdbUtil.getImdbResponse(movieTitle, movieYear, movieType);
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
                        response.getActors() + Constant.TAB + Constant.IMDB_LINK +
                        response.getImdbId() + Constant.TAB + isSubsExits;
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


    private String checkMovieQuality(String pathName) {
        File dir = new File(pathName);

        if (!dir.isDirectory()) {
            System.out.println("Directory does not exists : " + pathName);
            return Constant.EMPTY_STRING;
        }

        String[] list = dir.list();
        if (StringUtils.containsIgnoreCase(list[0], Constant.HIGH_QUALITY)) {
            return Constant.HIGH_QUALITY;
        } else if (StringUtils.containsIgnoreCase(list[0], Constant.MED_QUALITY)) {
            return Constant.MED_QUALITY;
        } else if (StringUtils.containsIgnoreCase(list[0], Constant.LOW1_QUALITY)) {
            return Constant.LOW1_QUALITY;
        } else if (StringUtils.containsIgnoreCase(list[0], Constant.LOW2_QUALITY)) {
            return Constant.LOW2_QUALITY;
        }
        return Constant.EMPTY_STRING;
    }
}
