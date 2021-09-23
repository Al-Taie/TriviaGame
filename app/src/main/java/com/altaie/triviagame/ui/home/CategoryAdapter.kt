package com.altaie.triviagame.ui.home

import android.graphics.Color
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altaie.triviagame.R
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.data.response.category.Category
import com.altaie.triviagame.data.response.quiz.Quiz
import com.altaie.triviagame.databinding.CategoryCardItemBinding
import com.altaie.triviagame.ui.interfaces.ItemListener
import java.util.*
import kotlin.random.Random

class CategoryAdapter(private val list: List<Category>, private val listener: ItemListener?=null) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CategoryCardItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = R.layout.category_card_item
        val itemView = LayoutInflater.from(parent.context).inflate(item, parent, false)
        return ViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            with(list[position]) {
                circleLayer.setBackgroundColor(randomColor)
                categoryName.text = name
                categoryImage.setImageResource(imageId)
                categoryCard.setOnClickListener { listener?.onClickItem(name = name) }
                root.setOnClickListener {
                    TrivialRepository.Settings.category = id.toString()
                    TrivialRepository.Settings.categoryName = name
                }
            }
        }
    }

    override fun getItemCount() = list.size

    private val randomColor get()   = Color.rgb(Random.nextInt(108,255), Random.nextInt(20,100), Random.nextInt(105,255))

}