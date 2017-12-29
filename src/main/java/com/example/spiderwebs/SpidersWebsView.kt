package com.example.spiderwebs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by jin.wen on 2017/12/26.
 */
class SpidersWebsView : View {

    private var textSize = 20 //文字大小
    private var viewHe = 0 //图形高度
    private var viewWi = 0 //图形宽度
    private var nodeCount = 4  //多边形层数
    private var pathBeans = ArrayList<PathPointBean>()
    private var angle = 0
    private lateinit var mPaint: Paint //画笔

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }


    fun initView() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = Color.GRAY

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        viewHe = h
        viewWi = w
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null && pathBeans.size > 0) {
            //将坐标原点移动至屏幕中心
            canvas.translate(viewWi / 2f, viewHe / 2f)
            drawName(canvas)
            drawSpiderWeb(canvas)
            drawInsideWebs(canvas)
        }
        super.onDraw(canvas)
    }

    /**
     * 画蜘蛛网
     */
    private fun drawSpiderWeb(canvas: Canvas) {
        var path = Path()
        mPaint.style = Paint.Style.STROKE
        for ((i, bean) in pathBeans.withIndex()) {
            path.reset()
            val radius = bean.mMaxValue.toFloat() - i * bean.mMaxValue.toFloat() / nodeCount
            if (radius == 0f) break
            for (j in 0 until pathBeans.size) {
                if (j == 0) {
                    path.moveTo(radius, 0f)
                    canvas.drawLine(0f, 0f, radius, 0f, mPaint)
                } else {
                    var x = radius * Math.cos(angle * j.toDouble())
                    var y = radius * Math.sin(angle * j.toDouble())
                    Log.i("wj", "x:$x  y:$y")
                    canvas.drawLine(0f, 0f, x.toFloat(), y.toFloat(), mPaint)
                    path.lineTo(x.toFloat(), y.toFloat())
                }
            }
            path.close()
            canvas.drawPath(path, mPaint)
        }
    }

    /**
     * 画内部蜘蛛网
     */
    private fun drawInsideWebs(mCanvas: Canvas) {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f
        mPaint.color = Color.RED
        val path = Path()
        pathBeans
                .map { it.mCurValue.toFloat() }
                .forEachIndexed { i, radius ->
                    if (i == 0) {
                        path.moveTo(radius, 0f)
                    } else {
                        var x = radius * Math.cos(angle * i.toDouble())
                        var y = radius * Math.sin(angle * i.toDouble())
                        path.lineTo(x.toFloat(), y.toFloat())
                    }
                }
        path.close()
        mCanvas.drawPath(path, mPaint)
    }

    /**
     * 画文字
     */
    private fun drawName(mCanvas: Canvas) {
        for ((i, bean) in pathBeans.withIndex()) {
            var x = (bean.mMaxValue + 10) * Math.cos(angle * i.toDouble())
            var y = (bean.mMaxValue + 10) * Math.sin(angle * i.toDouble())
            var o = bean.mName.length * textSize
            when {
                x > 0 && y > 0 -> y += textSize
                x < 0 && y > 0 -> {
                    x -= o
                    y += textSize
                }
                x < 0 && y < 0 -> x -= o
            }
            mPaint.textSize = textSize.toFloat()
            mCanvas.drawText(bean.mName, x.toFloat(), y.toFloat(), mPaint)
        }
    }

    /**
     * 设置数据
     */
    fun setData(pathBeans: ArrayList<PathPointBean>) {
        angle = ((Math.PI * 2 / pathBeans.size).toInt())
        this.pathBeans = pathBeans
        invalidate()
    }
}