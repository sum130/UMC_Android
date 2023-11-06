package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding
import com.example.flo.databinding.ItemLockerMusicBinding

class LockerRVAdapter(private val albumList: ArrayList<LockerMusic>): RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LockerRVAdapter.ViewHolder {
        val binding: ItemLockerMusicBinding = ItemLockerMusicBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        //holder.itemView.setOnClickListener{myItemClickListener.onItemClick()}
        holder.binding.lockerMusicDotBtn.setOnClickListener{myItemClickListener.onRemoveAlbum(position)}
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemLockerMusicBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: LockerMusic){
            binding.itemLockerTitleTv.text = album.title
            binding.itemLockerSingerTv.text = album.singer
            binding.itemLockerMusicImgIv.setImageResource(album.coverImg!!)
        }
    }

}