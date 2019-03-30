package com.example.aleks.movies.presentation.screen.movies.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.example.aleks.movies.R
import com.example.aleks.movies.data.models.content.MoviesValue
import com.example.aleks.movies.presentation.screen.movies.fragments.MoviesFilterFragment.Companion.moviesParametersList
import com.example.aleks.movies.presentation.adapter.MoviesAdapter
import com.example.aleks.movies.presentation.general.Preferences
import com.example.aleks.movies.presentation.screen.movies.presenter.MoviesPresenter
import com.example.aleks.movies.presentation.screen.view.Callbacks
import com.example.aleks.movies.presentation.screen.view.MoviesListView


class MoviesListFragment : Fragment(), MoviesListView {

    lateinit var recyclerView: RecyclerView
    lateinit var textViewGenre: TextView
    lateinit var textViewYear: TextView
    lateinit var textViewDirectors: TextView

    var callbacks: Callbacks? = null
    lateinit var arrayGenres: Array<String>
    lateinit var arraYears: Array<String>
    lateinit var arrayDirectors: Array<String>

    lateinit var presenter: MoviesPresenter

    var filteredList: MutableList<MoviesValue> = ArrayList()
    var filterListCriteria: MutableList<String>? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
        filterListCriteria = Preferences.getSavedObjectFromPreference(context, "preference", "objectKey", moviesParametersList.javaClass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.movies_list_fragment, container, false)

        arrayGenres = resources.getStringArray(R.array.genres)
        arraYears = resources.getStringArray(R.array.years)
        arrayDirectors = resources.getStringArray(R.array.directors)

        textViewGenre = view.findViewById(R.id.tv_filter_genre)
        textViewYear = view.findViewById(R.id.tv_filter_year)
        textViewDirectors = view.findViewById(R.id.tv_filter_directors)

        recyclerView = view.findViewById(R.id.rv_movies)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)

        presenter = MoviesPresenter(this)
        presenter.getListValues()

        return view
    }

    override fun setMoviesList(moviesList: MutableList<MoviesValue>) {
        filteredList.clear()

        if(filterListCriteria == null){
            setAdapter(moviesList)
        }
        else {
            filteringByCriteria(filterListCriteria!!)
            movieFiltering(moviesList)

            if(filteredList.size == 0){
                setAdapter(moviesList)
            }
            else {
                setAdapter(filteredList)
            }
        }
    }

    private fun filteringByCriteria(filterListGenre1: MutableList<String>) {
        var genre: String = ""
        var year: String = ""
        var director: String = ""

        for (item in filterListGenre1) {
            val iterGenres = arrayGenres.iterator()
            val iterYears = arraYears.iterator()
            val iterDirectors = arrayDirectors.iterator()
            while (iterGenres.hasNext()) {
                if (item.contains(iterGenres.next())) {
                    genre += item + ","
                }
            }
            while (iterYears.hasNext()) {
                if (item.contains(iterYears.next())) {
                    year += item + ","
                }
            }
            while (iterDirectors.hasNext()) {
                if (item.contains(iterDirectors.next())) {
                    director += item + ","
                }
            }
        }

        if (genre.endsWith(",")) {
            genre = genre.substring(0, genre.length - 1);
        }
        if (year.endsWith(",")) {
            year = year.substring(0, year.length - 1);
        }
        if (director.endsWith(",")) {
            director = director.substring(0, director.length - 1);
        }
            textViewGenre.setText("Genre: $genre")
            textViewYear.setText("Year: $year")
            textViewDirectors.setText("Directors: $director")

    }

    private fun movieFiltering(moviesList: MutableList<MoviesValue>) {
        for (item in moviesList) {
            val iter = moviesParametersList.iterator()
            while (iter.hasNext()) {
                val element = iter.next()
                if (item.year.toString().contains(element) or item.genre!!.contains(element) or item.director!!.contains(element)) {
                    filteredList.add(item);
                }
            }
        }
    }

    private fun setAdapter(moviesList: MutableList<MoviesValue>) {
        val adapter = MoviesAdapter(moviesList)
        recyclerView.adapter = adapter
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun showError() {
        Toast.makeText(activity, resources.getString(R.string.text_failed_to_get_data), Toast.LENGTH_SHORT).show()
    }

    override fun showNetworkError() {
        Toast.makeText(activity, resources.getString(R.string.text_no_internet_connection), Toast.LENGTH_SHORT).show()
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val inflater = inflater
        inflater?.inflate(R.menu.menu_movies, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.movies -> {
                callbacks!!.showSelectedFragment(MoviesFilterFragment.newInstance())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {

        fun newInstance(): MoviesListFragment {
            val fragment = MoviesListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}
