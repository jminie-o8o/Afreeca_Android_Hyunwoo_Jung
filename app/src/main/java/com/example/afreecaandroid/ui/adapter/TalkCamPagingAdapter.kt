package com.example.afreecaandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecaandroid.databinding.ItemUiDataBinding
import com.example.afreecaandroid.ui.model.UiData

class TalkCamPagingAdapter :
    PagingDataAdapter<UiData, TalkCamPagingAdapter.TalkCamViewHolder>(TalkCamDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkCamViewHolder {
        return TalkCamViewHolder(
            ItemUiDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TalkCamViewHolder, position: Int) {
        val uiDataItem = getItem(position)
        uiDataItem?.let { uiData ->
            holder.bind(uiData)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(uiData) }
            }
        }
    }

    private var onItemClickListener: ((UiData) -> Unit)? = null
    fun setOnItemClickListener(listener: (UiData) -> Unit) {
        onItemClickListener = listener
    }

    class TalkCamViewHolder(
        private val binding: ItemUiDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uiData: UiData) {
            binding.uiData = uiData
        }
    }
}

object TalkCamDiffCallback : DiffUtil.ItemCallback<UiData>() {
    override fun areItemsTheSame(oldItem: UiData, newItem: UiData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UiData, newItem: UiData): Boolean {
        return oldItem.userId == newItem.userId
    }
}
