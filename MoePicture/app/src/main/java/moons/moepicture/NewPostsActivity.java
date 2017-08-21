package moons.moepicture;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.effects.FadeEffect;
import com.twotoasters.jazzylistview.effects.SlideInEffect;

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
import moons.moepicture.Models.YandeUrl;
import moons.moepicture.Services.Spider;

public class NewPostsActivity extends AppCompatActivity {
    private PictureAdapter pictureAdapter;
    private JazzyGridView gridView;
    private YandeUrl yandeUrl;
    private boolean isBusy;
    private boolean canUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_posts);

        initialView();
        setLiscenter();

        initialUpdate("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_post_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    initialUpdate("");
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                initialUpdate(query);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理动作按钮的点击事件
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void initialView() {
        isBusy = false;
        yandeUrl = YandeUrl.getPostUrl();
        pictureAdapter = new PictureAdapter(this, new LinkedList<PictureItem>());

        gridView = (JazzyGridView)findViewById(R.id.ImageGrid);
        gridView.setAdapter(pictureAdapter);
        gridView.setTransitionEffect(new FadeEffect());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    };

    void setLiscenter() {
        gridView.setOnScrollListener( new AbsListView.OnScrollListener() {
              @Override
              public void onScroll(AbsListView view, int firstVisibleItem,
                                   int visibleItemCount, int totalItemCount) {
                  if (canUpdate &&  !isBusy && (firstVisibleItem+visibleItemCount) == totalItemCount) {
                      new GetUrlContentTask().execute(yandeUrl.getFinalUrl());
                  }
              }

              @Override
              public void onScrollStateChanged(AbsListView view, int scrollState) {
                  //listview滚动时会执行这个方法，这儿调用加载数据的方法。
              }
          }
        );

        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewPostsActivity.this, SingleItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PictureItem", pictureAdapter.getList().get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        );
    }

    void initialUpdate(String tags) {
        canUpdate = true;

        yandeUrl.setTags(tags);
        pictureAdapter.clearList();
        new GetUrlContentTask().execute(yandeUrl.getFinalUrl());
    }


    void UpdataGridView(String result) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .parse(new InputSource(new ByteArrayInputStream(result.getBytes("utf-8"))));

            NodeList nList = doc.getElementsByTagName("post");
            for (int i = 0; i < nList.getLength(); i++) {
                Element postNode = (Element) nList.item(i);
                pictureAdapter.add(PictureItem.getItemFromDomElement(postNode));
            }

            pictureAdapter.notifyDataSetChanged();
            if (nList.getLength() > 0) {
                Toast.makeText(this, "SUCCESS UPDATE", Toast.LENGTH_SHORT).show();
            } else {
                canUpdate = false;
                Toast.makeText(this, "ALL is UPDATE", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "FAIL TO UPDATE", Toast.LENGTH_SHORT).show();
        }

    }

    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            isBusy = true;
            try {
                return Spider.getStringFromUrl(urls[0]);
            } catch (IOException e) {
                // we don't want use see Expection message
                // just return "" let DOM has error then make a toast
                return "";
            }
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            // this is executed on the main thread after the process is over
            // update your UI here
            UpdataGridView(result);
            isBusy = false;
        }
    }
}


