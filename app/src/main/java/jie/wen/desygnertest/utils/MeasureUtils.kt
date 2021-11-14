package jie.wen.desygnertest.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

object MeasureUtils {
    fun pxToDp(dip: Float, resources: Resources): Float {
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
    }

    fun getWidthByPercentage(context: Context, width: String, resources: Resources): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth =
            displayMetrics.widthPixels / displayMetrics.density * width.replace("%", "").toFloat() / 100f

        return pxToDp(dpWidth, resources).toInt()
    }

    fun getHeightByPercentage(context: Context, height: String, resources: Resources): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpHeight =
            displayMetrics.heightPixels / displayMetrics.density * height.replace("%", "").toFloat() / 100f

        return pxToDp(dpHeight, resources).toInt()
    }
}