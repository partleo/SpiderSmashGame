package com.example.leopa.testapplication.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.leopa.testapplication.MainActivity
import com.example.leopa.testapplication.database.ARGame
import com.example.leopa.testapplication.database.DataBase
import org.jetbrains.anko.doAsync

class ARGameService : Service() {

    private lateinit var db: DataBase
    private val mBinder = ARGameBinder()
    private val highScoreId: Int = 1
    private lateinit var mainActivity: MainActivity

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        db = DataBase.get(this)
        mainActivity = MainActivity()
    }

    inner class ARGameBinder : Binder() {
        internal// Return this instance of LocalService so clients can call public methods
        val service: ARGameService
            get() = this@ARGameService
    }

    fun saveHighScore(highScore: Int) {
        doAsync {
            db.myDao().insertHighScore(ARGame(highScoreId, highScore))
        }
    }

    fun replaceHghScore(highScore: Int) {
        doAsync {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, db.myDao().comparePoints(8)))
            db.myDao().insertHighScore(ARGame(8, db.myDao().comparePoints(7)))
            db.myDao().insertHighScore(ARGame(7, db.myDao().comparePoints(6)))
            db.myDao().insertHighScore(ARGame(6, db.myDao().comparePoints(5)))
            db.myDao().insertHighScore(ARGame(5, db.myDao().comparePoints(4)))
            db.myDao().insertHighScore(ARGame(4, db.myDao().comparePoints(3)))
            db.myDao().insertHighScore(ARGame(3, db.myDao().comparePoints(2)))
            db.myDao().insertHighScore(ARGame(2, db.myDao().comparePoints(1)))
            db.myDao().insertHighScore(ARGame(highScoreId, highScore))
        }
    }

    fun saveScore(finalScore: Int) {
        doAsync {
            lowestScore(finalScore)
        }
    }

    private fun comparePoints(finalScore: Int) {
        if (db.myDao().getSavedScoreId(2) == 2) {
            if (finalScore > db.myDao().comparePoints(2)) {
                saveNewScore(finalScore)
            }
            else {
                if (db.myDao().getSavedScoreId(3) == 3) {
                    if (finalScore > db.myDao().comparePoints(3)) {
                        saveNewScore(finalScore)
                    }
                    else {
                        if (db.myDao().getSavedScoreId(4) == 4) {
                            if (finalScore > db.myDao().comparePoints(4)) {
                                saveNewScore(finalScore)
                            }
                            else {
                                if (db.myDao().getSavedScoreId(5) == 5) {
                                    if (finalScore > db.myDao().comparePoints(5)) {
                                        saveNewScore(finalScore)
                                    }
                                    else {
                                        if (db.myDao().getSavedScoreId(6) == 6) {
                                            if (finalScore > db.myDao().comparePoints(6)) {
                                                saveNewScore(finalScore)
                                            }
                                            else {
                                                if (db.myDao().getSavedScoreId(7) == 7) {
                                                    if (finalScore > db.myDao().comparePoints(7)) {
                                                        saveNewScore(finalScore)
                                                    }
                                                    else {
                                                        if (db.myDao().getSavedScoreId(8) == 8) {
                                                            if (finalScore > db.myDao().comparePoints(8)) {
                                                                saveNewScore(finalScore)
                                                            }
                                                            else {
                                                                if (db.myDao().getSavedScoreId(9) == 9) {
                                                                    if (finalScore > db.myDao().comparePoints(9)) {
                                                                        saveNewScore(finalScore)
                                                                    }
                                                                    else {
                                                                        db.myDao().insertHighScore(ARGame(10, finalScore))
                                                                    }
                                                                }
                                                                else {
                                                                    db.myDao().insertHighScore(ARGame(9, finalScore))
                                                                }
                                                            }
                                                        }
                                                        else {
                                                            db.myDao().insertHighScore(ARGame(8, finalScore))
                                                        }
                                                    }
                                                }
                                                else {
                                                    db.myDao().insertHighScore(ARGame(7, finalScore))
                                                }
                                            }
                                        }
                                        else {
                                            db.myDao().insertHighScore(ARGame(6, finalScore))
                                        }
                                    }
                                }
                                else {
                                    db.myDao().insertHighScore(ARGame(5, finalScore))
                                }
                            }
                        }
                        else {
                            db.myDao().insertHighScore(ARGame(4, finalScore))
                        }
                    }
                }
                else {
                    db.myDao().insertHighScore(ARGame(3, finalScore))
                }
            }
        }
        else {
            db.myDao().insertHighScore(ARGame(2, finalScore))
        }
    }

    private fun saveNewScore(finalScore: Int) {
        if (finalScore > db.myDao().comparePoints(2)) {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, db.myDao().comparePoints(8)))
            db.myDao().insertHighScore(ARGame(8, db.myDao().comparePoints(7)))
            db.myDao().insertHighScore(ARGame(7, db.myDao().comparePoints(6)))
            db.myDao().insertHighScore(ARGame(6, db.myDao().comparePoints(5)))
            db.myDao().insertHighScore(ARGame(5, db.myDao().comparePoints(4)))
            db.myDao().insertHighScore(ARGame(4, db.myDao().comparePoints(3)))
            db.myDao().insertHighScore(ARGame(3, db.myDao().comparePoints(2)))
            db.myDao().insertHighScore(ARGame(2, finalScore))
        }
        else if (finalScore > db.myDao().comparePoints(3)) {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, db.myDao().comparePoints(8)))
            db.myDao().insertHighScore(ARGame(8, db.myDao().comparePoints(7)))
            db.myDao().insertHighScore(ARGame(7, db.myDao().comparePoints(6)))
            db.myDao().insertHighScore(ARGame(6, db.myDao().comparePoints(5)))
            db.myDao().insertHighScore(ARGame(5, db.myDao().comparePoints(4)))
            db.myDao().insertHighScore(ARGame(4, db.myDao().comparePoints(3)))
            db.myDao().insertHighScore(ARGame(3, finalScore))
        }
        else if (finalScore > db.myDao().comparePoints(4)) {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, db.myDao().comparePoints(8)))
            db.myDao().insertHighScore(ARGame(8, db.myDao().comparePoints(7)))
            db.myDao().insertHighScore(ARGame(7, db.myDao().comparePoints(6)))
            db.myDao().insertHighScore(ARGame(6, db.myDao().comparePoints(5)))
            db.myDao().insertHighScore(ARGame(5, db.myDao().comparePoints(4)))
            db.myDao().insertHighScore(ARGame(4, finalScore))
        }
        else if (finalScore > db.myDao().comparePoints(5)) {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, db.myDao().comparePoints(8)))
            db.myDao().insertHighScore(ARGame(8, db.myDao().comparePoints(7)))
            db.myDao().insertHighScore(ARGame(7, db.myDao().comparePoints(6)))
            db.myDao().insertHighScore(ARGame(6, db.myDao().comparePoints(5)))
            db.myDao().insertHighScore(ARGame(5, finalScore))
        }
        else if (finalScore > db.myDao().comparePoints(6)) {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, db.myDao().comparePoints(8)))
            db.myDao().insertHighScore(ARGame(8, db.myDao().comparePoints(7)))
            db.myDao().insertHighScore(ARGame(7, db.myDao().comparePoints(6)))
            db.myDao().insertHighScore(ARGame(6, finalScore))
        }
        else if (finalScore > db.myDao().comparePoints(7)) {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, db.myDao().comparePoints(8)))
            db.myDao().insertHighScore(ARGame(8, db.myDao().comparePoints(7)))
            db.myDao().insertHighScore(ARGame(7, finalScore))
        }
        else if (finalScore > db.myDao().comparePoints(8)) {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, db.myDao().comparePoints(8)))
            db.myDao().insertHighScore(ARGame(8, finalScore))
        }
        else if (finalScore > db.myDao().comparePoints(9)) {
            db.myDao().insertHighScore(ARGame(10, db.myDao().comparePoints(9)))
            db.myDao().insertHighScore(ARGame(9, finalScore))
        }
        else {
            db.myDao().insertHighScore(ARGame(10, finalScore))
        }
    }

    private fun lowestScore(finalScore: Int) {

        if (db.myDao().getSavedScoreId(10) == 10) {
            if (finalScore > db.myDao().comparePoints(10)) {
                comparePoints(finalScore)
            }
        }
        else {
            comparePoints(finalScore)
        }
    }
}