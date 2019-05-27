package com.furkanisitan.youtubeplaylist.adapters

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furkanisitan.youtubeplaylist.R
import com.furkanisitan.youtubeplaylist.models.Playlist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_playlist.view.*

class PlaylistAdapter(playlistItems: List<Playlist.Items>) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private var playlists = playlistItems

    override fun getItemCount(): Int =
        playlists.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) =
        holder.setData(playlists[position])

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val view = itemView as CardView
        private var tvTitle = view.itemPlaylist_tvTitle
        private var image = view.itemPlaylist_image

        fun setData(playlist: Playlist.Items) {
            tvTitle.text = playlist.snippet?.title
            Picasso.with(view.context).load(playlist.snippet?.thumbnails?.high?.url).into(image)
        }
    }
}