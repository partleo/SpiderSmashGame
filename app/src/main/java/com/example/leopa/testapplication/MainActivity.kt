package com.example.leopa.testapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.view.View
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.main_content.*
import android.content.Intent
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.leopa.testapplication.services.StepCounterService
import android.arch.lifecycle.Observer
import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import com.example.leopa.testapplication.database.DataBase

class MainActivity : AppCompatActivity() {

    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var stepCounterService: StepCounterService
    private var mBound = false
    private var numSteps = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, StepCounterService::class.java)
        this.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        createObserver()

        sheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottom_sheet)
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED ->{
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Spider Smash"
        toolbar.setTitleTextColor(Color.parseColor(COLOR_WHITE))

        toolbar.inflateMenu(R.menu.menu)
        toolbar.setOnMenuItemClickListener {
            expandCloseSheet()
            true
        }

        menu.setOnClickListener {
            expandCloseSheet()
        }

        play_game.setOnClickListener{
            if (tv_steps.text == TEXT_DONE){
                val vrIntent = Intent(this, ARActivity::class.java)
                startActivity(vrIntent)
            }
            else {
                Toast.makeText(this, TEXT_STEPS_NOT_DONE, Toast.LENGTH_SHORT).show()
            }
        }

        highscores.setOnClickListener{
            val vrIntent = Intent(this, HighScoreActivity::class.java)
            startActivity(vrIntent)
        }

        progress_bar.setOnClickListener {
            if (progress_bar.progress >= progress_bar.max) {
                Toast.makeText(this, R.string.progress_bar_full, Toast.LENGTH_SHORT).show()

            }
            else {
                Toast.makeText(this, R.string.progress_bar, Toast.LENGTH_SHORT).show()

            }
        }

        if (tv_steps.text != TEXT_DONE){
            play_game.alpha = 0.5F
        }

        step_amount.setOnClickListener { view ->
            chooseStepAmount(view)
        }


    }

    override fun onRestart() {
        super.onRestart()
        if (tv_steps.text != TEXT_DONE){
            play_game.alpha = 0.5F
        }
    }


    private fun expandCloseSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as StepCounterService.StepCounterBinder
            stepCounterService = binder.service
            mBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    private fun createObserver() {
        DataBase.get(this).myDao().getSteps().observe(this , Observer { it ->
            if (it != null){
                numSteps = it.steps
                progress_bar.progress = numSteps

                if (progress_bar.progress >= progress_bar.max) {
                    tv_steps.text = TEXT_DONE
                    play_game.alpha = 1F
                }
                else {
                    val steps = TEXT_NUM_STEPS + numSteps
                    tv_steps.text = steps
                }
            }
        })
    }

    private fun chooseStepAmount(view: View) {
        val popup: PopupMenu?
        popup = PopupMenu(this, view)
        popup.inflate(R.menu.step_amount)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.steps20 -> {
                    progress_bar.max = 20
                    tv_target_steps.text = TARGET_1
                }
                R.id.steps50 -> {
                    progress_bar.max = 50
                    tv_target_steps.text = TARGET_2
                }
                R.id.steps100 -> {
                    progress_bar.max = 100
                    tv_target_steps.text = TARGET_3
                }
                R.id.steps200 -> {
                    progress_bar.max = 200
                    tv_target_steps.text = TARGET_4
                }
                R.id.steps500 -> {
                    progress_bar.max = 500
                    tv_target_steps.text = TARGET_5
                }
                R.id.steps1000 -> {
                    progress_bar.max = 1000
                    tv_target_steps.text = TARGET_6
                }
            }
            if (progress_bar.progress >= progress_bar.max) {
                tv_steps.text = TEXT_DONE
                play_game.alpha = 1F
            }
            else {
                val steps = TEXT_NUM_STEPS + numSteps
                progress_bar.progress = numSteps
                tv_steps.text = steps
                play_game.alpha = 0.5F
            }
            true
        })

        popup.show()
    }

    companion object {
        private const val TEXT_NUM_STEPS = "Steps: "
        private const val TEXT_DONE = "Done!"
        private const val TEXT_STEPS_NOT_DONE = "Not enough steps to play"
        private const val COLOR_WHITE = "#FFFFFF"
        private const val TARGET_1 = "Target steps: 20"
        private const val TARGET_2 = "Target steps: 50"
        private const val TARGET_3 = "Target steps: 100"
        private const val TARGET_4 = "Target steps: 200"
        private const val TARGET_5 = "Target steps: 500"
        private const val TARGET_6 = "Target steps: 1000"
    }



/*/6767---->



    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:$packageName"))
                startActivity(intent)
                finish()
            }
        }
    }

6767*/



}