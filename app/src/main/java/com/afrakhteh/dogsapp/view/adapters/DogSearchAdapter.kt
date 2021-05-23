package com.afrakhteh.dogsapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.dogsapp.R
import com.afrakhteh.dogsapp.databinding.ItemDogLayoutBinding
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import com.afrakhteh.dogsapp.view.fragments.SearchFragmentDirections
import com.afrakhteh.dogsapp.view.interfaces.DogsClickListener
import kotlinx.android.synthetic.main.item_dog_layout.view.*

class DogSearchAdapter(private var searchList: List<DogsModel>)
    : RecyclerView.Adapter<DogSearchAdapter.ViewHolder>(), DogsClickListener {

    fun update(update: List<DogsModel>) {
        val dog = searchList.toCollection(ArrayList())
        dog.clear()
        dog.addAll(update)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_dog_layout,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.dogs = searchList[position]
        holder.item.listeners = this
    }


    override fun getItemCount(): Int {
        return searchList.size
    }


    inner class ViewHolder(var item: ItemDogLayoutBinding) : RecyclerView.ViewHolder(item.root)

    override fun onDogCliCk(v: View) {
        val uuid = v.dogIdtv.text.toString().toInt()
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment()
        action.dogId = uuid
        Navigation.findNavController(v).navigate(action)
    }

}

