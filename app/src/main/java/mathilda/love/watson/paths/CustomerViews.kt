package mathilda.love.watson.paths

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.lang.reflect.Array.getLength


/**
 * Created by watson on 1/24 0024.
 */


class PathView : View {

    private var cx: Float = 0f
    private var cy: Float = 0f
    private lateinit var paint: Paint
    private lateinit var dotPaint: Paint
    private lateinit var cornerEffect: PathEffect
    private var sides = 3
    private var progress: Float = 0f

    private val paths: ArrayList<Polygon> = ArrayList()
    private val effects: ArrayList<DashPathEffect> = ArrayList()
    private val lengths: ArrayList<Float> = ArrayList()
    private val phase: ArrayList<Float> = ArrayList()

    constructor(context: Context) : this(context, null) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }
        val height = canvas.height
        val width = canvas.width
        cx = width / 2.0f
        cy = height * 1.0f / 2
//        path = createPath(sides, 200f)
// Measure the path

// Apply the dash effect
//        val effect = DashPathEffect(floatArrayOf(length, length), 0.0f)

        paths.forEachIndexed { index, pic ->

            if (showBG) {
                paint.pathEffect = effects[index]
                canvas.drawPath(pic.path, paint)
            }

            val phase = pic.initialPhase + progress * pic.length * pic.laps / 100
            dotPaint.pathEffect = PathDashPathEffect(pathDot, lengths[index], phase, PathDashPathEffect.Style.TRANSLATE)
            dotPaint.color = pic.color
            canvas.drawPath(pic.path, dotPaint)
        }


    }

    var dotProgress = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
//            callback.invalidateDrawable(this)
        }

    private val pathDot = Path().apply {
        addCircle(0f, 0f, 8f, Path.Direction.CW)
    }

    private fun initView() {
        paint = Paint()
        // 去锯齿
        paint.isAntiAlias = true
        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f

        dotPaint = Paint()
        dotPaint.style = Paint.Style.FILL
        dotPaint.strokeWidth = 8f

        cornerEffect = CornerPathEffect(8f)

        paths.add(Polygon(15, 0xffe84c65.toInt(), 362f, 2))
        paths.add(Polygon(14, 0xffe84c65.toInt(), 338f, 3))
        paths.add(Polygon(13, 0xffd554d9.toInt(), 314f, 4))
        paths.add(Polygon(12, 0xffaf6eee.toInt(), 292f, 5))
        paths.add(Polygon(11, 0xff4a4ae6.toInt(), 268f, 6))
        paths.add(Polygon(10, 0xff4294e7.toInt(), 244f, 7))
        paths.add(Polygon(9, 0xff6beeee.toInt(), 220f, 8))
        paths.add(Polygon(8, 0xff42e794.toInt(), 196f, 9))
        paths.add(Polygon(7, 0xff5ae75a.toInt(), 172f, 10))
        paths.add(Polygon(6, 0xffade76b.toInt(), 148f, 11))
        paths.add(Polygon(5, 0xffefefbb.toInt(), 128f, 12))
        paths.add(Polygon(4, 0xffe79442.toInt(), 106f, 13))
        paths.add(Polygon(3, 0xffe84c65.toInt(), 90f, 14))

        paths.forEach { polygon ->
            val measure = PathMeasure(polygon.path, false)
            val length = measure.length
            val effect = DashPathEffect(floatArrayOf(length, length), 0f)
            effects.add(effect)
            lengths.add(length)
        }

    }


    fun setSides(p1: Int) {
        if (p1 >= 3) {
            this.sides = p1
            invalidate()
        }
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    companion object {
        private const val width = 1080
        private const val height = 1080
        private const val cx = (width / 2).toFloat()
        private const val cy = (height / 2).toFloat()
        public val pathMeasure = PathMeasure()
    }

    private var showBG = false
    fun setShowBG(show: Boolean) {
        showBG = show
    }

}

fun createPath(sides: Int, radius: Float): Path {
    val path = Path()
    val angle = 2.0 * Math.PI / sides
    path.moveTo(
            540 + (radius * Math.cos(0.0)).toFloat(),
            540 + (radius * Math.sin(0.0)).toFloat())
    for (i in 1 until sides) {
        path.lineTo(
                540 + (radius * Math.cos(angle * i)).toFloat(),
                540 + (radius * Math.sin(angle * i)).toFloat())
    }
    path.close()
    return path
}

private class Polygon(val sides: Int, val color: Int, radius: Float, val laps: Int) {
    val path = createPath(sides, radius)
    val length by lazy(LazyThreadSafetyMode.NONE) {
        PolygonLapsDrawable.pathMeasure.setPath(path, false)
        PolygonLapsDrawable.pathMeasure.length
    }
    val initialPhase by lazy(LazyThreadSafetyMode.NONE) {
        (1f - (1f / (2 * sides))) * length
    }

    private fun createPath(sides: Int, radius: Float): Path {
        val path = Path()
        val angle = 2.0 * Math.PI / sides
        val startAngle = Math.PI / 2.0 + Math.toRadians(360.0 / (2 * sides))
        path.moveTo(
                PolygonLapsDrawable.cx + (radius * Math.cos(startAngle)).toFloat(),
                PolygonLapsDrawable.cy + (radius * Math.sin(startAngle)).toFloat())
        for (i in 1 until sides) {
            path.lineTo(
                    PolygonLapsDrawable.cx + (radius * Math.cos(startAngle - angle * i)).toFloat(),
                    PolygonLapsDrawable.cy + (radius * Math.sin(startAngle - angle * i)).toFloat())
        }
        path.close()
        return path
    }
}