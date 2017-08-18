package moons.moepicture.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.zip.Inflater;

import moons.moepicture.Models.PictureItem;
import moons.moepicture.R;

/*
 * Created by jskyzero on 17/08/18.
 */

public class PictureAdapter extends BaseAdapter {

    private Context context;
    private List<PictureItem> pictureList;

    public PictureAdapter(Context context, List<PictureItem> list) {
        this.context = context;
        this.pictureList = list;
    }

    public List<PictureItem> getList()
    {
        return pictureList;
    }

    public boolean isListEmpty()
    {
        return pictureList == null;
    }

    public void add(PictureItem item)
    {
        this.pictureList.add(item);
    }


    @Override
    public int getCount() {
        return isListEmpty() ? 0 : pictureList.size();
    }

    @Override
    public Object getItem(int i) {
        return pictureList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View recycled, ViewGroup container){
        final ImageView view;
        if (recycled == null) {
            view = (ImageView) LayoutInflater.from(context).inflate(R.layout.grid_picture_item, container, false);
        } else {
            view = (ImageView) recycled;
        }

        String url = ((PictureItem)getItem(position)).preview_url;

        Glide.with(context)
            .load(url).apply(RequestOptions.centerCropTransform())
            .into(view);

        return view;
    }
}
