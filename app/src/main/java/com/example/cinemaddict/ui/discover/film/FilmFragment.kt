package com.example.cinemaddict.ui.discover.film

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cinemaddict.R
import com.example.cinemaddict.databinding.FragmentFilmBinding
import com.example.cinemaddict.network.model.Film
import com.example.cinemaddict.ui.base.BaseFragment
import com.example.cinemaddict.ui.discover.FilmAdapter

class FilmFragment : BaseFragment<FragmentFilmBinding>(FragmentFilmBinding::inflate) {
    private var genre: String? = null
    override fun initViews() {
        super.initViews()
        genre = arguments?.getString(ARG_GENRE)

        binding.rvList.apply {
            genre?.let {
                isNestedScrollingEnabled = false
                setHasFixedSize(false)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = FilmAdapter(getData(it))
            }
        }
    }

    // todo improve
    private fun getData(genre: String): List<Film> {

        return when (genre) {
            "Комедия",
            "Драма-триллер",
            "Экшн",
            "Ужасы",
            "Детективы" -> getList(genre)

            else -> getList(" genre - null !!!")
        }
    }

    // todo improve
    private fun getList(genre: String): List<Film> {
        return listOf(
            Film("$genre 1", R.drawable.img),
            Film("$genre 2", R.drawable.marv_1),
            Film("$genre 4", R.drawable.marv_1),
            Film("$genre 3", R.drawable.img),
            Film("$genre 5", R.drawable.img),
            Film("$genre 5", R.drawable.marv_1)
        )
    }

    companion object {
        private const val ARG_GENRE = "genre"
        fun newInstance(genre: String): FilmFragment {
            val fragment = FilmFragment()
            val args = Bundle()
            args.putString(ARG_GENRE, genre)
            fragment.arguments = args
            return fragment
        }
    }

}