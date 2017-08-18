package moons.moepicture;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import moons.moepicture.Adapter.PictureAdapter;
import moons.moepicture.Models.PictureItem;

public class NewPostsActivity extends AppCompatActivity {
    private PictureAdapter pictureAdapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_posts);
        pictureAdapter = new PictureAdapter(this, new LinkedList<PictureItem>());
        gridView = (GridView)findViewById(R.id.ImageGrid);
        gridView.setAdapter(pictureAdapter);
        new GetUrlContentTask().execute("https://yande.re/post.xml?page=1",
                                        "https://yande.re/post.xml?page=2");
    }

    void UpdataGridView(String result) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(result.getBytes("utf-8"))));

            NodeList nList = doc.getElementsByTagName("post");
            for (int i = 0; i < nList.getLength(); i++) {
                Element postNode = (Element) nList.item(i);
                pictureAdapter.add(PictureItem.getItemFromDomElement(postNode));
            }
            pictureAdapter.notifyDataSetChanged();
            Toast.makeText(this, "SUCCESS UPDATE", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]); //连接并下载数据
            } catch (Exception e) {
                return "";
            }

        }

        private String downloadUrl(String myurl) throws IOException
        {   InputStream is = null;
            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode(); //状态码为200表示连接成功
                is = conn.getInputStream(); //获取输入流
                String contentAsString = readIt(is); //转换为string
                return contentAsString;
            }
            finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        public String readIt(InputStream stream) throws IOException,
                UnsupportedEncodingException
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line;
            }
            return content;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            // this is executed on the main thread after the process is over
            // update your UI here
           UpdataGridView(result);
        }
    }
}


