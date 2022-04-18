package io.fhate.core.ui.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding

/* Base Activity class with DataBinding logic */
abstract class BaseDataBindingActivity<VB: ViewBinding>: BaseActivity() {

    private var _binding: ViewBinding? = null
    abstract val bindingLayout: Int

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, bindingLayout)
        init()
    }

}