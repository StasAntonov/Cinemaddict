package com.example.cinemaddict.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<T : ViewDataBinding>(
    private val bindingInflater: (LayoutInflater) -> T
) : Fragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        initViews()
        initObservers()
        initListeners()
    }

    protected open fun initViews() {}
    protected open fun initObservers() {}
    protected open fun initListeners() {}

    protected fun navigate(directions: NavDirections) {
        navController.navigate(directions)
    }

    protected fun goBack() {
        navController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.lifecycleOwner = null
        _binding = null
    }
}