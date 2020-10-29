package com.tencent.libpag.sample.libpag_sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View {
    private int count = 6;
    private float angle = (float)(Math.PI*2)/count;
    private float radius;
    private int centerX;
    private int centerY;
    private String[] titles = {"发育","对线","团战","生存","输出","经济"};
    private double[] data = {100,60,60,60,100,50,10,20};
    private float maxValue = 100;
    private Paint mainPaint;                //雷达区画笔
    private Paint valuePaint;               //数据区画笔
    private Paint textPaint;                //文本画笔

    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mainPaint= new Paint();
        mainPaint.setColor(Color.GRAY);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(2.5f);

        textPaint = new Paint();          // 创建画笔
        textPaint.setColor(Color.BLACK);        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(48f);              // 设置字体大小

        valuePaint = new Paint();
        valuePaint.setColor(Color.BLUE);

    }

    //设置标题
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    //设置数值
    public void setData(double[] data) {
        this.data = data;
        postInvalidate();
    }

    //设置最大数值
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    //设置蜘蛛网颜色
    public void setMainPaintColor(int color){
        mainPaint.setColor(color);
    }

    //设置标题颜色
    public void setTextPaintColor(int color){
        textPaint.setColor(color);
    }

    //设置覆盖局域颜色
    public void setValuePaintColor(int color){
        valuePaint.setColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h,w)/2*0.6f;
        centerX = w/2;
        centerY = h/2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void drawPolygon(Canvas canvas){
        Path path = new Path();
        float r = radius/(count-1);
        for(int i=1;i<count;i++){
            float curR = r*i;
            path.reset();
            for(int j=0;j<count;j++){
                if(j==0){
                    path.moveTo(centerX+curR,centerY);
                }else{
                    float x = (float)(centerX+curR*Math.cos(angle*j));
                    float y = (float)(centerY+curR*Math.sin(angle*j));
                    path.lineTo(x,y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path,mainPaint);
        }
    }

    private void drawLines(Canvas canvas){
        Path path = new Path();
        for(int i=0;i<count;i++){
            path.reset();
            path.moveTo(centerX,centerY);
            float x = (float)(centerX+radius*Math.cos(angle*i));
            float y = (float)(centerY+radius*Math.sin(angle*i));
            path.lineTo(x,y);
            canvas.drawPath(path,mainPaint);
        }
    }

    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float frontheight = fontMetrics.descent - fontMetrics.ascent;
        for(int i=0;i<count;i++){
            float x = (float)(centerX+(radius+frontheight/2)*Math.cos(angle*i));
            float y = (float)(centerY+(radius+frontheight/2)*Math.sin(angle*i));
            if(angle*i>=0 && angle*i<=Math.PI/2){
                canvas.drawText(titles[i],x,y,textPaint);
            }else if(angle*i>= 3*Math.PI/2 && angle*i<Math.PI*2){
                canvas.drawText(titles[i],x,y,textPaint);
            }else if(angle*i>Math.PI/2 && angle*i<Math.PI){
                float dis = textPaint.measureText(titles[i]);
                canvas.drawText(titles[i],x-dis,y,textPaint);
            }else if(angle*i>=Math.PI && angle*i<3*Math.PI/2){
                float dis = textPaint.measureText(titles[i]);
                canvas.drawText(titles[i],x-dis,y,textPaint);
            }
        }
    }

    private void drawRegion(Canvas canvas){
        Path path = new Path();
        valuePaint.setAlpha(255);
        for(int i=0;i<count;i++){
            double percent = data[i]/maxValue;
            float x = (float)(centerX+radius*Math.cos(angle*i)*percent);
            float y = (float)(centerY+radius*Math.sin(angle*i)*percent);
            if(i == 0){
                path.moveTo(x,centerY);
            }else{
                path.lineTo(x,y);
            }
            canvas.drawCircle(x,y,10,valuePaint);
        }
        path.close();

        valuePaint.setAlpha(120);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }
}
