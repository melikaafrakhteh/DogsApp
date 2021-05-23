package com.afrakhteh.dogsapp.view.fragments

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.afrakhteh.dogsapp.R
import com.afrakhteh.dogsapp.databinding.FragmentDetailBinding
import com.afrakhteh.dogsapp.databinding.SendSmsBinding
import com.afrakhteh.dogsapp.model.datamodel.DogsModel
import com.afrakhteh.dogsapp.model.datamodel.DogsPalette
import com.afrakhteh.dogsapp.model.datamodel.SmsInfo
import com.afrakhteh.dogsapp.view.activities.MainActivity
import com.afrakhteh.dogsapp.view.interfaces.DogsDetailClickListener
import com.afrakhteh.dogsapp.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class DetailFragment : Fragment(), DogsDetailClickListener {

    private var dogId = 0
    private lateinit var viewModel: DetailViewModel

    private lateinit var dataBinding: FragmentDetailBinding

    private var sendSmsStarted = false

    private var currentDog: DogsModel? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ):
            View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getData()
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogId)

        observeViewModel()
        // setClick()

        dataBinding.listener = this

    }

    private fun observeViewModel() {
        viewModel.detail.observe(
                this,
                Observer { dogs ->

                    currentDog = dogs

                    dogs?.let {
                        /* detail_fragment_name_tv.text = dogs.dogBread
                         detail_fragment_purpose_tv.text = dogs.dogBreadFor
                         detail_fragment_temp_tv.text = dogs.temperament
                         detail_fragment_lifespan_tv.text = dogs.dogLifeSpan
                         detail_fragment_image_iv.load(dogs.imageUrl, getProgressDrawable(this.context!!))*/
                        dataBinding.detail = dogs

                        it.imageUrl?.let { it ->
                            setUpColor(it)
                        }
                    }
                })

    }

    private fun setUpColor(uri: String) {
        Glide
                .with(this)
                .asBitmap()
                .load(uri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Palette
                                .from(resource)
                                .generate { palette ->
                                    val intColor = palette?.darkVibrantSwatch?.rgb ?: 0
                                    val myPalette = DogsPalette(intColor)
                                    dataBinding.palette = myPalette
                                }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
    }

    /* private fun setClick() {
         detail_fragment_back_iv.setOnClickListener {
             val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
             Navigation.findNavController(it).navigate(action)
         }
     }*/

    private fun getData() {
        arguments?.let {
            dogId = DetailFragmentArgs.fromBundle(it).dogId
        }
    }

    override fun back(v: View) {
        val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
        Navigation.findNavController(v).navigate(action)
        //  Toast.makeText(requireContext(), "back", Toast.LENGTH_LONG).show()
    }

    override fun send(v: View) {
        sendSmsStarted = true
        (activity as MainActivity).checkSmsPermission()
        //Toast.makeText(context, "send", Toast.LENGTH_LONG).show()
    }

    override fun share(v: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "CheckOut this Dog Info ")
        intent.putExtra(Intent.EXTRA_TEXT, "${currentDog?.dogBread} bred for ${currentDog?.dogBreadFor}")
        intent.putExtra(Intent.EXTRA_STREAM, currentDog?.imageUrl)
        startActivity(Intent.createChooser(intent, "Share with... "))
    }

    fun onPermissionResult(permissionGranted: Boolean) {
        if (sendSmsStarted && permissionGranted) {
            context?.let {
                val smsInfo = SmsInfo(
                        "",
                        "${currentDog?.dogBread} bred for ${currentDog?.dogBreadFor}",
                        currentDog?.imageUrl
                )

                val databindingDialog = DataBindingUtil.inflate<SendSmsBinding>(
                        LayoutInflater.from(it),
                        R.layout.send_sms, null, false
                )

                AlertDialog.Builder(it)
                        .setView(databindingDialog.root)
                        .setPositiveButton("Send SMS...") { dialog, which ->
                            if (!databindingDialog.smsdestination.text.isNullOrEmpty()) {
                                smsInfo.to = databindingDialog.smsdestination.text.toString()
                                sendingSms(smsInfo)
                            }
                        }
                        .setNegativeButton("Cancle") { dialog, which -> }
                        .show()
                databindingDialog.sms = smsInfo
            }
        }

    }

    private fun sendingSms(smsInfo: SmsInfo) {
        val intent = Intent(context, MainActivity::class.java)
        val pi = PendingIntent.getActivity(context, 0, intent, 0)
        val sms = SmsManager.getDefault().sendTextMessage(smsInfo.to, null, smsInfo.text, pi, null)
    }
}