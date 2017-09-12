package com.chengxiang.pay.framework.custom.signature;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chengxiang.pay.framework.utils.ImageUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/14 15:27
 * @description: 签名的画图页面
 */

public class DrawView extends View implements View.OnTouchListener {

    private List<FirstPoint> points;
    private Paint paint;

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        points = new ArrayList<>();
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    // 清屏
    public void clearView() {
        points.clear();
        invalidate();
    }

    /**
     * 返回图片
     *
     * @return
     * @throws IOException
     */
    public File saveSignature() throws IOException {
        if (points.size() == 0) {
            return null;
        } else {
            Bitmap bitmap = Bitmap.createBitmap(this.getWidth(),
                    this.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            this.draw(canvas);
            File file = null;
            try {
                file = ImageUtil.getFileFromBitmap(bitmap, ImageUtil.getImageFile());
            } catch (IOException e) {
                Logger.e("图片bitmap转file异常__", e);
                e.printStackTrace();
            }
            return file;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (FirstPoint point : points) {
            point.draw(canvas, paint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        FirstPoint point;
        if (action == MotionEvent.ACTION_MOVE) {
            float firstX = event.getX();
            float firstY = event.getY();
            point = new FollowPoints(event.getX(), event.getY(), Color.BLACK,
                    10, points.get(points.size() - 1));
        } else if (action == MotionEvent.ACTION_DOWN) {
            float lastX = event.getX();
            float lastY = event.getY();
            point = new FirstPoint(event.getX(), event.getY(), Color.BLACK, 10);
        } else {
            return false;
        }
        points.add(point);
        invalidate();
        return true;
    }
}
