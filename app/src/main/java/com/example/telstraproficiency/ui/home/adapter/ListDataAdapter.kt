package com.example.telstraproficiency.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.telstraproficiency.R
import com.example.telstraproficiency.data.model.CountryInfoModelData
import com.example.telstraproficiency.databinding.ItemLayoutBinding
import kotlinx.android.synthetic.main.item_layout.view.*


class ListDataAdapter(private var items : ArrayList<CountryInfoModelData>, private val context: Context) :
    RecyclerView.Adapter<ListDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_layout, parent, false)
        return ViewHolder(binding)
    }
    /**
     * This method for setting list to current list from another class
     */
    fun setList(countryList: ArrayList<CountryInfoModelData>)
    {
        this.items=countryList
        notifyDataSetChanged()
    }
    /**
     * This method for set data to item.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * @return the size of the list.
     */
    override fun getItemCount(): Int {
        return items.size
    }
    /**
     * This class for creating items
     */
    class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CountryInfoModelData) {
            //Loading image
            Glide.with(itemView)
                .load(data.imageHref)
                .placeholder(R.drawable.no_image)
                .fallback(R.drawable.no_image)
                .into(binding.imgImage)
            binding.setVariable(BR.data,data)
            binding.data = data
            binding.executePendingBindings()
        }
    }
}

/*class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val authorName: TextView = view.tvName
    val description: TextView = view.tvDesc
    val imageBlog:ImageView=view.imgImage
}*/

