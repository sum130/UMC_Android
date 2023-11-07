package com.example.flo

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
        fun onPlaySong(position: Int)
    }

    private lateinit var  myItemClickListener: MyItemClickListener
    private var currentSongIndex: Int = 0
    private var mediaPlayer: MediaPlayer?= null
    private var binding: ItemAlbumBinding? = null

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    fun onPlaySong(position: Int){
        albumList.get(position)
        if (position == currentSongIndex) {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
            } else {
                mediaPlayer?.start()
            }
        } else {
            mediaPlayer?.stop()
            mediaPlayer?.release()

            currentSongIndex = position
            val selectedAlbum = albumList[position]

//
//            mediaPlayer = MediaPlayer.create(binding.root.context, selectedAlbum)
//            mediaPlayer?.start()
//
//            binding.mainminiplayertitletv.text = selectedAlbum.title
//            binding.miniPlayerSinger.text = selectedAlbum.singer
//            binding.            miniPlayerProgress.progress = 0
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{ myItemClickListener.onItemClick(albumList[position])}
        //holder.binding.itemAlbumTitleTv.setOnClickListener { myItemClickListener.onRemoveAlbum(position) }
        holder.binding.itemAlbumCoverPlayIv.setOnClickListener { onPlaySong(position) }
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource((album.coverImg!!))
        }
    }
}