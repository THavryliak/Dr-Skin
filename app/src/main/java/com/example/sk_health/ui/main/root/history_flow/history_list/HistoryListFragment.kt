package com.example.sk_health.ui.main.root.history_flow.history_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.sk_health.App
import com.example.sk_health.R
import com.example.sk_health.databinding.FragmentHistoryListBinding
import com.example.sk_health.ui.main.root.history_flow.history.HistoryItemViewData
import com.example.sk_health.vm.root.history_flow.history_list.HistoryListViewModel

class HistoryListFragment : Fragment() {

    private val viewModel: HistoryListViewModel by viewModels { (requireContext().applicationContext as App).appComponent.viewModelsFactory() }
    private lateinit var binding : FragmentHistoryListBinding
    lateinit var adapter: HistoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_list, container, false)
        adapter = HistoryListAdapter { history -> onHistoryItemClick(history) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyRv.adapter = adapter

        viewModel.init()

        viewModel.historyItems.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                hideNoHistoryViewBlock()
            } else {
                showNoHistoryViewBlock()
            }
            adapter.submitList(it)
        }
    }

    private fun hideNoHistoryViewBlock() {
        binding.noHistoryBlock.isVisible = false
    }

    private fun showNoHistoryViewBlock() {
        binding.noHistoryBlock.isVisible = true
    }

    private fun onHistoryItemClick(history: HistoryItemViewData) {
        Navigation.findNavController(binding.root).navigate(R.id.action_historyFragment_to_historyItemFragment, bundleOf("historyId" to history.id))
    }
}