package org.almang.empatlima.model;

/**
 * Created by Eleanor on 11/18/2015.
 */
public class SeriesDto {

    private String seriesTitle;

    private String seriesOrigin;

    private String seriesTotalSize;

    private String seriesTotalSeason;

    private String seriesAverageSizePerSeason;

    public String getSeriesAverageSizePerSeason() {
        return seriesAverageSizePerSeason;
    }

    public String getSeriesOrigin() {
        return seriesOrigin;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public String getSeriesTotalSeason() {
        return seriesTotalSeason;
    }

    public String getSeriesTotalSize() {
        return seriesTotalSize;
    }

    public void setSeriesAverageSizePerSeason(String seriesAverageSizePerSeason) {
        this.seriesAverageSizePerSeason = seriesAverageSizePerSeason;
    }

    public void setSeriesOrigin(String seriesOrigin) {
        this.seriesOrigin = seriesOrigin;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public void setSeriesTotalSeason(String seriesTotalSeason) {
        this.seriesTotalSeason = seriesTotalSeason;
    }

    public void setSeriesTotalSize(String seriesTotalSize) {
        this.seriesTotalSize = seriesTotalSize;
    }

    @Override
    public String toString() {
        return seriesTitle + Constant.TAB + seriesOrigin + Constant.TAB + seriesTotalSeason +
                Constant.TAB + seriesTotalSize + Constant.TAB + seriesAverageSizePerSeason;
    }
}
