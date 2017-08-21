package moons.moepicture;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import me.next.slidebottompanel.SlideBottomPanel;
import moons.moepicture.Models.PictureItem;


public class SingleItemActivity extends AppCompatActivity {

    private PictureItem pictureItem;
    private PhotoView image;
    private ImageView download;
    private ImageView share;
    private TextView head_text;
    private ListView list_view;

    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        initialView();
        setListener();
        updateImage();
    }

    private void setListener() {
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return downloadImage();
            }
        });

        download.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                downloadImage();
            }
        });

        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });
    }

    private void shareImage() {
        Toast.makeText(this, "Need Finished", Toast.LENGTH_SHORT).show();
    }


    private void initialView() {
        pictureItem = (PictureItem) getIntent().getExtras().get("PictureItem");

        image = (PhotoView) findViewById(R.id.single_image);
        image.enable();

        download = (ImageView) findViewById(R.id.download);
        share = (ImageView) findViewById(R.id.share);
        head_text = (TextView) findViewById(R.id.head_text);
        list_view = (ListView) findViewById(R.id.list_view);

        head_text.setText(pictureItem.name);
        initialListView();
    }

    private void initialListView() {
        String[] tags = pictureItem.tags.split(" ");
        list_view.setAdapter(new ArrayAdapter<>(this, R.layout.list_tag_item, tags));
    }

    private boolean downloadImage() {
        if (!hasPermissions()) {
            askPermissions();
            return false;
        } else {
            downloadFile(pictureItem.jpeg_url, pictureItem.file_name);
            return true;
        }
    }

    private void updateImage() {
        GlideUrl glideUrl = new GlideUrl(pictureItem.sample_url, new LazyHeaders.Builder()
                .addHeader("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                .build());

        RequestOptions cropOptions = new RequestOptions().error(R.drawable.error);

        Glide.with(this)
             .load(glideUrl)
             .thumbnail(Glide.with(this).load(R.drawable.loading))
             .apply(cropOptions)
             .into(image);
    }

    private boolean hasPermissions() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        return  hasPermission;
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
                    downloadFile(pictureItem.jpeg_url, pictureItem.file_name);
                } else
                {
                    Toast.makeText(this,
                            "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void downloadFile(String uRl, String fileName) {
        DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uRl));

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(getString(R.string.app_name))
                .setDescription("Download" + fileName)
                .setDestinationInExternalPublicDir(getString(R.string.app_name), fileName);

        mgr.enqueue(request);
    }
}
