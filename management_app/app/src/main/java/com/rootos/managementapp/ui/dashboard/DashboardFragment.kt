package com.rootos.managementapp.ui.dashboard

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rootos.managementapp.R
import com.rootos.managementapp.databinding.FragmentDashboardBinding
import android.content.DialogInterface.OnClickListener
import android.util.Log

class DashboardFragment:Fragment(){


private var _binding: FragmentDashboardBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textDashboard
    dashboardViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
        var view: View? = null
        view = layoutInflater.inflate(R.layout.fragment_dashboard,container,false)
        val button_magisk_stop = view!!.findViewById<View>(R.id.magisk_stop) as Button
        button_magisk_stop.setOnClickListener{
            Runtime.getRuntime().exec("su -c magisk --stop")
        }

    return view


  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

