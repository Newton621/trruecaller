package com.example.truecaller.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truecaller.databinding.FragmentMessagesBinding
import com.example.truecaller.ui.MessageThreadAdapter
import com.example.truecaller.ui.viewmodel.MessageViewModel

class MessagesFragment : Fragment() {
    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MessageViewModel
    private lateinit var adapter: MessageThreadAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        adapter = MessageThreadAdapter { message ->
            val intent = android.content.Intent(requireContext(), com.example.truecaller.ui.ConversationActivity::class.java).apply {
                putExtra("THREAD_ID", message.threadId)
            }
            startActivity(intent)
        }
        binding.recyclerViewMessages.adapter = adapter
        
        viewModel = androidx.lifecycle.ViewModelProvider(this).get(MessageViewModel::class.java)
        viewModel.allMessages.observe(viewLifecycleOwner) { messages ->
            // Group messages by threadId and show latest
            val latestMessages = messages.groupBy { it.threadId }.map { it.value.last() }
            adapter.submitList(latestMessages)
        }

        binding.fabNewMessage.setOnClickListener {
            // New message logic
        }

        val toolbar = requireActivity().findViewById<com.google.android.material.appbar.MaterialToolbar>(com.example.truecaller.R.id.toolbar)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == com.example.truecaller.R.id.action_simulate_mpesa) {
                showSimulateMpesaDialog()
                true
            } else false
        }
    }

    private fun showSimulateMpesaDialog() {
        val dialogBinding = com.example.truecaller.databinding.DialogSimulateMpesaBinding.inflate(layoutInflater)
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Simulate M-PESA Payment")
            .setView(dialogBinding.root)
            .setPositiveButton("OK") { _, _ ->
                val name = dialogBinding.editSimName.text.toString()
                val phone = dialogBinding.editSimPhone.text.toString()
                val amount = dialogBinding.editSimAmount.text.toString()
                val isReceived = dialogBinding.radioReceived.isChecked
                
                generateMpesaMessage(name, phone, amount, isReceived)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun generateMpesaMessage(name: String, phone: String, amount: String, isReceived: Boolean) {
        val transactionCode = (('A'..'Z') + ('0'..'9')).shuffled().take(10).joinToString("")
        val date = java.text.SimpleDateFormat("dd/MM/yy", java.util.Locale.getDefault()).format(java.util.Date())
        val time = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())
        
        val content = if (isReceived) {
            "$transactionCode Confirmed. You have received KES $amount.00 from $name $phone on $date at $time. New M-PESA balance is KES 12,500.00."
        } else {
            "$transactionCode Confirmed. You have sent KES $amount.00 to $name $phone on $date at $time. New M-PESA balance is KES 7,500.00."
        }

        val message = com.example.truecaller.data.entity.Message(
            threadId = "MPESA",
            sender = "MPESA",
            content = content,
            timestamp = System.currentTimeMillis(),
            type = "MPESA"
        )
        
        viewModel.insert(message)
        showNotification(content)
    }

    private fun showNotification(content: String) {
        val context = requireContext()
        val channelId = "mpesa_channel"
        val notificationManager = context.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(channelId, "MPESA Notifications", android.app.NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = android.content.Intent(context, com.example.truecaller.ui.ConversationActivity::class.java).apply {
            putExtra("THREAD_ID", "MPESA")
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = android.app.PendingIntent.getActivity(context, 0, intent, android.app.PendingIntent.FLAG_IMMUTABLE)

        val builder = androidx.core.app.NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("MPESA")
            .setContentText(content)
            .setStyle(androidx.core.app.NotificationCompat.BigTextStyle().bigText(content))
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
