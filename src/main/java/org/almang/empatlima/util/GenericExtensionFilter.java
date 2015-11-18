package org.almang.empatlima.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Eleanor on 11/18/2015.
 */
public class GenericExtensionFilter implements FilenameFilter {

    private String ext;

    public GenericExtensionFilter(String ext) {
        this.ext = ext;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(ext);
    }
}
