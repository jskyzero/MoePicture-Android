package moons.moepicture.Services;
/*
 * Spider is some static methods collection
 * Like get fileName from url
 *      get String   from url (Http Collection)
 *
 * these functions makes other logic more easily and concise
 *
 * Created by jskyzero on 17/08/18.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static String getStringFromUrl(String url_str)throws IOException{
        InputStream is = null;
        try {
            URL url = new URL(url_str);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            // 状态码为200表示连接成功
            if ( conn.getResponseCode() == 200) {
                is = conn.getInputStream(); //获取输入流
                String contentAsString = getStringFromInputStream(is); //转换为string
                return contentAsString;
            } else {
                throw new IOException("conn.getResponseCode() != 200");
            }
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static String getStringFromInputStream(InputStream stream) throws IOException
    {
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
        String content = "", line;
        while ((line = rd.readLine()) != null) {
            content += line;
        }
        return content;
    }
}
