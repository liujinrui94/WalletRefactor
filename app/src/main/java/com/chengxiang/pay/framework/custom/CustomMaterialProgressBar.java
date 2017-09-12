package com.chengxiang.pay.framework.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.chengxiang.pay.R;


/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/30 15:10
 * @description: 适配Android 5.0 以下圆形ProgressBar
 */


public class CustomMaterialProgressBar extends View {
    private Paint arcPaint;//画笔
    private RectF arcRectF;
    private float startAngle = -60f;//起始绘制角度
    private float sweepAngle = -40f;//偏移角度
    private float incrementAngele = 0;//增量角度
    private final static int DEFAULT_ARC_COLOR = Color.BLUE;
    private int arcColor = DEFAULT_ARC_COLOR;//圆圈划线颜色
    private AnimatorSet animatorSet;
    private int borderWidth;//圆圈划线宽度
    private int DEFAULT_DURATION = 650;//默认的动画时间
    private static float DEFAULT_MAX_ANGLE = -270f;
    private static float DEFAULT_MIN_ANGLE = -10f;


    private int size;


    public CustomMaterialProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomMaterialProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs, 0);
        init();
    }

    public CustomMaterialProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arcPaint = new Paint();
        arcPaint.setColor(arcColor);
        arcPaint.setStrokeWidth(borderWidth);
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcRectF = new RectF();
    }


    private void initAttributeSet(Context context, AttributeSet attrs, int defStyleAttr) {
        Resources resources = getResources();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMaterialProgressBar, defStyleAttr, 0);
        arcColor = typedArray.getColor(R.styleable.CustomMaterialProgressBar_arcColor, Color.BLUE);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.CustomMaterialProgressBar_borderlineWidth,
                resources.getDimensionPixelSize(R.dimen.train_border_width));

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(arcRectF, startAngle + incrementAngele, sweepAngle, false, arcPaint);
        if (animatorSet == null || !animatorSet.isRunning()) {
            startAnimation();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = (w < h) ? w : h;
        setupBound();
    }

    /**
     * 设置边界
     */
    private void setupBound() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        arcRectF.set(paddingLeft + borderWidth, paddingTop + borderWidth, size - paddingLeft - borderWidth, size - paddingTop - borderWidth);
    }

    private void startAnimation() {
        if (animatorSet != null && animatorSet.isRunning()) {
            animatorSet.cancel();
        }

        animatorSet = new AnimatorSet();

        AnimatorSet set = circleAnimation();
        animatorSet.play(set);
        animatorSet.addListener(new Animator.AnimatorListener() {
            private boolean isCancel = false;

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isCancel) {
                    startAnimation();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isCancel = true;
            }
        });

        animatorSet.start();
    }

    /**
     * 循环的动画
     */
    private AnimatorSet circleAnimation() {
        //从小圈到大圈
        ValueAnimator holdAnimator1 = ValueAnimator.ofFloat(incrementAngele + DEFAULT_MIN_ANGLE, incrementAngele + 115f);
        holdAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                incrementAngele = (float) animation.getAnimatedValue();
            }
        });
        holdAnimator1.setDuration(DEFAULT_DURATION);
        holdAnimator1.setInterpolator(new LinearInterpolator());


        ValueAnimator expandAnimator = ValueAnimator.ofFloat(DEFAULT_MIN_ANGLE, DEFAULT_MAX_ANGLE);
        expandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (float) animation.getAnimatedValue();
                incrementAngele -= sweepAngle;
                invalidate();
            }
        });
        expandAnimator.setDuration(DEFAULT_DURATION);
        expandAnimator.setInterpolator(new DecelerateInterpolator(2));


        //从大圈到小圈
        ValueAnimator holdAnimator = ValueAnimator.ofFloat(startAngle, startAngle + 115f);
        holdAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (float) animation.getAnimatedValue();
            }
        });

        holdAnimator.setDuration(DEFAULT_DURATION);
        holdAnimator.setInterpolator(new LinearInterpolator());

        ValueAnimator narrowAnimator = ValueAnimator.ofFloat(DEFAULT_MAX_ANGLE, DEFAULT_MIN_ANGLE);
        narrowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        narrowAnimator.setDuration(DEFAULT_DURATION);
        narrowAnimator.setInterpolator(new DecelerateInterpolator(2));

        AnimatorSet set = new AnimatorSet();
        set.play(holdAnimator1).with(expandAnimator);
        set.play(holdAnimator).with(narrowAnimator).after(holdAnimator1);
        return set;
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public void setArcColor(int arcColor) {
        this.arcColor = arcColor;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }
}
