package com.example.sk_health.ui.main.root.history_flow.history

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.sk_health.App
import com.example.sk_health.R
import com.example.sk_health.databinding.FragmentHistoryItemBinding
import com.example.sk_health.vm.root.history_flow.history.HistoryItemViewModel

class HistoryItemFragment : Fragment() {

    private lateinit var binding: FragmentHistoryItemBinding
    private val viewModel: HistoryItemViewModel by viewModels { (requireContext().applicationContext as App).appComponent.viewModelsFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_item, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getString("historyId")

        viewModel.init(args)

        binding.deleteHistoryBtn.setOnClickListener {
            viewModel.deleteHistory()
            Navigation.findNavController(binding.root).popBackStack()
        }

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }

        viewModel.history.observe(viewLifecycleOwner) {
            fillCardWithData(it)
        }
    }

    private fun fillCardWithData(viewData: HistoryItemViewData) {
        binding.apply {
            diseaseItemProbability.text = resources.getString(R.string.probability, viewData.probability.toString())
            historyItemCard.strokeColor = Color.parseColor(viewData.disease.color)
            diseaseItemName.text = viewData.disease.disease
            historyItemDate.text = viewData.dateOfCreation
            backBtn.iconTint = ColorStateList.valueOf(Color.parseColor(viewData.disease.color))
            diseaseImage.setImageResource(viewData.disease.resId)
        }
    }
}