package com.example.flo

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer : Timer
    private  var mediaPlayer: MediaPlayer?=null
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

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
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
        }


        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
            timer.stopTimer()

            timer = Timer(song.playTime, song.isPlaying)
            timer.start()

            setPlayerStatus(true)
        }
    }

    //사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause(){
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //에디터
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)
        editor.apply()
    }

    override fun onDestroy(){
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어플레이어가 갖고있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
    }

    private fun initSong(){
        if(intent.hasExtra("title")&& intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!

            )
        }
        startTimer()
    }

    private fun setPlayer(song:Song){
        binding.songMusicTitleTv.text= intent.getStringExtra("title")!!
        binding.songSingerNameTv.text= intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second /60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime /60, song.playTime%60)
        binding.songProgressSb.progress = (song.second*100/song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying==true){
                mediaPlayer?.pause()
            }
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




    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime : Int, var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        fun stopTimer(){
            isPlaying = false
            interrupt()
        }

        override fun run(){
            super.run()
            try{
                while(second<playTime){
                    if(isPlaying){
                        Thread.sleep(1000)
                        mills += 1000
                        runOnUiThread(){
                            binding.songProgressSb.progress = ((mills /playTime)*100).toInt()
                            binding.songStartTimeTv.text =
                                String.format("%02d:%02d", second / 60, second % 60)
                            binding.songEndTimeTv.text =
                                String.format("%02d:%02d", (playTime - second) / 60, (playTime - second) % 60)
                        }
                        second++
                    }
                }
//                while(true){
//                    if(second >=playTime){
//                        break
//                    }
//                    if(isPlaying){
//                        sleep(50)
//                        mills += 50
//
//                        runOnUiThread(){
//                            binding.songProgressSb.progress = ((mills /playTime)*100).toInt()
//                        }
//                        if(mills % 1000==0f){
//                            runOnUiThread{
//                                binding.songStartTimeTv.text = String.format("%02d:%02d", second /60, second%60)
//                            }
//                            second++
//                        }
//                    }
//                }

            }catch(e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")

            }

        }
    }
}