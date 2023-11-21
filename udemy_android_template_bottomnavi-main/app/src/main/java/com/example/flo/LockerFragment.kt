package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.concurrent.locks.Lock
import androidx.viewpager2.widget.ViewPager2
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity



class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    //private var albumDatas = ArrayList<LockerMusic>()

    private val information = arrayListOf("저장한 곡", "음악파일", "저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

//        albumDatas.apply {
//            add(LockerMusic("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(LockerMusic("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(LockerMusic("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp2))
//            add(LockerMusic("Boy with luv", "방탄소년단 (BTS)", R.drawable.img_album_exp2))
//            add(LockerMusic("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp2))
//            add(LockerMusic("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp2))
//        }
        //val lockerRVAdapter = LockerRVAdapter(albumDatas)
//        binding.lockerMusicRv.adapter = lockerRVAdapter
//        binding.lockerMusicRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//
//
//        lockerRVAdapter.setMyItemClickListener(object : LockerRVAdapter.MyItemClickListener{
//            override fun onItemClick() {
//                TODO("Not yet implemented")
//            }
//
//            override fun onRemoveAlbum(position: Int) {
//                lockerRVAdapter.removeItem(position)
//            }
//
//        })


        val lockerAdapter = LockerVPAdapter(this)
        binding.locekrContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.locekrContentVp){
                tab, position ->
            tab.text = information[position]
        }.attach()


        binding.loginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }


        
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initViews()
    }

    private fun initViews() {
        val jwt: Int = getJwt()

        if (jwt == 0){
            binding.loginTv.text = "로그인"

            binding.loginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        else{
            binding.loginTv.text = "로그아웃"

            binding.loginTv.setOnClickListener {
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)

        return spf!!.getInt("jwt", 0)
    }

    private fun logout() {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        editor.remove("jwt")
        editor.apply()
    }
}