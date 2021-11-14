package jie.wen.desygnertest.ui.component

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import jie.wen.desygnertest.data.SVGRect
import jie.wen.desygnertest.utils.DragTouchListener
import jie.wen.desygnertest.utils.MeasureUtils
import java.lang.Exception

class SVGRectView(element: SVGRect, context: Context) : View(context) {
    init {
        val rect = GradientDrawable()
        rect.shape = GradientDrawable.RECTANGLE
        try {
            rect.setColor(Color.parseColor(element.fill))
        } catch (e: Exception) {

        }
        if (element.height.endsWith("%") && element.width.endsWith("%")) {
            this.layoutParams = ViewGroup.LayoutParams(
                MeasureUtils.getWidthByPercentage(context, element.width, resources),
                MeasureUtils.getHeightByPercentage(context, element.height, resources)
            )
        } else {
            this.layoutParams = ViewGroup.LayoutParams(
                MeasureUtils.pxToDp(element.width.toFloat(), resources).toInt(),
                MeasureUtils.pxToDp(element.height.toFloat(), resources).toInt()
            )
        }
        this.x = MeasureUtils.pxToDp(element.x, resources)
        this.y = MeasureUtils.pxToDp(element.y, resources)
        if (element.stroke.isNotEmpty() && element.strokeDashArray.isNotEmpty()) {
            var color = 0
            try {
                color = Color.parseColor(element.stroke)
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }

            try {
                rect.setStroke(
                    element.strokeWidth.toInt(),
                    color,
                    element.strokeDashArray.split(",")[0].toFloat(),
                    element.strokeDashArray.split(",")[1].toFloat()
                )
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
        this.background = rect
        setOnTouchListener(DragTouchListener)
    }

    companion object {
        private const val TAG = "SVGRectView"
    }
}