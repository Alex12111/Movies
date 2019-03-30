package com.example.aleks.movies.presentation.screen.movies.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.aleks.movies.R
import com.example.aleks.movies.presentation.general.Preferences
import com.example.aleks.movies.presentation.screen.view.Callbacks
import android.widget.AdapterView
import com.example.aleks.movies.presentation.screen.movies.activity.MoviesActivity


class MoviesFilterFragment: Fragment(), MoviesActivity.OnBackPressedListener {

    var callbacks: Callbacks? = null

    lateinit var listView: ListView
    lateinit var apply: Button
    var moviesArray: Array<String>? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.filter_list_fragment, container, false)

        var genres = resources.getStringArray(R.array.genres)
        var years = resources.getStringArray(R.array.years)
        var directors = resources.getStringArray(R.array.directors)
        moviesArray = genres + years + directors

        apply = view!!.findViewById(R.id.btn_apply) as Button
        listView = view.findViewById(R.id.lv_filter_items) as ListView
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE)

        val adapter = ArrayAdapter<String>(activity, R.layout.filter_item,
                moviesArray)
        listView.setAdapter(adapter)

        if(keysList.size > 0){
            for(item in keysList) {
                listView.setItemChecked(item, true)
            }
        }

        var itemClickListener: AdapterView.OnItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
                val lv = arg0 as ListView
                if (lv.isItemChecked(position)) {
                    if(position == 0){
                         for(i in 0 until moviesArray!!.size) {
                            listView.setItemChecked(i, true)
                         }
                    }
                }else {
                    if(position == 0) {
                        for (i in 0 until moviesArray!!.size) {
                            listView.setItemChecked(i, false)
                        }
                    }
                }
            }
        }

        listView.setOnItemClickListener(itemClickListener);

        apply.setOnClickListener {
            moviesList.clear()
            keysList.clear()

            val sbArray = listView.getCheckedItemPositions()
            for (i in 0 until sbArray.size()) {
                val key = sbArray.keyAt(i)
                if (sbArray.get(key)) {
                    moviesList.add(moviesArray!![key])
                    keysList.add(key)
                }
            }
            Preferences.saveGenreToSharedPreference(context, "preference", "objectKey", moviesList)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            callbacks!!.showSelectedFragment(newInstance())
        }
    }

    override fun onBackPressed() {
        callbacks!!.showSelectedFragment(MoviesListFragment.newInstance())
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val inflater = inflater
        inflater?.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.filter -> {
                for (i in 0 until moviesArray!!.size) {
                    listView.setItemChecked(i, false)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        val moviesList: MutableList<String> = ArrayList()
        val keysList: MutableList<Int> = ArrayList()

        fun newInstance(): MoviesFilterFragment {
            val fragment = MoviesFilterFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}
