package com.example.oday.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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

public class MainActivity extends AppCompatActivity {
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<itemClass> al = new ArrayList<itemClass>();
        for(int i =1; i<40; i+=2){
            al.add(new itemClass(String.valueOf(i),String.valueOf(i+1)));
        }

        mListView = findViewById(R.id.list_items);

        adapter ad = new adapter(al);
        mListView.setAdapter(ad);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Button b = (Button) view.findViewById(R.id.lb);
                 String s = b.getText().toString();
                Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
            }});
//                Button b2 = view.findViewById(R.id.rb);
//                b.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        b.setText("Hi");
//
//                        b.setBackgroundColor(3932159);
//                    }
//                });
//            }
//      });
    }

    class adapter extends BaseAdapter {
        ArrayList<itemClass> alist = new ArrayList<itemClass>();

        public adapter(ArrayList<itemClass> alist) {
            this.alist = alist;
        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int i) {
            return alist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.item, null);



            Button b1 = v.findViewById(R.id.lb);
            Button b2 = v.findViewById(R.id.rb);
            String s = alist.get(i).mButton1;
            b1.setText(s);

            b2.setText(alist.get(i).mButton2);

            return v;
        }
    }
}
