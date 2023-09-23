package com.example.cinemaddict.ui.details

import android.content.Context
import com.example.cinemaddict.databinding.FragmentDetailsBinding
import com.example.cinemaddict.ui.base.BaseUiFragment

class DetailsFragment : BaseUiFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        hideBar()
    }

    override fun onDetach() {
        showBar()
        super.onDetach()
    }
}