package jie.wen.desygnertest.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

object DragTouchListener: View.OnTouchListener {
    private var x: Float = 0f
    private var y: Float = 0f

    private var mx: Float = 0f
    private var my: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                x = event.x
                y = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                mx = event.x - x
                my = event.y - y

                v?.let { v ->
                    if (mx != 0f && my != 0f) {
                        val l = v.left.plus(mx)
                        val r = v.right.plus(mx)
                        val t = v.top.plus(my)
                        val b = v.bottom.plus(my)

                        v.layout(l.toInt(), t.toInt(), r.toInt(), b.toInt())
                    }
                }
            }
        }

        return true
    }
}