package com.example.afreecaandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecaandroid.databinding.ItemUiDataBinding
import com.example.afreecaandroid.ui.model.UiData

class CookPagingAdapter :
    PagingDataAdapter<UiData, CookPagingAdapter.CookViewHolder>(CookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookPagingAdapter.CookViewHolder {
        return CookViewHolder(
            ItemUiDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CookPagingAdapter.CookViewHolder, position: Int) {
        val talkCamData = getItem(position)
        talkCamData?.let { talkCamData ->
            holder.bind(talkCamData)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(talkCamData) }
            }
        }
    }

    private var onItemClickListener: ((UiData) -> Unit)? = null
    fun setOnItemClickListener(listener: (UiData) -> Unit) {
        onItemClickListener = listener
    }

    class CookViewHolder(
        private val binding: ItemUiDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uiData: UiData) {
            binding.uiData = uiData
        }
    }
}

object CookDiffCallback : DiffUtil.ItemCallback<UiData>() {
    override fun areItemsTheSame(oldItem: UiData, newItem: UiData): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: UiData, newItem: UiData): Boolean {
        return oldItem == newItem
    }
}
