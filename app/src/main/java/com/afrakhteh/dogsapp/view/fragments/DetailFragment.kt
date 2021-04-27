package com.afrakhteh.dogsapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.afrakhteh.dogsapp.R
import com.afrakhteh.dogsapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private var dogId = 0
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch()

        getData()
        observeViewModel()
        setClick()
    }

    private fun observeViewModel() {
        viewModel.detail.observe(
                this,
                Observer { dogs ->
                    dogs?.let {
                        detail_fragment_name_tv.text = dogs.dogBread
                        detail_fragment_purpose_tv.text = dogs.dogBreadFor
                        detail_fragment_temp_tv.text = dogs.temperament
                        detail_fragment_lifespan_tv.text = dogs.dogLifeSpan
                        //  detail_fragment_image_iv.setImageResource(dogs.imageUrl)
                    }
                })
    }

    private fun setClick() {
        detail_fragment_back_iv.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun getData() {
        arguments?.let {
            dogId = DetailFragmentArgs.fromBundle(it).dogId
        }
    }
}