package com.spierings.protestupdates.Animations

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.spierings.protestupdates.R

class Animations: AnimationsInterface {

    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null


    override fun fadeInView(context: Context, view: View){

        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in_animation)

        view.startAnimation(fadeIn)

    }

    override fun fadeOutView(context: Context, view: View, fadeInOutTransition: Boolean, fadeInView: View){

        fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out_animation)

        view.startAnimation(fadeOut)

    }

}