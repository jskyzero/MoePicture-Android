package moons.moepicture.Services;
/*
 * Created by jskyzero on 17/08/18.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Spider {

    public static String getCleanFileName(String name) {
        // The ^ within the brackets stands for "NOT".
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    public static String getFileNameFromUrl(String url) {
        try {
            String fileName = URLDecoder.decode(url, "UTF-8");
            return getCleanFileName(fileName.substring(fileName.lastIndexOf('/') + 1));
        }
        catch (UnsupportedEncodingException e) {
            return "this error will not happen in my opinion";
        }
    }
}
