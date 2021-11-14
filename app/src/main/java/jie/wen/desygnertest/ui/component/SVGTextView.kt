package jie.wen.desygnertest.ui.component

import android.content.Context
import android.graphics.Color
import androidx.appcompat.widget.AppCompatTextView
import jie.wen.desygnertest.data.SVGText
import jie.wen.desygnertest.utils.DragTouchListener
import jie.wen.desygnertest.utils.MeasureUtils
import java.lang.Exception

class SVGTextView(element: SVGText, context: Context) : AppCompatTextView(context) {
    init {
        this.text = element.text
        this.x = MeasureUtils.pxToDp(element.x, resources)
        element.fontSize?.let {
            this.textSize = it
            this.y = MeasureUtils.pxToDp(element.y, resources) - MeasureUtils.pxToDp(it, resources)
        } ?: run {
            this.y = MeasureUtils.pxToDp(element.y, resources)
        }
        try {
            this.setTextColor(Color.parseColor(element.fill))
        } catch (e: Exception) {

        }
        setOnTouchListener(DragTouchListener)
    }
}