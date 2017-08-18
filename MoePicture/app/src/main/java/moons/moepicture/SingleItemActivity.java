package moons.moepicture;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.io.File;

import moons.moepicture.Models.PictureItem;


public class SingleItemActivity extends AppCompatActivity {

    private PictureItem pictureItem;
//    private RippleView downloadButton;
    private PhotoView image;

    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        initialData();
        updateImage();
        setListener();
    }

    private void setListener() {
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!hasPermissions()) {
                    askPermissions();
                    return false;
                } else {
                    downloadFile(pictureItem.jpeg_url, pictureItem.file_name);
                    return true;
                }
            }
        });
//        downloadButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if (!hasPermissions()) {
//                    askPermissions();
//                } else {
//                    downloadFile(pictureItem.jpeg_url, pictureItem.file_name);
//                }
//            }
//        });
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


    private void initialData() {
        pictureItem = (PictureItem) getIntent().getExtras().get("PictureItem");

        image = (PhotoView) findViewById(R.id.image);
//        downloadButton = (RippleView) findViewById(R.id.download);


        image.enable();
    }

    private void updateImage() {
        GlideUrl glideUrl = new GlideUrl(pictureItem.sample_url, new LazyHeaders.Builder()
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                .build());

        Glide.with(this)
                .load(glideUrl)
                .into(image);
    }

    public void downloadFile(String uRl, String fileName) {
        DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(getString(R.string.app_name))
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir(getString(R.string.app_name), fileName);

        mgr.enqueue(request);
    }
}
