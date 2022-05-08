package com.example.sk_health.ui.main.root.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sk_health.R
import com.example.sk_health.databinding.FragmentHomeBinding
import com.example.sk_health.di.app.App
import com.example.sk_health.di.vm_factory.HomeViewModelFactory
import com.example.sk_health.vm.root.home.HomeViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var vmFactory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding : FragmentHomeBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()

        startCameraPreview()

        binding.takePhotoBtn.setOnClickListener {
            takePicture()
        }

        binding.captureResultCard.backBtn.setOnClickListener {
            hidePredicationCard()
        }

        viewModel.photoResult.observe(viewLifecycleOwner) {
            binding.captureResultCard.label.text = it.title
            binding.captureResultCard.probability.text = it.probability
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

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

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
                viewModel.processPhoto(image.image!!)
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
        binding.takePhotoBtn.isVisible = false
        binding.loadingSpinner.isVisible = false
        binding.captureResultCard.root.isVisible = true
    }

    private fun hidePredicationCard() {
        binding.takePhotoBtn.isVisible = true
        binding.captureResultCard.root.isVisible = false
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}