package com.example.leopa.testapplication

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.leopa.testapplication.database.DataBase
import kotlinx.android.synthetic.main.activity_highscore.*

class HighScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)
        createObserver()
    }

    private fun createObserver() {
        DataBase.get(this).myDao().getSavedScore(1).observe(this , Observer { it ->
            if (it != null){
                highscoretable1.text = it.toString()
            }
            else {
                highscoretable1.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(2).observe(this , Observer { it ->
            if (it != null){
                highscoretable2.text = it.toString()
            }
            else {
                highscoretable2.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(3).observe(this , Observer { it ->
            if (it != null){
                highscoretable3.text = it.toString()
            }
            else {
                highscoretable3.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(4).observe(this , Observer { it ->
            if (it != null){
                highscoretable4.text = it.toString()
            }
            else {
                highscoretable4.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(5).observe(this , Observer { it ->
            if (it != null){
                highscoretable5.text = it.toString()
            }
            else {
                highscoretable5.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(6).observe(this , Observer { it ->
            if (it != null){
                highscoretable6.text = it.toString()
            }
            else {
                highscoretable6.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(7).observe(this , Observer { it ->
            if (it != null){
                highscoretable7.text = it.toString()
            }
            else {
                highscoretable7.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(8).observe(this , Observer { it ->
            if (it != null){
                highscoretable8.text = it.toString()
            }
            else {
                highscoretable8.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(9).observe(this , Observer { it ->
            if (it != null){
                highscoretable9.text = it.toString()
            }
            else {
                highscoretable9.text = "0"
            }
        })
        DataBase.get(this).myDao().getSavedScore(10).observe(this , Observer { it ->
            if (it != null){
                highscoretable10.text = it.toString()
            }
            else {
                highscoretable10.text = "0"
            }
        })
    }
}