package com.kieling.aptoide.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kieling.aptoide.R
import com.kieling.aptoide.model.AppAptoide

class AppAdapter : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {
    var appList = listOf<AppAptoide>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appImage: ImageView = view.findViewById(R.id.item_image)
        val appName: TextView = view.findViewById(R.id.item_name)
        val appRating: TextView = view.findViewById(R.id.item_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.app_list_item, parent, false)

        return AppViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        Glide.with(holder.appImage.context)
            .load(appList[position].icon)
            .transform(CenterCrop(), RoundedCorners(25))
            .into(holder.appImage)
        holder.appName.text = appList[position].name
        val rating = appList[position].rating
        holder.appRating.text = if (rating == 0F) "- -" else appList[position].rating.toString()
    }

    override fun getItemCount(): Int = appList.size
}