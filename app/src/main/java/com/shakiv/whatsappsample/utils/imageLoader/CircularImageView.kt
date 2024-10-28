package com.shakiv.whatsappsample.utils.imageLoader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircularImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private var bitmap: Bitmap? = null
    private var radius: Float = 0f

    override fun onDraw(canvas: Canvas) {
        bitmap?.let {
            if (radius == 0f) {
                radius = width.coerceAtMost(height) / 2f
            }
            canvas.drawCircle(width / 2f, height / 2f, radius, paint)
        }
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        bitmap = bm
        bitmap?.let {
            val shader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.shader = shader
            invalidate()
        }
    }
}
