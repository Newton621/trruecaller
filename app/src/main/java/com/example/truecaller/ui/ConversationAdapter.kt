package com.example.truecaller.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.truecaller.data.entity.Message
import com.example.truecaller.databinding.ItemMessageBubbleBinding
import java.text.SimpleDateFormat
import java.util.*

class ConversationAdapter : ListAdapter<Message, ConversationAdapter.MsgViewHolder>(MsgDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val binding = ItemMessageBubbleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MsgViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MsgViewHolder(private val binding: ItemMessageBubbleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.tvMsgContent.text = message.content
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            binding.tvMsgTime.text = sdf.format(Date(message.timestamp))
        }
    }

    class MsgDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean = oldItem == newItem
    }
}
