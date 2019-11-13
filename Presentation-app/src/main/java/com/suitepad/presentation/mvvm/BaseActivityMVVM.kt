package com.suitepad.presentation.mvvm

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivityMVVM<T : ViewDataBinding, V : BaseViewModel> : BaseActivity() {

    /**********************************************************************************/
    /**
     * Override for set binding variable
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getBindingViewModel(): V

    /**********************************************************************************/

    private lateinit var viewDataBinding: T
    private lateinit var viewModel: V

    /**********************************************************************************/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    fun getViewDataBinding(): T {
        return viewDataBinding
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.viewModel = getBindingViewModel()
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    fun getDataBinding(): T {
        return viewDataBinding
    }

    fun getViewModel(): V {

        return viewModel
    }

}