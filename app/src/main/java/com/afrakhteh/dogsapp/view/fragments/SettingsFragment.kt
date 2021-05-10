package com.afrakhteh.dogsapp.view.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.afrakhteh.dogsapp.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefrence, rootKey)
    }

}