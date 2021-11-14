package jie.wen.desygnertest.utils

import java.lang.StringBuilder

object ColorUtils {
    fun padColor(colorCode: String): String {
        if (colorCode.length < 7) {
            val stringBuilder = StringBuilder()

            val pad = 6 - colorCode.length + 1

            for (i in 0 until pad) {
                stringBuilder.insert(0, colorCode[1])
            }

            stringBuilder.insert(0, "#")

            return stringBuilder.append(colorCode.substring(1)).toString()
        }

        return colorCode
    }
}