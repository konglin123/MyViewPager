package com.example.myviewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private MyViewPager myViewPager;
    private RadioGroup radioGroup;

    private int[] imgIds={R.mipmap.bg_login,R.mipmap.bg_login_bottom,R.mipmap.bg_welcome};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager = findViewById(R.id.myviewpager);
        radioGroup=findViewById(R.id.rg);
        initData();
        initListener();
    }


    private void initData() {
        for (int i = 0; i < imgIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgIds[i]);
            myViewPager.addView(imageView);

        }

        View testView = View.inflate(this, R.layout.test, null);
        myViewPager.addView(testView,1);

        View touchView = View.inflate(this, R.layout.touch, null);
        myViewPager.addView(touchView,2);

        for (int i = 0; i < myViewPager.getChildCount(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            if(i==0){
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
    }


    private void initListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViewPager.scroll2Index(checkedId);
            }
        });

        myViewPager.setOnPagerChangeListener(new MyViewPager.OnPagerChangeListener() {
            @Override
            public void onChange(int index) {
                radioGroup.check(index);
            }
        });
    }
}
