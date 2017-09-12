package com.chengxiang.pay.framework.custom.signature;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by FengWenyao on 2016/7/14.
 * 类描述：
 */
class FollowPoints extends FirstPoint implements Point {
    private FirstPoint firstPoint;

    FollowPoints(float x, float y, int color, int width, FirstPoint point) {
        super(x, y, color, width);
        this.firstPoint = point;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(this.color);
        paint.setStrokeWidth(this.width);
        canvas.drawLine(this.x, this.y, firstPoint.x, firstPoint.y, paint);
        canvas.drawCircle(this.x, this.y, this.width / 2, paint);
    }
}
