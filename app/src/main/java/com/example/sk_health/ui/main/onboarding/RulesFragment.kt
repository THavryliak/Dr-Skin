package com.example.sk_health.ui.main.onboarding

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sk_health.R
import com.example.sk_health.databinding.FragmentRulesBinding
import com.example.sk_health.ui.main.root.RootActivity
import com.example.sk_health.vm.onboarding.RulesViewModel

class RulesFragment : Fragment() {

    private lateinit var viewModel: RulesViewModel
    lateinit var binding: FragmentRulesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rules, container, false)
        viewModel = ViewModelProvider(this)[RulesViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rulesCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onUserReadTheRulesClick(isChecked)
        }

        binding.acceptRulesButton.setOnClickListener {
            startActivity(Intent(activity, RootActivity::class.java))
        }
    }

    private fun onUserReadTheRulesClick(isChecked: Boolean) {
        if (isChecked) {
            enableRulesButton()
            binding.checkAnimationView.isVisible = true
            binding.recordsImage.isVisible = false
            binding.checkAnimationView.playAnimation()
        } else {
            disableRulesButton()
            binding.checkAnimationView.isVisible = false
            binding.recordsImage.isVisible = true
        }
    }

    private fun disableRulesButton() {
        binding.acceptRulesButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.disabled_button_color))
        binding.acceptRulesButton.setTextColor(resources.getColor(R.color.disabled_button_text))
        binding.acceptRulesButton.isEnabled = false
    }

    private fun enableRulesButton() {
        binding.acceptRulesButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.main_teal))
        binding.acceptRulesButton.setTextColor(resources.getColor(R.color.main_deep_blue))
        binding.acceptRulesButton.isEnabled = true
    }
}