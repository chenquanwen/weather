package com.example.a0720.UI.Search

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.a0720.R


class textView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    private var iconDrawble: Drawable? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.MyEditText, 0, 0).apply {
            try{
                val iconId = getResourceId(R.styleable.MyEditText_clearIcon, 0)
                if(iconId != 0)
                    iconDrawble = ContextCompat.getDrawable(context, iconId)
            }finally {
                recycle()
            }
        }
    }
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        clear()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { motionEvent ->
            iconDrawble?.let {
                if (motionEvent.action == MotionEvent.ACTION_UP
                    && motionEvent.x > width - it.intrinsicWidth - 20
                    && motionEvent.x < width + 20
                    && motionEvent.y > height / 2 - it.intrinsicHeight / 2 - 20
                    && motionEvent.y < height / 2 + 20
                ){
                    text?.clear()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        clear()
    }

    private fun clear(){
        val icon = if(isFocused && text?.isNotEmpty() == true ) iconDrawble else null
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
    }
}