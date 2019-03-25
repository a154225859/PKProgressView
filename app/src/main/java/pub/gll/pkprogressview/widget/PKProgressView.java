package pub.gll.pkprogressview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class PKProgressView extends View {

    private Paint mPaintLeft = new Paint(Paint.ANTI_ALIAS_FLAG);//画左侧矩形
    private Paint mPaintRight = new Paint(Paint.ANTI_ALIAS_FLAG);//画右侧矩形

    private Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);//画字体

    private int textSize = sp2px(10);



    private int mLeftColor= Color.parseColor("#E6A93B");//左侧矩形颜色
    private int mRightColor=Color.parseColor("#285AD8");//右侧矩形颜色

    private float mLeftWidth = 0f;//左侧矩形的宽度
    private float mRightWidth = 0f;//右侧矩形的宽度

    private float width = dpTpPx(300);//给控件一个默认宽度

    private float height = dpTpPx(15);//给控件一个默认高度


    public PKProgressView(Context context) {
        super(context);
        init();//初始化
    }
    float mTextHeight,mTextWidth;
    private void init() {
        mPaintLeft.setColor(mLeftColor);
        mPaintRight.setColor(mRightColor);
        mPaintText.setColor(Color.WHITE);//设置字体的颜色
        mPaintText.setTextSize(textSize);
    }
    public PKProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PKProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mLeftNum = 0;//左侧数量
    private int mRightNum = 0;//右侧数量
    private String mLeftText = "我方0";
    private String mRightText = "0对方";

    private float radius = dpTpPx(5);//给定一个默认的圆角大小

    public int getLeftNum() {
        return mLeftNum;
    }

    public void setLeftNum(int mLeftNum) {
        mLeftText = "我方" + mLeftNum;
        this.mLeftNum = mLeftNum;
        invalidate();
    }

    public int getRightNum() {
        return mRightNum;
    }

    public void setRightNum(int mRightNum) {
        mRightText = mRightNum + "对方";
        this.mRightNum = mRightNum;
        invalidate();
    }
    public void setRadius(int radius){
        this.radius = dpTpPx(radius);
    }

    public void setTextSize(int size){
        mPaintText.setTextSize(sp2px(size));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect();
        mPaintText.getTextBounds(mRightText, 0, mRightText.length(), rect);//用一个矩形去"套"字符串,获得能完全套住字符串的最小矩形
        mTextWidth = rect.width();//字符串的宽度
        mTextHeight = rect.height();//字符串的高度

        if (mLeftNum==0&&mRightNum==0){
            mLeftWidth = 0.5f;
            canvas.drawRoundRect(mLeftWidth*width, 0, width, height, radius, radius, mPaintRight);
            canvas.drawRoundRect(0, 0, mLeftWidth*width+radius, height, radius, radius, mPaintLeft);
        }else if (mLeftNum==0){
            mLeftWidth = 0;
            canvas.drawRoundRect(0, 0, width, height, radius, radius, mPaintRight);
            canvas.drawRoundRect(0, 0, 0, height, radius, radius, mPaintLeft);
        }else if (mRightNum==0){
            mLeftWidth = 1;
            canvas.drawRoundRect(0, 0, 0, height, radius, radius, mPaintRight);
            canvas.drawRoundRect(0, 0, width, height, radius, radius, mPaintLeft);
        }else {
            mLeftWidth = (float) mLeftNum / (float) (mRightNum + mLeftNum);

            mRightWidth = (float) mRightNum / (float) (mRightNum + mLeftNum);
            if (mRightNum>mLeftNum) {
                if (mLeftWidth * width>=radius){
                    canvas.drawRoundRect(0, 0, mLeftWidth * width, height, radius, radius, mPaintLeft);
                    canvas.drawRoundRect(mLeftWidth * width - radius, 0, width , height, radius, radius, mPaintRight);
                }else {
                    canvas.drawRoundRect(0, 0, mLeftWidth * width, height, radius, radius, mPaintLeft);
                    canvas.drawRoundRect(0, 0, width , height, radius, radius, mPaintRight);
                }

            }else {
                if (mRightWidth* width>=radius) {
                    canvas.drawRoundRect(mLeftWidth * width, 0, width, height, radius, radius, mPaintRight);
                    canvas.drawRoundRect(0, 0, mLeftWidth * width + radius , height, radius, radius, mPaintLeft);
                }else {
                    canvas.drawRoundRect(mLeftWidth * width, 0, width, height, radius, radius, mPaintRight);
                    canvas.drawRoundRect(0, 0,  mLeftWidth * width+(mRightWidth* width), height, radius, radius, mPaintLeft);
                }
            }
        }
        //绘制左侧文字
        canvas.drawText(mLeftText,
                dpTpPx(5),
                (height+mTextHeight)/2-(height-mTextHeight)/2,
                mPaintText);
        //绘制右侧文字
        canvas.drawText(mRightText,
                width - mTextWidth-dpTpPx(5),
                (height+mTextHeight)/2-(height-mTextHeight)/2,
                mPaintText);


    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }
    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        height = result;
        return result;

    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        width = result;
        return result;

    }

    //将dp转换为px
    public float dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (float) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

    // 将sp值转换为px值
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


}
