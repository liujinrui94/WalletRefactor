package com.chengxiang.pay.framework.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.chengxiang.pay.bean.BalanceModel;
import com.chengxiang.pay.framework.utils.DisplayUtil;
import com.chengxiang.pay.framework.utils.StringUtil;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/11 8:29
 * @description:
 */
public class AccountBalanceCakeView extends View {

    private Context mContext;
    private BalanceModel model;
    private int cakeWidth; //饼图的宽
    private int cakeHeight;//饼图的高
    private int distanceTop;//累计收益距顶部距离
    private int distanceBottom;//累计收益距底部距离

    private int screenWidth;//整体画布宽
    private int screenHeight;//画布高

    public AccountBalanceCakeView(Context context) {
        this(context, null);
    }

    public AccountBalanceCakeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccountBalanceCakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specModel = MeasureSpec.getMode(widthMeasureSpec);
        int speSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSpeSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specModel == MeasureSpec.EXACTLY) {
            screenWidth = widthSpeSize;
            screenHeight = speSize;
        }
        setMeasuredDimension(widthSpeSize, speSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint firstPaint = new Paint();//第一个画笔
        firstPaint.setAntiAlias(true);
        firstPaint.setStyle(Paint.Style.STROKE);
        firstPaint.setStrokeWidth(DisplayUtil.dpToPx(mContext,18));
        firstPaint.setColor(Color.parseColor("#75b347"));
        firstPaint.setStrokeCap(Paint.Cap.ROUND);

        Paint secondPaint = new Paint();//第二个画笔
        secondPaint.setAntiAlias(true);
        secondPaint.setStyle(Paint.Style.STROKE);
        secondPaint.setStrokeWidth(DisplayUtil.dpToPx(mContext,18));
        secondPaint.setColor(Color.parseColor("#fcb534"));
        secondPaint.setStrokeCap(Paint.Cap.ROUND);

        int x_start = screenWidth / 2 - cakeWidth / 2;
        int y_start = screenHeight / 2 - cakeHeight / 2;
        int x_end = screenWidth / 2 + cakeWidth / 2;
        int y_end = screenHeight / 2 + cakeHeight / 2;

        float okAngel;// 可提现金额起始角度
        float okRangeAngel;//可提现金额角度范围
        float noAngel;//不可提现金额起始角度
        float noRangeAngel;//不可提现金额角度范围

        if (model == null) {
            return;
        }
        double okAmt = Double.parseDouble(model.getOkAmt());//可提现金额
        double noAmt = Double.parseDouble(model.getNoAmt());//不可提现金额、待入账收益

        double totalAmt = Double.parseDouble(model.getBalance());
        String amt = StringUtil.formatDouble(totalAmt);
        okAngel = 135;
        okRangeAngel = (float) (okAmt / totalAmt * 270);
        noAngel = okAngel + okRangeAngel;
        noRangeAngel = (float) (noAmt / totalAmt * 270);

        RectF rectF = new RectF(x_start, y_start, x_end, y_end);//饼图大小
        canvas.drawArc(rectF, okAngel, okRangeAngel, false, firstPaint);

        if (noAngel > 360) {
            noAngel = noAngel - 360;
        }

        if (noRangeAngel != 0) {
            canvas.drawArc(rectF, noAngel, noRangeAngel, false, secondPaint);//255+150 = 405
            secondPaint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawArc(rectF, noAngel, noRangeAngel / 3, false, secondPaint);
        }

        Paint textPaint = new TextPaint();//文字画笔
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(10);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(DisplayUtil.dpToPx(mContext,15));
        textPaint.setColor(Color.parseColor("#333333"));
        Rect rect = new Rect();
        String textOne = "账户余额";
        textPaint.getTextBounds(textOne, 0, textOne.length(), rect);

        canvas.drawText(textOne, x_start + (x_end - x_start) / 2 - rect.width() / 2, y_start + (y_end - y_start) / 2 - rect.height() / 2, textPaint);

        Rect rect1 = new Rect();
        textPaint.setTextSize(DisplayUtil.dpToPx(mContext,15));
        textPaint.setColor(Color.parseColor("#fd6e4c"));
        textPaint.getTextBounds(amt, 0, amt.length(), rect1);
        canvas.drawText(amt, x_start + (x_end - x_start) / 2 - rect.width() / 2, y_start + (y_end - y_start) / 2 + 60 - rect.height() / 2, textPaint);
        textPaint.setTextSize(DisplayUtil.dpToPx(mContext,12));
        textPaint.setColor(Color.parseColor("#999999"));
        textPaint.getTextBounds("可提现余额", 0, "可提现余额".length(), rect1);
        canvas.drawText("可提现余额", x_start - rect.width() - 30, y_end - 90, textPaint);
        textPaint.setTextSize(DisplayUtil.dpToPx(mContext,15));
        textPaint.setColor(Color.parseColor("#333333"));
        textPaint.getTextBounds(model.getOkAmt(), 0, model.getOkAmt().length(), rect1);
        canvas.drawText(model.getOkAmt(), x_start - rect.width() - 20, y_end - 40, textPaint);
        textPaint.setTextSize(DisplayUtil.dpToPx(mContext,12));
        textPaint.setColor(Color.parseColor("#999999"));
        textPaint.getTextBounds("待入账收益", 0, "待入账收益".length(), rect1);
        canvas.drawText("待入账收益", x_end + 30, y_end - 90, textPaint);

        textPaint.setTextSize(DisplayUtil.dpToPx(mContext,15));
        textPaint.setColor(Color.parseColor("#333333"));
        textPaint.getTextBounds(model.getNoAmt(), 0, model.getNoAmt().length(), rect1);
        canvas.drawText(model.getNoAmt(), x_end + 50, y_end - 40, textPaint);

        if (okAmt == 0 && noAmt == 0) {
            firstPaint.setColor(Color.GRAY);
            canvas.drawArc(rectF, 135, 270, false, firstPaint);
        }
    }

    public void setInitCakeData(float cakeWidthDp, float cakeHeightDp, BalanceModel balanceModel) {

        model = balanceModel;
        cakeWidth = DisplayUtil.dpToPx(mContext, cakeWidthDp);
        cakeHeight = DisplayUtil.dpToPx(mContext, cakeHeightDp);
        invalidate();
    }
}