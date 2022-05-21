package com.example.sk_health.ui.main.root.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sk_health.App
import com.example.sk_health.R
import com.example.sk_health.databinding.FragmentHomeBinding
import com.example.sk_health.ui.main.root.history_flow.history.HistoryItemViewData
import com.example.sk_health.utils.ImageUtil.toBitmap
import com.example.sk_health.vm.root.history_flow.HistoryDiseaseType
import com.example.sk_health.vm.root.home.HomeViewModel
import java.time.LocalDate
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels { (requireContext().applicationContext as App).appComponent.viewModelsFactory() }
    private lateinit var binding : FragmentHomeBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()

        val getPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
            viewModel.processPhoto(bitmap)
            Handler(Looper.getMainLooper()).postDelayed(Runnable { showPredictionCard() }, 1000L)
        }

        startCameraPreview()

        binding.getFromGalleryBtn.setOnClickListener {
            getPhoto.launch("image/*")
        }

        binding.takePhotoBtn.setOnClickListener {
            takePicture()
        }

        binding.captureResultCard.backBtn.setOnClickListener {
            hidePredicationCard()
        }

        binding.changeCameraBtn.setOnClickListener {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                CameraSelector.DEFAULT_BACK_CAMERA
            } else {
                CameraSelector.DEFAULT_FRONT_CAMERA
            }
            startCameraPreview()
        }

        binding.captureResultCard.saveBtn.setOnClickListener {
            try {
                viewModel.saveResultToHistory(grabViewData())
                Toast.makeText(requireContext(), "Successfully saved!", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Sth went wrong!", Toast.LENGTH_LONG).show()
            } finally {
                hidePredicationCard()
            }
        }

        viewModel.photoResult.observe(viewLifecycleOwner) {
            binding.captureResultCard.label.text = it.title
            binding.captureResultCard.probability.text = resources.getString(R.string.probability, (it.probability * 100)).plus("%")
            binding.captureResultCard.diseaseImage.setImageResource(it.imageRes)
            binding.captureResultCard.diseaseInfo.setText(it.textRes)
        }
    }

    private fun startCameraPreview() {
        imageCapture = ImageCapture.Builder().build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also { it.setSurfaceProvider(binding.cameraView.surfaceProvider) }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                throw Throwable("Camera error")
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePicture() {
        binding.loadingSpinner.isVisible = true
        imageCapture.takePicture(cameraExecutor, object : ImageCapture.OnImageCapturedCallback() {
            @SuppressLint("UnsafeOptInUsageError")
            override fun onCaptureSuccess(image: ImageProxy) {
                viewModel.processPhoto(image.image!!.toBitmap())
                Handler(Looper.getMainLooper()).post {
                    showPredictionCard()
                }
                super.onCaptureSuccess(image)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e(TAG, exception.stackTraceToString())
            }
        })
    }

    private fun showPredictionCard() {
        binding.homeButtonsBlock.isVisible = false
        binding.changeCameraBtn.isVisible = false
        binding.loadingSpinner.isVisible = false
        binding.captureResultCard.root.isVisible = true
    }

    private fun hidePredicationCard() {
        binding.homeButtonsBlock.isVisible = true
        binding.changeCameraBtn.isVisible = true
        binding.captureResultCard.root.isVisible = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun grabViewData(): HistoryItemViewData {
        return HistoryItemViewData(
            id = null,
            disease = HistoryDiseaseType.getByName(binding.captureResultCard.label.text.toString()),
            dateOfCreation = LocalDate.now().toString(),
            probability = binding.captureResultCard.probability.text.toString().replace("[^0-9,.]".toRegex(), "").replace(",",".").toDouble()
        )
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}