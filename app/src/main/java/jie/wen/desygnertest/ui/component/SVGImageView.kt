package jie.wen.desygnertest.ui.component

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import jie.wen.desygnertest.data.SVGImage
import jie.wen.desygnertest.utils.DragTouchListener
import jie.wen.desygnertest.utils.MeasureUtils

class SVGImageView(element: SVGImage, context: Context) : AppCompatImageView(context) {
    init {
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
        Glide.with(context)
            .load(element.link)
            .into(this)
        setOnTouchListener(DragTouchListener)
    }
}