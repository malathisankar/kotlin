package com.example.kotlin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompatSideChannelService
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit  var mPager: ViewPager
    var layouts: IntArray = intArrayOf(R.layout.first_slide,R.layout.second_slide)
     lateinit var  dotslayouts: LinearLayout
     lateinit var  dots: Array<ImageView>
     lateinit var  mAdapter: PageAdapter
     lateinit var  btnskip: Button
     lateinit var  btn1getstarted: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(PrefManager(this).checkPreference())
        {
            loadHome()
        }
        if(Build.VERSION.SDK_INT >= 19)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        }
        else
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        setContentView(R.layout.activity_main)
        mPager = findViewById(R.id.pager) as ViewPager
        mAdapter = PageAdapter(layouts,this)
        mPager.adapter = mAdapter
        dotslayouts = findViewById(R.id.dots) as LinearLayout
        btnskip = findViewById(R.id.btn_skip) as Button
        btn1getstarted = findViewById(R.id.btn1_getstarted) as Button
        btnskip.setOnClickListener(this)
        btn1getstarted.setOnClickListener(this)
        createDots(0)
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                createDots(position)

                if (position == layouts.size -1)
                {
                    btn1getstarted.setText("Start")
                    btnskip.visibility = View.INVISIBLE
                }
                else
                {
                    btn1getstarted.setText("Next")
                    btnskip.visibility = View. VISIBLE
                }

            }
        })

    }
    override fun onClick(p0: View?) {
        when(p0!!.id)
        {
            R.id.btn_skip ->
            {
                loadHome()
                PrefManager(this).writeSP()
            }
            R.id.btn1_getstarted ->
            {
                loadNextSlide()
            }
        }

    }

    private fun loadNextSlide() {
        var nextSlide: Int = mPager.currentItem + 1
        if (nextSlide < layouts.size)
        {
            mPager.setCurrentItem(nextSlide)
        }
        else
        {
            loadHome()
            PrefManager(this).writeSP()
        }
    }

    private fun loadHome() {
        startActivity(Intent(this,MainActivity2::class.java))
        finish()
    }

    fun createDots(position: Int)
    {
        if (dotslayouts!=null)
        {
            dotslayouts.removeAllViews()
        }
        dots  = Array(layouts.size,{i -> ImageView(this) })
        for (i in 0..layouts.size - 1)
        {
            dots[i] = ImageView(this)
            if (i == position)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots))

            }
            else
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots))
            }
            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(4,0,4,0)
            dotslayouts.addView(dots[i],params)
        }
    }


}