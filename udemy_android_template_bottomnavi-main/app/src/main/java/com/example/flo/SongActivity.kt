package com.example.flo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener{
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", binding.songMusicTitleTv.text)
            intent.putExtra("singer", binding.songSingerNameTv.text)
            //startActivity(intent)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.songRepeatIv.setOnClickListener {
            setPlayStatus(false)
        }
        binding.songRandomIv.setOnClickListener {
            setPlayStatus(true)
        }


        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }

        if(intent.hasExtra("title")&& intent.hasExtra("singer")){
            binding.songMusicTitleTv.text= intent.getStringExtra("title")
            binding.songSingerNameTv.text= intent.getStringExtra("singer")
        }
    }
    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    fun setPlayStatus(isPlaying: Boolean){
        if(isPlaying){
            binding.songRepeatIv.visibility = View.VISIBLE
            binding.songRandomIv.visibility = View.GONE
        }
        else{
            binding.songRepeatIv.visibility = View.GONE
            binding.songRandomIv.visibility = View.VISIBLE
        }
    }
}