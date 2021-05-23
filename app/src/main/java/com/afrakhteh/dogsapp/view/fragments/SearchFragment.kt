package com.afrakhteh.dogsapp.view.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.dogsapp.R
import com.afrakhteh.dogsapp.model.database.DogsDatabase
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import com.afrakhteh.dogsapp.view.adapters.DogSearchAdapter
import com.afrakhteh.dogsapp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var dogSearchAdapter: DogSearchAdapter
    private var listSearch: ArrayList<DogsModel> = ArrayList()
    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelIcon = search_item.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(R.color.darkGreen)

        viewModel.searchDataInDB()

        observeViewModel()

        setupSearch()

        dogSearchAdapter = DogSearchAdapter(listSearch)
    }

    private fun setupSearch() {
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        search_item.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            isSubmitButtonEnabled = true
        }

        search_item.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                setUpRecycler(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    setUpRecycler(newText)
                    //      view?.findNavController()?.navigate(R.id.searchFragment)
                    //     dogSearchAdapter.notifyDataSetChanged()
                }
                return true
            }
        })

    }

    private fun observeViewModel() {
        viewModel.dogs.observe(this,
                Observer { dogs ->
                    dogs?.let {
                        search_recycler.visibility = View.VISIBLE
                        dogSearchAdapter.update(dogs)
                    }
                })
        viewModel.searchLoading.observe(this,
                Observer { loading ->
                    loading?.let {
                        search_progressBar.visibility = if (it) View.VISIBLE else View.GONE
                        if (it) {
                            search_recycler.visibility = View.GONE
                        }
                    }
                })
    }

    private fun setUpRecycler(title: String?) {
        search_recycler.layoutManager = LinearLayoutManager(context)
        search_recycler.hasFixedSize()

        val searchQuery = "%$title%"
        context?.let {
            DogsDatabase.invoke(it).getDao().getSearch(searchQuery).observe(this, object : Observer<List<DogsModel>> {
                override fun onChanged(t: List<DogsModel>?) {
                    val test = t?.toCollection(ArrayList()) ?: return
                    dogSearchAdapter = DogSearchAdapter(test)
                    search_recycler.adapter = dogSearchAdapter
                }

            })
        }

    }

}


