package com.example.truecaller.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.truecaller.data.entity.CallLog
import com.example.truecaller.databinding.ItemCallLogBinding
import java.text.SimpleDateFormat
import java.util.*

class CallLogAdapter : ListAdapter<CallLog, CallLogAdapter.CallLogViewHolder>(CallLogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        val binding = ItemCallLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CallLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CallLogViewHolder(private val binding: ItemCallLogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(callLog: CallLog) {
            binding.textNameOrNumber.text = callLog.phoneNumber
            val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
            binding.textTimestamp.text = sdf.format(Date(callLog.timestamp))
            
            val icon = when (callLog.type) {
                1 -> android.R.drawable.sym_call_incoming
                2 -> android.R.drawable.sym_call_outgoing
                else -> android.R.drawable.stat_notify_missed_call
            }
            binding.imageCallType.setImageResource(icon)
        }
    }

    class CallLogDiffCallback : DiffUtil.ItemCallback<CallLog>() {
        override fun areItemsTheSame(oldItem: CallLog, newItem: CallLog): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CallLog, newItem: CallLog): Boolean = oldItem == newItem
    }
}
