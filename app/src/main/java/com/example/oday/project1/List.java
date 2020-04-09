package com.example.oday.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setContentView(R.layout.activity_main);
        ArrayList<itemClass> al = new ArrayList<itemClass>();
        for (int i = 1; i < 40; i += 2) {
            al.add(new itemClass(String.valueOf(i), String.valueOf(i + 1)));
        }

        mListView = findViewById(R.id.list_items);

        List.adapter ad = new List.adapter(al);
        mListView.setAdapter(ad);

    }

    class adapter extends BaseAdapter {
        ArrayList<itemClass> informationList = new ArrayList<itemClass>();

        public adapter(ArrayList<itemClass> alist) {
            this.informationList = alist;
        }

        @Override
        public int getCount() {
            return informationList.size();
        }

        @Override
        public Object getItem(int i) {
            return informationList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.item, null);


            TextView tv1 = (TextView) v.findViewById(R.id.leftTextView);
            TextView tv2 = (TextView) v.findViewById(R.id.RightTextView);
            //conncting to item class
            tv1.setText(informationList.get(i).mTextView1);
            tv2.setText(informationList.get(i).mTextView2);

            return v;
        }
    }
}
