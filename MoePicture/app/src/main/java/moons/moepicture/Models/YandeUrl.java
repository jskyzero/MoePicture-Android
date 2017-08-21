package moons.moepicture.Models;
/*
 * Created by jskyzero on 17/08/18.
 */

import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;

public class YandeUrl {
    static final String baseUrl= "https://yande.re/";

    public int limit;
    public int page;
    public String tags;
    public String type;
    public String response_type;

    public void setTags(String tags) {
        this.tags = tags;
        this.page = 1;
    }


    public static YandeUrl getPostUrl() {
        YandeUrl url = new YandeUrl();
        url.type = "post";
        url.response_type = ".xml";

        url.limit = 80;
        url.page = 1;
        url.tags = "";
        return url;
    }

    public String getFinalUrl() {
        List<String> keys = new LinkedList<>();
        keys.add("limit=" + Integer.toString(limit));
        keys.add("page=" + Integer.toString(page));
        keys.add("tags=" + tags);

        // next page
        page++;

        StringBuilder sb = new StringBuilder();
        for (String s : keys)
        {
            sb.append(s).append("&");
        }

        return baseUrl + type + response_type + "?" + sb.toString();
    }

}
