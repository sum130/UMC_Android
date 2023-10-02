package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.databinding.FragmentHomePannelBinding

class PannelFragment(val imgRes : Int) : Fragment(){
    lateinit var binding : FragmentHomePannelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePannelBinding.inflate(inflater, container, false)

        binding.homePannelBackgroundIv.setImageResource(imgRes)

        return binding.root
    }
}