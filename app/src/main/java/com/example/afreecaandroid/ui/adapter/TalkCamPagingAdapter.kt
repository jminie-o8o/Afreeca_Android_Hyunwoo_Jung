package com.example.afreecaandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecaandroid.databinding.ItemTalkCamBinding
import com.example.afreecaandroid.ui.model.TalkCamData

class TalkCamPagingAdapter :
    PagingDataAdapter<TalkCamData, TalkCamPagingAdapter.TalkCamViewHolder>(TalkCamDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkCamViewHolder {
        return TalkCamViewHolder(
            ItemTalkCamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TalkCamViewHolder, position: Int) {
        val talkCamData = getItem(position)
        talkCamData?.let { talkCamData ->
            holder.bind(talkCamData)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(talkCamData) }
            }
        }
    }

    private var onItemClickListener: ((TalkCamData) -> Unit)? = null
    fun setOnItemClickListener(listener: (TalkCamData) -> Unit) {
        onItemClickListener = listener
    }

    class TalkCamViewHolder(
        private val binding: ItemTalkCamBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(talkCamData: TalkCamData) {
            binding.talkCamData = talkCamData
        }
    }
}

object TalkCamDiffCallback : DiffUtil.ItemCallback<TalkCamData>() {
    override fun areItemsTheSame(oldItem: TalkCamData, newItem: TalkCamData): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: TalkCamData, newItem: TalkCamData): Boolean {
        return oldItem == newItem
    }
}
