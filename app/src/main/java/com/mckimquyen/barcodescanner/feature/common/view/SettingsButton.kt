package com.mckimquyen.barcodescanner.feature.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.mckimquyen.barcodescanner.R
import kotlinx.android.synthetic.main.lo_settings_button.view.*

class SettingsButton : FrameLayout {
    private val view: View

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        view = LayoutInflater
            .from(context)
            .inflate(R.layout.lo_settings_button, this, true)

        context.obtainStyledAttributes(attrs, R.styleable.SettingsButton).apply {
            showText(this)
            showHint(this)
            showSwitch(this)
            recycle()
        }
    }
    
    var hint: String
        get() = view.textViewHint.text.toString()
        set(value) {
            view.textViewHint.apply {
                text = value
                isVisible = text.isNullOrEmpty().not()
            }
        }

    var isChecked: Boolean
        get() = view.switchButton.isChecked
        set(value) { view.switchButton.isChecked = value }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        textViewText.isEnabled = enabled
    }

    fun setCheckedChangedListener(listener: ((Boolean) -> Unit)?) {
        view.switchButton.setOnCheckedChangeListener { _, isChecked ->
            listener?.invoke(isChecked)
        }
    }

    private fun showText(attributes: TypedArray) {
        view.textViewText.text = attributes.getString(R.styleable.SettingsButton_text).orEmpty()
    }

    private fun showHint(attributes: TypedArray) {
        hint = attributes.getString(R.styleable.SettingsButton_hint).orEmpty()
    }

    private fun showSwitch(attributes: TypedArray) {
        view.switchButton.isVisible = attributes.getBoolean(R.styleable.SettingsButton_isSwitchVisible, true)
        if (view.switchButton.isVisible) {
            view.setOnClickListener {
                view.switchButton.toggle()
            }
        }
    }
}