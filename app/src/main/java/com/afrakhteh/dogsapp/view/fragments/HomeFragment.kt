package com.afrakhteh.dogsapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.dogsapp.R
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import com.afrakhteh.dogsapp.view.adapters.DogsListAdapter
import com.afrakhteh.dogsapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private var dogList: ArrayList<DogsModel> = ArrayList()
    private lateinit var dogsListAdapter: DogsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        setupRecycler()
        observeViewModel()
        setupRefreshLayout()

    }

    private fun setupRefreshLayout() {
        home_fragment_refresh_srlout.setOnRefreshListener {
            home_fragment_dogs_recycler.visibility = View.GONE
            home_fragment_error_msg_tv.visibility = View.GONE
            home_fragment_progress.visibility = View.VISIBLE
            viewModel.refreshByPassCache()
            home_fragment_refresh_srlout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.dogs.observe(
                this,
                Observer { dogs ->
                    dogs?.let {
                        home_fragment_dogs_recycler.visibility = View.VISIBLE
                        dogsListAdapter.updateList(dogs)
                    }
                })
        viewModel.dogsLoadingError.observe(
                this,
                Observer { isError ->
                    isError?.let {
                        home_fragment_error_msg_tv.visibility = if (it) View.VISIBLE else View.GONE
                    }
                })
        viewModel.dogsLoading.observe(
                this,
                Observer { isLoading ->
                    isLoading?.let {
                        home_fragment_progress.visibility = if (it) View.VISIBLE else View.GONE
                        if (it) {
                            home_fragment_error_msg_tv.visibility = View.GONE
                            home_fragment_dogs_recycler.visibility = View.GONE
                        }
                    }
                }
        )

    }

    private fun setupRecycler() {
        dogsListAdapter = DogsListAdapter(context!!,dogList)
        home_fragment_dogs_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter
            dogsListAdapter.notifyDataSetChanged()
        }
    }
}