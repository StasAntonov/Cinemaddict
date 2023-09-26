package com.example.cinemaddict.ui.discover.film

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cinemaddict.R
import com.example.cinemaddict.databinding.FragmentFilmBinding
import com.example.cinemaddict.repository.response.Film
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
            "Детективы" -> test(genre)

            else -> test(" genre - null !!!")
        }
    }

    // todo improve
    private fun test(genre: String): List<Film> {
        val pair = mapOf(
            1 to R.drawable.marv_1,
            2 to R.drawable.img,
            3 to R.drawable.marv_1,
            0 to R.drawable.img
        )
        return buildList {
            (1..20).forEach {
                add(Film("$genre $it", pair.getOrDefault(it % 4, R.drawable.marv_1)))
            }
        }
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