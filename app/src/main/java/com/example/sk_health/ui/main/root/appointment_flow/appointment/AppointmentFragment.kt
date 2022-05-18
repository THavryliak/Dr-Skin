package com.example.sk_health.ui.main.root.appointment_flow.appointment

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.sk_health.R
import com.example.sk_health.databinding.FragmentAppointmentBinding
import com.example.sk_health.di.app.App
import com.example.sk_health.vm.root.appointment_flow.AppointmentStatus
import com.example.sk_health.vm.root.appointment_flow.AppointmentVisitType
import com.example.sk_health.vm.root.appointment_flow.appointment.AppointmentViewData
import com.example.sk_health.vm.root.appointment_flow.appointment.AppointmentViewModel

class AppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAppointmentBinding
    private val viewModel: AppointmentViewModel by viewModels { (requireContext().applicationContext as App).appComponent.viewModelsFactory() }
    private var photoUri: Uri? = null
    private var appointmentStatus: AppointmentStatus = AppointmentStatus.NEW
    private var appointmentVisitType: AppointmentVisitType = AppointmentVisitType.COMMON

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
            setAppointmentImage(it)
        }
        val args = arguments?.getString("appointmentId")

        if (args != null) {
            binding.dbLoadingSpinner.isVisible = true
        }

        viewModel.init(args)

        viewModel.appointment.observe(viewLifecycleOwner) {
            fillAppointmentCard(it)
        }

        viewModel.validationError.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                enableValidationError()
            } else {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }

        binding.saveAppointment.setOnClickListener {
            viewModel.saveAppointment(grabViewData())
        }

        binding.removePhotoBtn.setOnClickListener {
            removeAttachedPhoto()
        }

        binding.deleteAppointment.setOnClickListener {
            viewModel.deleteAppointment()
            Navigation.findNavController(binding.root).popBackStack()
        }

        binding.attachPhoto.setOnClickListener {
            getPhoto.launch("image/*")
        }

        binding.doneStatusBtn.setOnClickListener {
//            resetStatusButtons()
//            binding.doneStatusBtn.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
            appointmentStatus = AppointmentStatus.ACCEPTED
        }

        binding.rejectedStatusBtn.setOnClickListener {
//            resetStatusButtons()
//            binding.rejectedStatusBtn.backgroundTintList = ColorStateList.valueOf(Color.RED)
            appointmentStatus = AppointmentStatus.REJECTED
        }

        binding.newStatusBtn.setOnClickListener {
//            resetStatusButtons()
//            binding.newStatusBtn.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
//            binding.newStatusBtn.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
            appointmentStatus = AppointmentStatus.NEW
        }

        binding.commonVisitBtn.setOnClickListener {
            appointmentVisitType = AppointmentVisitType.COMMON
        }

        binding.childrenVisitBtn.setOnClickListener {
            appointmentVisitType = AppointmentVisitType.CHILDREN
        }

        binding.therapyVisitBtn.setOnClickListener {
            appointmentVisitType = AppointmentVisitType.THERAPY
        }

        binding.consultationVisitBtn.setOnClickListener {
            appointmentVisitType = AppointmentVisitType.CONSULTATION
        }
    }

    private fun resetStatusButtons() {
        binding.apply {
            doneStatusBtn.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            rejectedStatusBtn.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            newStatusBtn.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        }
    }

    private fun enableValidationError() {
        binding.appointmentDateInput.error = "This field is required."
        binding.diseaseInput.error = "This field is required."
        binding.doctorInput.error = "This field is required."
        binding.doctorTypeInput.error = "This field is required."

        binding.appointmentDateInput.isErrorEnabled = true
        binding.diseaseInput.isErrorEnabled = true
        binding.doctorInput.isErrorEnabled = true
        binding.doctorTypeInput.isErrorEnabled = true
    }

    private fun grabViewData(): AppointmentViewData {
        return AppointmentViewData(
            doctor = binding.doctorEt.text.toString(),
            doctorType = binding.doctorTypeEt.text.toString(),
            therapyType = appointmentVisitType,
            statusType = appointmentStatus,
            address = binding.addressEt.text.toString(),
            phone = binding.phoneEt.text.toString(),
            disease = binding.diseaseEt.text.toString(),
            dateOfAppointment = binding.appointmentEt.text.toString(),
            photoUrl = photoUri?.toString() ?: "",
            notes = binding.notesEt.text.toString()
        )
    }

    private fun fillAppointmentCard(viewData: AppointmentViewData) {
        appointmentStatus = viewData.statusType
        appointmentVisitType = viewData.therapyType
        binding.apply {
            dbLoadingSpinner.isVisible = false
            doctorEt.setText(viewData.doctor)
            diseaseEt.setText(viewData.disease)
            addressEt.setText(viewData.address)
            phoneEt.setText(viewData.phone)
            appointmentEt.setText(viewData.dateOfAppointment)
            notesEt.setText(viewData.notes)
            if (viewData.photoUrl.isNotBlank()) {
                try {
                    setAppointmentImage(Uri.parse(viewData.photoUrl))
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Can't open image...", Toast.LENGTH_LONG).show()
                }
            }
            deleteAppointment.isVisible = true
        }
    }

    private fun setAppointmentImage(uri: Uri) {
        photoUri = uri
        binding.attachedPhoto.setImageURI(uri)
        binding.attachedPhoto.isVisible = true
        binding.removePhotoBtn.isVisible = true
        binding.attachedPhotoText.isVisible = true
    }

    private fun removeAttachedPhoto() {
        photoUri = null
        binding.attachedPhoto.isVisible = false
        binding.removePhotoBtn.isVisible = false
        binding.attachedPhotoText.isVisible = false
    }
}