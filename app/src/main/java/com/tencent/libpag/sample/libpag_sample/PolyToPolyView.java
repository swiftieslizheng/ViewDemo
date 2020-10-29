package com.tencent.libpag.sample.libpag_sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

public class PolyToPolyView extends View {
    private static final int NUM_OF_POINT = 8;
    /**
     * 图片的折叠后的总宽度
     */
    private int mTranslateDis;
    /**
     * 折叠后的总宽度与原图宽度的比例
     */
    private float mFactor = 0.8f;
    /**
     * 折叠块的个数
     */
    private int mNumOfFolds = 8;

    private Matrix[] mMatrices = new Matrix[mNumOfFolds];

    private Bitmap mBitmap;

    /**
     * 绘制黑色透明区域
     */
    private Paint mSolidPaint;

    /**
     * 绘制阴影
     */
    private Paint mShadowPaint;
    private Matrix mShadowGradientMatrix;
    private LinearGradient mShadowGradientShader;

    /***
     * 原图每块的宽度
     */
    private int mFlodWidth;
    /**
     * 折叠时，每块的宽度
     */
    private int mTranslateDisPerFlod;

    public PolyToPolyView(Context context) {
        super(context);

        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.timg);

        mTranslateDis = (int) (mBitmap.getWidth() * mFactor);
        mFlodWidth = mBitmap.getWidth()/mNumOfFolds;
        mTranslateDisPerFlod = mTranslateDis/mNumOfFolds;

        for(int i=0;i<mNumOfFolds;i++){
            mMatrices[i] = new Matrix();
        }


        mSolidPaint = new Paint();
        int alpha = (int) (255 * mFactor * 0.8f) ;
        mSolidPaint.setColor(Color.argb((int) (alpha*0.8F), 0, 0, 0));

        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        mShadowGradientMatrix = new Matrix();
        mShadowGradientMatrix.setScale(mFlodWidth, 1);
        mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
        mShadowPaint.setAlpha(alpha);

        int depth = (int) Math.sqrt(mFlodWidth * mFlodWidth - mTranslateDisPerFlod * mTranslateDisPerFlod)/2;

        float[] src = new float[NUM_OF_POINT];
        float[] dst = new float[NUM_OF_POINT];

        for(int i=0;i<mNumOfFolds;i++){
            src[0] = i*mFlodWidth;
            src[1] = 0;
            src[2] = src[0]+mFlodWidth;
            src[3] = 0;
            src[4] = src[2];
            src[5] = mBitmap.getHeight();
            src[6] = src[0];
            src[7] = src[5];

            boolean isEven = i%2 == 0;

            dst[0] = i*mTranslateDisPerFlod;
            dst[1] = isEven ? 0:depth;
            dst[2] = dst[0] + mTranslateDisPerFlod;
            dst[3] = isEven? depth:0;
            dst[4] = dst[2];
            dst[5] = isEven?mBitmap.getHeight() - depth:mBitmap.getHeight();
            dst[6] = dst[0];
            dst[7] = isEven?mBitmap.getHeight():mBitmap.getHeight()-depth;

            mMatrices[i].setPolyToPoly(src,0,dst,0,src.length>>1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mNumOfFolds; i++)
        {

            canvas.save();
            //将matrix应用到canvas
            canvas.concat(mMatrices[i]);
            //控制显示的大小
            canvas.clipRect(mFlodWidth * i, 0, mFlodWidth * i + mFlodWidth,
                    mBitmap.getHeight());
            //绘制图片
            canvas.drawBitmap(mBitmap, 0, 0, null);
            //移动绘制阴影
            canvas.translate(mFlodWidth * i, 0);
            if (i % 2 == 0)
            {
                //绘制黑色遮盖
                canvas.drawRect(0, 0, mFlodWidth, mBitmap.getHeight(),
                        mSolidPaint);
            }else
            {
                //绘制阴影
                canvas.drawRect(0, 0, mFlodWidth, mBitmap.getHeight(),
                        mShadowPaint);
            }
            canvas.restore();
        }
    }
}
