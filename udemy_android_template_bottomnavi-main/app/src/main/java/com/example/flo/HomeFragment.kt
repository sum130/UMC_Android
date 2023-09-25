package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        binding.homePannelTodayAlbumIv.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("title", binding.homePannelTodayAlbumTitleTv.text.toString())
            bundle.putString("singer", binding.homePannelTodayAlbumSingerTv.text.toString())
            val albumFragment = AlbumFragment()
            albumFragment.arguments = bundle
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, albumFragment).commitAllowingStateLoss()
        }
        return binding.root
    }
}