package com.example.truecaller.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.truecaller.data.entity.Message
import com.example.truecaller.databinding.ItemMessageThreadBinding
import java.text.SimpleDateFormat
import java.util.*

class MessageThreadAdapter(private val onThreadClick: (Message) -> Unit) : ListAdapter<Message, MessageThreadAdapter.ThreadViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadViewHolder {
        val binding = ItemMessageThreadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThreadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThreadViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ThreadViewHolder(private val binding: ItemMessageThreadBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.textSender.text = message.sender
            binding.textLastMessage.text = message.content
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            binding.textTime.text = sdf.format(Date(message.timestamp))
            
            if (message.sender == "MPESA") {
                binding.imageSender.setImageResource(android.R.drawable.ic_dialog_info)
            } else {
                binding.imageSender.setImageResource(android.R.drawable.ic_menu_send)
            }
            
            binding.root.setOnClickListener { onThreadClick(message) }
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean = oldItem == newItem
    }
}
