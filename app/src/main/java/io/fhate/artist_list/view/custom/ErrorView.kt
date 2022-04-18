package io.fhate.artist_list.view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.fhate.artist_list.R
import io.fhate.artist_list.databinding.ViewErrorBinding
import io.fhate.core.tools.ext.delayAction
import io.fhate.core.tools.ext.hide
import io.fhate.core.tools.ext.onBlockedClick
import io.fhate.core.tools.ext.show

/* Custom ViewGroup: show errors to user */
class ErrorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class ErrorType(val value: Int) {
        INTERNAL(-1),
        CONNECTION(0),
        NOT_FOUND(1)
    }

    private var clickListener: OnClickListener? = null

    private val binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this, true).also {
        removeAllViews()
        addView(it.root)
    }

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ErrorView)

        binding.apply {
            root.visibility = View.GONE
            val errorType = attributes.getInt(R.styleable.ErrorView_errorType, ErrorType.INTERNAL.value)
            setupWithErrorType(errorType)
            btn.text = attributes.getString(R.styleable.ErrorView_buttonText)?: context.getString(R.string.retry)
            if (attributes.getBoolean(R.styleable.ErrorView_blockedClick, true)) {
                btn.onBlockedClick {
                    clickListener?.onClick(btn)
                }
            } else {
                btn.setOnClickListener {
                    clickListener?.onClick(btn)
                }
            }
        }

        attributes.recycle()
    }

    private fun setupWithErrorType(type: Int) = with(binding) {
        when (type) {
            ErrorType.INTERNAL.value -> {
                tv.text = context.getString(R.string.error_internal)
                iv.setImageResource(R.drawable.undraw_bug_fixing)
            }
            ErrorType.CONNECTION.value -> {
                tv.text = context.getString(R.string.error_no_connection)
                iv.setImageResource(R.drawable.undraw_no_connection)
            }
            ErrorType.NOT_FOUND.value -> {
                tv.text = context.getString(R.string.error_not_found)
                iv.setImageResource(io.fhate.core.R.drawable.undraw_not_found)
            }
        }
    }

    fun setButtonClickListener(clickListener: OnClickListener?) {
        this.clickListener = clickListener
    }

    fun show() {
        binding.root.show()
    }

    fun hide() {
        binding.root.hide()
    }

}