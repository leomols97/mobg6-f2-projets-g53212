package com.example.lemenestrel.FragmentAndVMs.Beers

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.lemenestrel.Database.Models.Beers

//import com.example.android.trackmysleepquality.R
//import com.example.android.trackmysleepquality.convertDurationToFormatted
//import com.example.android.trackmysleepquality.convertNumericQualityToString
//import com.example.android.trackmysleepquality.database.Beers


//@BindingAdapter("sleepQualityString")
//fun TextView.setSleepQualityString(item: Beers?) {
//    item?.let {
//        text = convertNumericQualityToString(item.sleepQuality, context.resources)
//    }
//}
//
//
//@BindingAdapter("sleepImage")
//fun ImageView.setSleepImage(item: Beers?) {
//    item?.let {
//        setImageResource(when (item.sleepQuality) {
//            0 -> R.drawable.ic_sleep_0
//            1 -> R.drawable.ic_sleep_1
//            2 -> R.drawable.ic_sleep_2
//
//            3 -> R.drawable.ic_sleep_3
//
//            4 -> R.drawable.ic_sleep_4
//            5 -> R.drawable.ic_sleep_5
//            else -> R.drawable.ic_sleep_active
//        })
//    }
//}