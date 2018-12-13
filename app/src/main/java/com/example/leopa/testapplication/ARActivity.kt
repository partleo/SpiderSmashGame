package com.example.leopa.testapplication

import android.arch.lifecycle.Observer
import android.content.*
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import java.util.concurrent.CompletableFuture
import android.os.CountDownTimer
import android.os.IBinder
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AlertDialog
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.leopa.testapplication.database.DataBase
import com.example.leopa.testapplication.services.ARGameService
import com.example.leopa.testapplication.services.ShakeService
import com.example.leopa.testapplication.services.StepCounterService
import kotlinx.android.synthetic.main.activity_ar.*

class ARActivity : AppCompatActivity() {

    private var bugRenderable: ModelRenderable? = null
    private var splatRenderable: ModelRenderable? = null
    private lateinit var fragment: ArFragment
    private lateinit var renderableFuture: CompletableFuture<ModelRenderable>
    private val bugUri = Uri.parse("spider.sfb")
    private val splatUri = Uri.parse("slime.sfb")
    private var finished = false
    private var timeLeft: Boolean = true
    private var combo: Int? = null
    private var lastTime: Int? = null
    private var smashed: Boolean = false
    private lateinit var arGameService: ARGameService
    private lateinit var stepCounterService: StepCounterService
    private lateinit var shakeService: ShakeService
    private var arBound = false
    private var stepBound = false
    private var shakeBound = false
    private var viewGroup: ViewGroup? = null
    private var enoughPoints = false
    private var roundPoints = 0
    private var pointsNeeded: Int = 20
    private val gameTime: Long = 30000
    private val interval: Long = 1000
    private var newHighScore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        val arIntent = Intent(this, ARGameService::class.java)
        this.bindService(arIntent, arConnection, Context.BIND_AUTO_CREATE)
        val stepIntent = Intent(this, StepCounterService::class.java)
        this.bindService(stepIntent, stepConnection, Context.BIND_AUTO_CREATE)
        val shakeIntent = Intent(this, ShakeService::class.java)
        this.bindService(shakeIntent, shakeConnection, Context.BIND_AUTO_CREATE)
        createObserver()
        prepareGame()
        points.text = "0"

        spiderWeb.setOnClickListener {
            Toast.makeText(this, "Shake your phone to remove the web", Toast.LENGTH_SHORT).show()
        }
        spiderWeb.visibility = ImageView.INVISIBLE

        gameButton.text = getString(R.string.start_game)
        gameButton.setOnClickListener {
            if (!finished || enoughPoints) { countDown() }
            else {
                savePoints()
                finish()
            }
        }

