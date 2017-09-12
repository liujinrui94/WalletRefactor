package com.chengxiang.pay.framework.custom.signature;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/14 15:37
 * @description: 签名画笔
 */

class FirstPoint extends Point {
    protected float x, y;//画笔的中心点
    protected int color;//画笔的颜色
    protected int width;//画笔的宽度

    FirstPoint(float x, float y, int color, int width) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(this.color);
        canvas.drawCircle(this.x, this.y, this.width / 2, paint);
    }
}
