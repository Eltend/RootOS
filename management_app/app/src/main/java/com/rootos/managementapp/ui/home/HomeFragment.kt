package com.rootos.managementapp.ui.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rootos.managementapp.databinding.FragmentHomeBinding
import java.io.BufferedReader
import java.io.InputStreamReader


fun whoami_button () {
    try {
        var line: String?
        val process = Runtime.getRuntime().exec("su -c whoami")
        val stdin = process.outputStream
        val stderr = process.errorStream
        val stdout = process.inputStream

        stdin.write(("ls\n").toByteArray())
        stdin.write("exit\n".toByteArray())
        stdin.flush()

        stdin.close()
        var br =
            BufferedReader(InputStreamReader(stdout))
        while ((br.readLine().also { line = it }) != null) {
            Log.d("[Output]", line!!)
        }
        br.close()
        br =
            BufferedReader(InputStreamReader(stderr))
        while ((br.readLine().also { line = it }) != null) {
            Log.e("[Error]", line!!)
        }
        br.close()

        process.waitFor()
        process.destroy()
    } catch (ex: Exception) {
    }
}

class VibratorHelper private constructor(private val context: Context) {

    fun vibrate(duration: Long, amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator.run {
                cancel()
                vibrate(VibrationEffect.createOneShot(duration, amplitude))
            }
        } else {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.cancel()
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
            } else {
                vibrator.vibrate(duration) // Amplitude control is not available below API 26
            }
        }
    }

    companion object {
        @JvmStatic
        fun from(context: Context): VibratorHelper? {
            val hasVibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator.hasVibrator()
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.hasVibrator()
            }
            return if (hasVibrator) VibratorHelper(context.applicationContext) else null
        }
    }
}



    class HomeFragment : Fragment() {

        private var _binding: FragmentHomeBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)

            val view: View = binding.root

            // Reboot to system button
            binding.homeReboot.setOnClickListener {
                Runtime.getRuntime().exec("su -c reboot")
            }

            // Reboot to recovery button
            binding.homeRebootRec.setOnClickListener {
                Runtime.getRuntime().exec("su -c reboot recovery")
            }

            // Reboot to bootloader button
            binding.homeRebootBot.setOnClickListener {
                Runtime.getRuntime().exec("su -c reboot bootloader")
            }

            // Test vibration and log with whoami button
            binding.homeWhoami.setOnClickListener {
                Log.w("Debug", "Button pressed: whoami")
                val vibratorHelper = context?.let { VibratorHelper.from(it) }
                vibratorHelper?.vibrate(150, 50)
                whoami_button()
            }

            return view
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