        gameInfo.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, viewGroup)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_close_game, viewGroup)
        builder.setView(dialogView)
                .setPositiveButton("Yes") { _, _ ->
                    savePoints()
                    super.onBackPressed()
                }
                .setNegativeButton("No") { _, _ ->
                }
                .show()
    }

    private fun prepareGame() {
        fragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment
        fragment.planeDiscoveryController.hide()
        fragment.arSceneView.scene.addOnUpdateListener { _ ->
            fragment.planeDiscoveryController.hide()
        }
    }

    private fun countDown() {
        enoughPoints = false
        countdown.visibility = TextView.VISIBLE
        gameInfo.hide()
        stepCounterService.resetSteps()
        gameButton.isClickable = false
        gameButton.visibility = Button.INVISIBLE
        object : CountDownTimer(3000, interval) {
            override fun onTick(millisUntilFinished: Long) {
                countdown.text = (millisUntilFinished / 1000 + 1).toString()
            }

            override fun onFinish() {
                countdown.visibility = TextView.INVISIBLE
                startGame()
            }
        }.start()
    }

    private fun startGame(){
        timeLeft = true
        finished = false
        object : CountDownTimer(gameTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                time.text = (millisUntilFinished / 1000 + 1).toString()
                combo = (millisUntilFinished/1000).toInt()
            }
            override fun onFinish() {
                time.text = "0"
                timeLeft = false
            }
        }.start()

        attachWeb()


        fragment.arSceneView.scene.addOnUpdateListener { _ ->
            addObject()
        }

        renderableFuture = ModelRenderable.builder()
                .setSource(fragment.context, bugUri)
                .build()
        renderableFuture.thenAccept{ it -> bugRenderable = it }

        renderableFuture = ModelRenderable.builder()
                .setSource(fragment.context, splatUri)
                .build()
        renderableFuture.thenAccept{ it -> splatRenderable = it }
    }

    private fun addObject(){
        val frame = fragment.arSceneView.arFrame
        val hits: List<HitResult>
        if (frame != null && bugRenderable != null && timeLeft){
            hits = frame.hitTest((300+(Math.random()*480)).toFloat(), (720+(Math.random()*480)).toFloat())
            for (hit in hits){
                val trackable = hit.trackable
                if (trackable is Plane){
                    val anchor = hit!!.createAnchor()
                    val anchorNode = AnchorNode(anchor)
                    anchorNode.setParent(fragment.arSceneView.scene)
                    val mNode = TransformableNode(fragment.transformationSystem)
                    mNode.setParent(anchorNode)
                    mNode.renderable = bugRenderable
                    mNode.select()
                    val bug = bugRenderable
                    bugRenderable = null

                    mNode.setOnTapListener { _, _ ->
                        /*
                        if (mNode.renderable == bug && !timeLeft) {
                            anchorNode.removeChild(mNode)
                            restartButton.isClickable = true
                            restartButton.visibility = Button.VISIBLE
                        }
                        */
                        if (mNode.renderable == bug && timeLeft) {
                            mNode.renderable = splatRenderable
                            mNode.select()
                            if (smashed) {
                                val comboCounter = (lastTime!!-combo!!)
                                if (comboCounter<=1) {
                                    roundPoints += 2
                                    points.text = (points.text.toString().toInt() + 2).toString()
                                    Toast.makeText(this, "Combo! +2p", Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    roundPoints += 1
                                    points.text = (points.text.toString().toInt() + 1).toString()
                                    //Toast.makeText(this, "Hit! +1p", Toast.LENGTH_SHORT).show()
                                }
                            }
                            else {
                                roundPoints += 1
                                points.text = (points.text.toString().toInt() + 1).toString()
                                //Toast.makeText(this, "Hit! +1p", Toast.LENGTH_SHORT).show()
                            }
                            if ((points.text.toString()).toInt()>(highScore.text.toString()).toInt()) {
                                val score = points.text.toString().toInt()
                                if (!newHighScore) {
                                    arGameService.replaceHghScore(score)
                                    newHighScore = true
                                }
                                else {
                                    arGameService.saveHighScore(score)
                                }
                                //highScore.text = score.toString()
                            }

                            renderableFuture = ModelRenderable.builder()
                                    .setSource(fragment.context, bugUri)
                                    .build()
                            renderableFuture.thenAccept{ it -> bugRenderable = it }
                        }
                        else {
                            anchorNode.removeChild(mNode)
                            bugRenderable = null
                        }
                        lastTime = combo
                        smashed = true
                    }
                    break
                }
            }
        }
        if (!timeLeft && !finished) {
            Toast.makeText(this, "Time is up!", Toast.LENGTH_SHORT).show()
            when {
                points.text.toString().toInt() == highScore.text.toString().toInt() ->
                    Toast.makeText(this, "New high score! ${points.text} points", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Your score: ${points.text} points", Toast.LENGTH_SHORT).show()
            }
            if (roundPoints >= pointsNeeded) {
                roundPoints = 0
                pointsNeeded += 5
                enoughPoints = true
                gameButton.text = getString(R.string.continue_game)
            }
            else {
                gameButton.text = getString(R.string.end_game)
            }
            finished = true
            gameButton.isClickable = true
            gameButton.visibility = Button.VISIBLE
            stepCounterService.resetSteps()
        }
    }

    private fun attachWeb() {

        var currentTime = 0.toLong()
        var started = false

        fun attaching(time: Long){
            object : CountDownTimer(time, time) {
                override fun onTick(millisUntilFinished: Long) {
                    if (currentTime != 0.toLong()) {
                        if (started) {
                            spiderWeb.visibility = TextView.VISIBLE
                        }
                        started = true
                    }
                }
                override fun onFinish() {
                    currentTime += time
                    val newTime = (Math.random()*8000+3000).toLong()
                    currentTime += newTime
                    if (currentTime < gameTime) {
                        currentTime -= newTime
                        attaching(newTime)
                    }
                }
            }.start()
        }
        attaching(interval)
    }


    private fun savePoints() {
        val finalPoints = points.text.toString().toInt()
        if (finalPoints != highScore.text.toString().toInt()) {
            arGameService.saveScore(finalPoints)
        }
    }




    /*
    private fun restart() {
        finished = false
        timeLeft = true
        combo = null
        lastTime = null
        smashed = false
        points.text = "0"
        renderableFuture = ModelRenderable.builder()
                .setSource(fragment!!.context, bugUri)
                .build()
        renderableFuture!!.thenAccept{ it -> bugRenderable = it }
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                time.text = (millisUntilFinished / 1000).toString()
                combo = (millisUntilFinished/1000).toInt()
            }

            override fun onFinish() {
                time.text = "0"
                timeLeft = false
            }
        }.start()
        restartButton.isClickable = false
        restartButton.visibility = Button.INVISIBLE
    }
    */

    private val arConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ARGameService.ARGameBinder
            arGameService = binder.service
            arBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            arBound = false
        }
    }

    private val stepConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as StepCounterService.StepCounterBinder
            stepCounterService = binder.service
            stepBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            stepBound = false
        }
    }

    private val shakeConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ShakeService.ShakeBinder
            shakeService = binder.service
            shakeBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            shakeBound = false
        }
    }

    private fun createObserver() {
        DataBase.get(this).myDao().getHighScore().observe(this , Observer { it ->
            if (it != null){
                highScore.text = it.score.toString()
            }
            else {
                highScore.text = "0"
            }
        })
        DataBase.get(this).myDao().getShake().observe(this , Observer {
            spiderWeb.visibility = ImageView.INVISIBLE
        })

    }
}

