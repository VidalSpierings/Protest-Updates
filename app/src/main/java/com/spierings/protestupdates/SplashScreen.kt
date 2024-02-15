package com.spierings.protestupdates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import kotlin.concurrent.schedule

class SplashScreen: AppCompatActivity() {

    private var splash_image: ImageView? = null

    companion object{

        var fadeInAnimation: Animation? = null
        var fadeOutAnimation: Animation? = null
        var fadeInlistener: Animation.AnimationListener? = null
        var fadeOutlistener: Animation.AnimationListener? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splash_image = findViewById(R.id.splash_image)

        showQuote(applicationContext)


    }


    // <---------- private functions ---------->


    private fun showQuote(context: Context){

        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in_animation)

        initSetOnAnimEndListenerFadeIn(context)

        startAnimWhenLayoutLoaded()

        // fade in image,
        // wait five seconds,
        // fade out image,
        // launch MainActivity when fade out animation finished

    }

    private fun waitThenStartFadeOutAnim(context: Context){

        Timer("Timer", false).schedule(5000) {

            startFadeOutAnimLaunchMainActivity(context)

        }

        // wait five seconds,
        // start image fade out animation,
        // when image fade out finished, launch MainActivity

    }

    private fun startFadeOutAnimLaunchMainActivity(context: Context){

        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out_animation)

        initSetOnAnimEndListenerFadeOut()

        splash_image?.startAnimation(fadeOutAnimation)

    }

    private fun startMainActivity(){

        val intent = Intent(this, MainActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION

        startActivity(intent)

        // start MainActivity with no activity transition animation

    }

    private fun startAnimWhenLayoutLoaded(){

        val allStartTasksLoaded: ViewTreeObserver? = null

        splash_image?.startAnimation(fadeInAnimation)

        allStartTasksLoaded?.addOnGlobalLayoutListener {

        }

    }

    private fun initSetOnAnimEndListenerFadeIn(context: Context){

        fadeInlistener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

                waitThenStartFadeOutAnim(context)

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        }

        fadeInAnimation?.setAnimationListener(fadeInlistener)

    }

    private fun initSetOnAnimEndListenerFadeOut(){

        fadeOutlistener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

                splash_image?.visibility = View.INVISIBLE

                startMainActivity()

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        }

        fadeOutAnimation?.setAnimationListener(fadeOutlistener)

    }

}