package com.example.oday.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Questions extends AppCompatActivity {
    TextView tv;
    RadioGroup rg;
    RadioButton rbChecked,rbYes, rbNo;
    Button next;
    int i;
    int isclick = 0;
    String result, res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        tv = findViewById(R.id.tv);
        rg = findViewById(R.id.rg);
        next = findViewById(R.id.next);
        i = 0;
        result = "";
        int rb = rg.getCheckedRadioButtonId();

        tv.setText("هل لديك ");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isclick != 0) {
                    result = result + res;
                }
                Toast.makeText(Questions.this, result , Toast.LENGTH_SHORT).show();
         //       Toast.makeText(Questions.this, "rb2"  +String.valueOf(rb2.isSelected()), Toast.LENGTH_SHORT).show();
                if(isclick == 0){
                    Toast.makeText(Questions.this, "يجب أن تختار أحد الخيارات", Toast.LENGTH_SHORT).show();

                }
                else{
                    i++;
//
                    if (i == 1) {
                        tv.setText(" هل لديك حرارة");
                    }
                    else if (i == 2) {
                        tv.setText(" هل لديك عطس");
                    }
                    else if (i == 3) {
                        tv.setText(" هل لديك سعال جاف");
                    }
                    else if (i > 3){
                        tv.setText("انتهى الاختبار");
                        rbYes.setVisibility(View.INVISIBLE);
                        rbNo.setVisibility(View.INVISIBLE);
                        next.setVisibility(View.INVISIBLE);

                    }
                    rbYes.setChecked(false);
                    rbNo.setChecked(false);
                    isclick=0;
                }


            }
        });


    }


    public void radioButtonClick(View view) {
        int rb = rg.getCheckedRadioButtonId();
        rbChecked = findViewById(rb);
        isclick = 1;
        rbYes = findViewById(R.id.rbyes);
        rbNo = findViewById(R.id.rbno);
        if (rbYes == rbChecked){
            res = "1";
        }
        else if(rbNo == rbChecked){
            res ="0";
        }
    }
}

