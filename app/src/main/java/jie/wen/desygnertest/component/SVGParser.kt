package jie.wen.desygnertest.component

import android.util.Xml
import jie.wen.desygnertest.data.SVGImage
import jie.wen.desygnertest.data.NotSupportElement
import jie.wen.desygnertest.data.SVGRect
import jie.wen.desygnertest.data.SVGText
import jie.wen.desygnertest.utils.ColorUtils
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

private val ns: String? = null

// TODO: just support "rect", "text", "image" which is based on the attached svg file at this moment for simplicity
class SVGParser {
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<*> {
        inputStream.use { it ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setInput(it, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<Any> {
        val entries = mutableListOf<Any>()

        parser.require(XmlPullParser.START_TAG, ns, "svg")

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.END_TAG) {

            } else if (eventType == XmlPullParser.START_TAG) {
                if (parser.name == "rect" || parser.name == "image" || parser.name == "text") {
                    entries.add(readElement(parser))
                }
            }
            eventType = parser.next()
        }

        return entries
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readElement(parser: XmlPullParser): Any {
        return when (parser.name) {
            "rect" -> readRect(parser)
            "image" -> readImage(parser)
            "text" -> readTxt(parser)
            else -> skip(parser)
        }
    }

    // Processes title tags in the feed.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTxt(parser: XmlPullParser): SVGText {
        parser.require(XmlPullParser.START_TAG, ns, "text")
        var fill = ""
        var fontSize: Float? = null
        var x = 0f
        var y= 0f

        for (i in 0 until parser.attributeCount) {
            when (parser.getAttributeName(i)) {
                "fill" -> fill = ColorUtils.padColor(parser.getAttributeValue(i))
                "font-size" -> fontSize = parser.getAttributeValue(i).toFloat()
                "x" -> x = parser.getAttributeValue(i).toFloat()
                "y" -> y = parser.getAttributeValue(i).toFloat()
            }
        }

        val text = readText(parser)

        return SVGText(fill, fontSize, x, y, text)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readImage(parser: XmlPullParser): SVGImage {
        parser.require(XmlPullParser.START_TAG, ns, "image")

        var height = ""
        var width = ""
        var link = ""
        var x = 0f
        var y= 0f

        for (i in 0 until parser.attributeCount) {
            when (parser.getAttributeName(i)) {
                "width" -> width = parser.getAttributeValue(i)
                "height" -> height = parser.getAttributeValue(i)
                "x" -> x = parser.getAttributeValue(i).toFloat()
                "y" -> y = parser.getAttributeValue(i).toFloat()
                "href" -> link = parser.getAttributeValue(i)
            }
        }

        return SVGImage(height, width, x, y, link)
    }

    // Processes summary tags in the feed.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readRect(parser: XmlPullParser): SVGRect {
        parser.require(XmlPullParser.START_TAG, ns, "rect")

        var fill = ""
        var height = ""
        var width = ""
        var x = 0f
        var y= 0f
        var id = ""
        var stroke = ""
        var strokeDashArray = ""
        var strokeWidth = 0f

        for (i in 0 until parser.attributeCount) {
            when (parser.getAttributeName(i)) {
                "width" -> width = parser.getAttributeValue(i)
                "height" -> height = parser.getAttributeValue(i)
                "x" -> x = parser.getAttributeValue(i).toFloat()
                "y" -> y = parser.getAttributeValue(i).toFloat()
                "fill" -> fill = ColorUtils.padColor(parser.getAttributeValue(i))
                "stroke" -> stroke = ColorUtils.padColor(parser.getAttributeValue(i))
                "stroke-dasharray" -> strokeDashArray = parser.getAttributeValue(i)
                "stroke-width" -> strokeWidth = parser.getAttributeValue(i).toFloat()
                "id" -> id = parser.getAttributeValue(i)
            }
        }

        return SVGRect(fill, height, width, x, y, id, stroke, strokeDashArray, strokeWidth)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser): Any {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }

        return NotSupportElement("")
    }
}