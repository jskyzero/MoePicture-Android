package moons.lab3;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);
        list_view = (ListView) findViewById(R.id.list_view);

        initialListView();
    }

    private void initialListView() {

        List<Map<String, Object>> map_item_list = new LinkedList<>();

        String[] example_items_1 = new String[] {"qwer", "asdf", "zxcv"};
        String[] example_items_2 = new String[] {"qwerqwer", "asdfasdf", "zxcvzxcv"};


        for (int i = 0; i < example_items_1.length; i++) {
            Map<String, Object> map_item = new LinkedHashMap<>();
            map_item.put("item_1", example_items_1[i]);
            map_item.put("item_2", example_items_2[i]);

            map_item_list.add(map_item);
        }

        SimpleAdapter simple_adapter = new SimpleAdapter(this, map_item_list, R.layout.list_item_layout,
                new String[]{"item_1", "item_2"}, new int[] {R.id.text_2, R.id.text_1});

        list_view.setAdapter(simple_adapter);
    }
}
