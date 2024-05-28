package com.mckimquyen.barcodescanner.feature.common.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.mckimquyen.barcodescanner.R
import kotlinx.android.synthetic.main.lo_icon_button.view.*


class IconButton : FrameLayout {
    private val view: View

    var text: String
        get() = view.textView.text.toString()
        set(value) { view.textView.text = value }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        view = LayoutInflater
            .from(context)
            .inflate(R.layout.lo_icon_button, this, true)

        context.obtainStyledAttributes(attrs, R.styleable.IconButton).apply {
            showIcon(this)
            showIconBackgroundColor(this)
            showText(this)
            recycle()
        }
    }

    private fun showIcon(attributes: TypedArray) {
        val iconResId = attributes.getResourceId(R.styleable.IconButton_icon, -1)
        val icon = AppCompatResources.getDrawable(context, iconResId)
        view.imageViewSchema.setImageDrawable(icon)
    }

    private fun showIconBackgroundColor(attributes: TypedArray) {
        val color = attributes.getColor(
            R.styleable.IconButton_iconBackground,
            ContextCompat.getColor(view.context, R.color.green)
        )
        (view.layoutImage.background.mutate() as GradientDrawable).setColor(color)
    }

    private fun showText(attributes: TypedArray) {
        view.textView.text = attributes.getString(R.styleable.IconButton_text).orEmpty()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        view.imageViewSchema.isEnabled = enabled
        view.textView.isEnabled = enabled
    }
}