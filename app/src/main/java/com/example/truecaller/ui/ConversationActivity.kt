package com.example.truecaller.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.truecaller.databinding.ActivityConversationBinding
import com.example.truecaller.ui.viewmodel.MessageViewModel
import com.example.truecaller.ui.ConversationAdapter

class ConversationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConversationBinding
    private lateinit var viewModel: MessageViewModel
    private lateinit var adapter: ConversationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val threadId = intent.getStringExtra("THREAD_ID") ?: return
        
        setSupportActionBar(binding.toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = threadId
        binding.toolbarDetails.setNavigationOnClickListener { finish() }

        adapter = ConversationAdapter()
        binding.rvConversation.adapter = adapter

        viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        viewModel.getMessagesByThread(threadId).observe(this) { messages ->
            adapter.submitList(messages)
            binding.rvConversation.scrollToPosition(messages.size - 1)
        }
    }
}
