package com.afrakhteh.dogsapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.dogsapp.R
import com.afrakhteh.dogsapp.databinding.ItemDogLayoutBinding
import com.afrakhteh.dogsapp.model.datamodel.DogsModel

class DogsListAdapter(val context: Context, val dogList: ArrayList<DogsModel>)
    : RecyclerView.Adapter<DogsListAdapter.ViewHolder>() {

    fun updateList(updateList: List<DogsModel>) {
        dogList.clear()
        dogList.addAll(updateList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dog_layout, parent, false)
        val view = DataBindingUtil.inflate<ItemDogLayoutBinding>(
                LayoutInflater.from(context), R.layout.item_dog_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   val current = dogList[position]
        // holder.setData(current, position)

        holder.item.dogs = dogList[position]
    }

    override fun getItemCount(): Int = dogList.size

    inner class ViewHolder(var item: ItemDogLayoutBinding) : RecyclerView.ViewHolder(item.root) {

        /*   var name = itemView.findViewById<TextView>(R.id.item_dog_name_tv)
           var lifeSpan = itemView.findViewById<TextView>(R.id.item_dog_lifespan_tv)
           var image = itemView.findViewById<ImageView>(R.id.item_dog_image_iv)
           var card = itemView.findViewById<CardView>(R.id.item_dog_card_crd)

           fun setData(current: DogsModel, position: Int) {
               name.text = current.dogBread
               lifeSpan.text = current.dogLifeSpan
               card.setOnClickListener {
                   val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                   action.dogId = dogList[position].uuid
                   Navigation.findNavController(it).navigate(action)
               }
               image.load(
                       current.imageUrl,
                       getProgressDrawable(context)
               )
           }*/

    }
}