package com.tyas.smartfarm.view.pages.fragment

import android.content.Context
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.tyas.smartfarm.R

class PlantFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tangani tombol "Back" untuk keluar aplikasi
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Keluar dari aplikasi
                requireActivity().finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plant, container, false)

        // Cek apakah ini login pertama
        val sharedPreferences = requireActivity().getSharedPreferences("SmartFarmPrefs", Context.MODE_PRIVATE)
        val isFirstLogin = sharedPreferences.getBoolean("isFirstLogin", true)

        if (isFirstLogin) {
            // Tampilkan dialog jika login pertama
            showFarmerDialog()

            // Update status menjadi bukan login pertama
            sharedPreferences.edit().putBoolean("isFirstLogin", false).apply()
        }

        return view
    }

    private fun showFarmerDialog() {
        // Inflate layout custom untuk dialog
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom_farmer, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Bind tombol dari layout custom
        val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
        val btnNo = dialogView.findViewById<Button>(R.id.btnNo)

        // Aksi tombol "Iya"
        btnYes.setOnClickListener {
            Toast.makeText(requireContext(), "Selamat datang petani baru!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // Aksi tombol "Tidak"
        btnNo.setOnClickListener {
            Toast.makeText(requireContext(), "Semoga harimu menyenangkan!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // Tampilkan dialog
        dialog.show()
    }
}
