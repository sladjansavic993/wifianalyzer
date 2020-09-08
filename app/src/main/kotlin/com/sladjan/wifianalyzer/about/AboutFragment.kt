
package com.sladjan.wifianalyzer.about

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.sladjan.util.EMPTY
import com.sladjan.util.buildMinVersionP
import com.sladjan.util.readFile
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.databinding.AboutContentBinding
import java.text.SimpleDateFormat
import java.util.*


class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: AboutContentBinding = AboutContentBinding.inflate(inflater, container, false)
        val activity: FragmentActivity = requireActivity()
        setTexts(binding, activity)
        setOnClicks(binding, activity)
        return binding.root
    }

    private fun setTexts(binding: AboutContentBinding, activity: FragmentActivity) {
        binding.aboutCopyright.text = copyright()
        binding.aboutVersionInfo.text = "2.2.1"
        binding.aboutPackageName.text = activity.packageName
    }

    private fun setOnClicks(binding: AboutContentBinding, activity: FragmentActivity) {
        val gpl = AlertDialogClickListener(activity, R.string.gpl, R.raw.gpl)
        binding.license.setOnClickListener(gpl)
        val al = AlertDialogClickListener(activity, R.string.al, R.raw.al)
        binding.graphViewLicense.setOnClickListener(al)
        binding.materialDesignIconsLicense.setOnClickListener(al)
        binding.writeReview.setOnClickListener(WriteReviewClickListener(activity))
    }

    private fun copyright(): String =
            resources.getString(R.string.app_copyright) + SimpleDateFormat(YEAR_FORMAT, Locale.getDefault()).format(Date())



    private class WriteReviewClickListener(private val activity: Activity) : View.OnClickListener {
        override fun onClick(view: View) {
            val url = "market://details?id=" + activity.applicationContext.packageName
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                activity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(view.context, e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

    private class AlertDialogClickListener(private val activity: Activity,
                                           private val titleId: Int,
                                           private val resourceId: Int,
                                           private val isSmallFont: Boolean = true) : View.OnClickListener {
        override fun onClick(view: View) {
            if (!activity.isFinishing) {
                val text = readFile(activity.resources, resourceId)
                val alertDialog: AlertDialog = AlertDialog.Builder(view.context)
                        .setTitle(titleId)
                        .setMessage(text)
                        .setNeutralButton(android.R.string.ok, Close())
                        .create()
                alertDialog.show()
                if (isSmallFont) {
                    alertDialog.findViewById<TextView>(android.R.id.message).textSize = 8f
                }
            }
        }

        private class Close : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialog.dismiss()
            }
        }

    }

    companion object {
        private const val YEAR_FORMAT = "yyyy"
    }
}