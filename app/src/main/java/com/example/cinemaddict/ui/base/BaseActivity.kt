package com.example.cinemaddict.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(
    private val bindingInflater: (LayoutInflater) -> T
) : AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObservers()
        iniListeners()
    }

    @CallSuper
    open fun initViews() {}

    @CallSuper
    open fun iniListeners() {}

    @CallSuper
    open fun initObservers() {}
}