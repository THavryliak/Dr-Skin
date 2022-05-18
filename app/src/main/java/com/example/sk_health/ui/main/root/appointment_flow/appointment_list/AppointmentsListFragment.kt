package com.example.sk_health.ui.main.root.appointment_flow.appointment_list

import android.annotation.SuppressLint
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
import com.example.sk_health.R
import com.example.sk_health.databinding.FragmentAppointmentsBinding
import com.example.sk_health.di.app.App
import com.example.sk_health.vm.root.appointment_flow.appointment_list.AppointmentItemViewData
import com.example.sk_health.vm.root.appointment_flow.appointment_list.AppointmentsListViewModel

class AppointmentsListFragment : Fragment() {

    private val viewModel: AppointmentsListViewModel by viewModels { (requireContext().applicationContext as App).appComponent.viewModelsFactory() }
    private lateinit var binding : FragmentAppointmentsBinding
    lateinit var adapter: AppointmentsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointments, container, false)
        adapter = AppointmentsListAdapter { appointment -> onAppointmentItemClick(appointment) }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appointmentsList.adapter = adapter

        viewModel.init()

        viewModel.appointments.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                hideNoAppointmentsBlock()
            } else {
                showNoAppointmentsBlock()
            }
            adapter.submitList(it)
        }

        binding.createAppointmentBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_appointmentsFragment_to_appointmentFragment)
        }
    }

    private fun hideNoAppointmentsBlock() {
        binding.noAppointmentsBlock.isVisible = false
    }

    private fun showNoAppointmentsBlock() {
        binding.noAppointmentsBlock.isVisible = true
    }

    private fun onAppointmentItemClick(appointment: AppointmentItemViewData) {
        Navigation.findNavController(binding.root).navigate(R.id.action_appointmentsFragment_to_appointmentFragment, bundleOf("appointmentId" to appointment.id))
    }
}