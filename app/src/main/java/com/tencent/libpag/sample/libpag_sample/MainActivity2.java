package com.tencent.libpag.sample.libpag_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity2 extends AppCompatActivity {

    private RadarView radarView;
    private Button btnReset;
    private BezierView bezier;
    private Bezier3View bazier3;
    private MagicCircle magicCircle;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RelativeLayout rlMain2 = findViewById(R.id.rl_main2);
//        btnReset = findViewById(R.id.btn_reset);
//        radarView = new RadarView(this);
//        rlMain2.addView(radarView);
//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                double[] data = {Math.random()*100,Math.random()*100,Math.random()*100,Math.random()*100,Math.random()*100,Math.random()*100,Math.random()*100,Math.random()*100};
//                radarView.setData(data);
//            }
//        });
//        bezier = new BezierView(this);
//        rlMain2.addView(bezier);
//        bazier3 = new Bezier3View(this);
//        rlMain2.addView(bazier3);
//        magicCircle = new MagicCircle(this);
//        rlMain2.addView(magicCircle);
//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                magicCircle.startAnimation();
//            }
//        });
//        rlMain2.addView(new BealonView(this));
//        searchView = new SearchView(this);
//        rlMain2.addView(searchView);
//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchView.startAnimator();
//            }
//        });
//          rlMain2.addView(new PolyToPolyView(this));
    }

}