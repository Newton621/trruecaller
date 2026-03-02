package com.example.truecaller.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truecaller.databinding.FragmentCallsBinding
import com.example.truecaller.ui.CallLogAdapter
import com.example.truecaller.ui.viewmodel.CallLogViewModel

class CallsFragment : Fragment() {
    private var _binding: FragmentCallsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CallLogViewModel
    private lateinit var adapter: CallLogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCallsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        adapter = CallLogAdapter()
        binding.recyclerViewCalls.adapter = adapter
        
        viewModel = androidx.lifecycle.ViewModelProvider(this).get(CallLogViewModel::class.java)
        viewModel.allCallLogs.observe(viewLifecycleOwner) { logs ->
            adapter.submitList(logs)
        }

        binding.fabDial.setOnClickListener {
            showDialPad()
        }
    }

    private fun showDialPad() {
        val dialogBinding = com.example.truecaller.databinding.DialogDialPadBinding.inflate(layoutInflater)
        var currentNumber = ""
        
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        val buttons = listOf(
            dialogBinding.btn0, dialogBinding.btn1, dialogBinding.btn2,
            dialogBinding.btn3, dialogBinding.btn4, dialogBinding.btn5,
            dialogBinding.btn6, dialogBinding.btn7, dialogBinding.btn8,
            dialogBinding.btn9, dialogBinding.btnStar, dialogBinding.btnHash
        )

        buttons.forEach { btn ->
            btn.setOnClickListener {
                currentNumber += btn.text
                dialogBinding.textNumber.text = currentNumber
            }
        }

        dialogBinding.btnCallAction.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                initiateCall(currentNumber)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun initiateCall(number: String) {
        val intent = android.content.Intent(android.content.Intent.ACTION_DIAL).apply {
            data = android.net.Uri.parse("tel:$number")
        }
        startActivity(intent)
        
        // Save to local logs for simulation
        viewModel.insert(com.example.truecaller.data.entity.CallLog(
            phoneNumber = number,
            timestamp = System.currentTimeMillis(),
            duration = 0,
            type = 2 // Outgoing
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
