package com.example.mydigitoonapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import com.example.mydigitoonapp.R
import com.example.mydigitoonapp.databinding.FragmentNetworkOfflineBinding

class NetworkOfflineFragment : AppCompatDialogFragment() {
    var binding: FragmentNetworkOfflineBinding? = null
    private var reloadListener: View.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.fragment_network_offline, null, false)
        binding!!.fragment = this
        binding!!.btnReload.setOnClickListener(reloadListener)
        val builder = AlertDialog.Builder(this.context)
        builder.setView(binding!!.root)
        val dialog: Dialog = builder.create()
        if (dialog.window != null) dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    fun setReloadListener(reloadListener: View.OnClickListener) {
        this.reloadListener = reloadListener
    }

    companion object {
        @JvmStatic
        fun newInstance() = NetworkOfflineFragment()
    }
}