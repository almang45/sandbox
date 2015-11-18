package org.almang.empatlima.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.almang.empatlima.model.Constant;
import org.almang.empatlima.model.ImdbResponse;
import org.almang.empatlima.model.SeriesDto;

/**
 * Created by Eleanor on 11/18/2015.
 */
public class SeriesDetailGetter {

    public static void main(String[] args) throws IOException {

        SeriesDto series = new SeriesDto();

        String fileDir = "J:\\Western TV-Show";
        //String fileDir = "E:\\completed";

        final String lastFolder = "tokusatsu -- Shuriken Sentai Ninninger";

        File dir = new File(fileDir);
        fileDir += "\\";

        FileOutputStream out = new FileOutputStream(fileDir + "series-list.txt");

        File[] subDirs = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        String prefix = Constant.EMPTY_STRING;
        boolean checkpoint = true;
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
            String seriesType = Constant.EMPTY_STRING;
            if (str.length > 1) {
                seriesType = str[0];
                tempStr = str[1];
            } else {
                tempStr = str[0];
            }

            series.setTitle(FolderUtil.getTitle(tempStr));
            int totalSeason = FolderUtil.countSubFolder(subDir);
            series.setTotalSeason(totalSeason + " season");
            series.setTotalSize(FolderUtil.calculateFolderSizeString(subDir));
            series.setAverageSizePerSeason(
                    FolderUtil.calculateAverageSizePerSeason(subDir, totalSeason));
            series.setAverageEpisodePerSeason(
                    (FolderUtil.countSeriesEpisodeFiles(subDir.getAbsolutePath()) / totalSeason) +
                            " episodes");

            ImdbResponse imdbResponse =
                    ImdbUtil.getImdbResponse(series.getTitle(), Constant.EMPTY_STRING, seriesType);
            series.setYear(imdbResponse.getYear());
            series.setRuntime(imdbResponse.getRuntime());
            series.setOrigin(imdbResponse.getCountryOfOrigin());
            series.setImdbRating(imdbResponse.getImdbRating());
            series.setImdbId(imdbResponse.getImdbId());
            series.setGenre(imdbResponse.getGenre());
            series.setActors(imdbResponse.getActors());

            System.out.println(imdbResponse);
            out.write(series.toString().getBytes(StandardCharsets.UTF_8));
            out.write(System.getProperty("line.separator").getBytes());
        }
        out.close();
        System.out.println("DONE!!");
    }
}
