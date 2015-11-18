package org.almang.empatlima.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;

import org.almang.empatlima.model.Constant;
import org.almang.empatlima.model.SeriesDto;

/**
 * Created by Eleanor on 11/18/2015.
 */
public class SeriesDetailGetter {

    public static void main(String[] args) throws IOException {

        SeriesDto series = new SeriesDto();

        //        String fileDir = "J:\\Western TV-Show";
        String fileDir = "E:\\completed";

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
                series.setSeriesOrigin(str[0]);
                tempStr = str[1];
            } else {
                series.setSeriesOrigin(Constant.EMPTY_STRING);
                tempStr = str[0];
            }

            series.setSeriesTitle(FolderUtil.getTitle(tempStr));
            int totalSeason = FolderUtil.countSubFolder(subDir);
            series.setSeriesTotalSeason(totalSeason + " season");
            series.setSeriesTotalSize(FolderUtil.calculateFolderSizeString(subDir));
            series.setSeriesAverageSizePerSeason(
                    FolderUtil.calculateAverageSizePerSeason(subDir, totalSeason));
            System.out.println(series.getSeriesTitle());
            out.write(series.toString().getBytes());
            out.write(System.getProperty("line.separator").getBytes());
        }
        out.close();
        System.out.println("DONE!!");
    }
}
