package com.afrakhteh.dogsapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.afrakhteh.dogsapp.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_btn.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}