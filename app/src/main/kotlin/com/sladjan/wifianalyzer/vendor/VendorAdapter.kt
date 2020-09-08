
package com.sladjan.wifianalyzer.vendor

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.sladjan.annotation.OpenClass
import com.sladjan.wifianalyzer.MainContext.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.databinding.VendorDetailsBinding
import com.sladjan.wifianalyzer.vendor.model.VendorService

@OpenClass
internal class VendorAdapter(context: Context, private val vendorService: VendorService) :
        ArrayAdapter<String>(context, R.layout.vendor_details, vendorService.findVendors()) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val binding: Binding = view?.let { Binding(it) } ?: Binding(create(parent))
        getItem(position)?.let {
            binding.vendorName.text = it
            binding.vendorMacs.text = vendorService.findMacAddresses(it).joinToString(", ")
        }
        return binding.root
    }

    fun update(filter: String) {
        clear()
        addAll(vendorService.findVendors(filter))
    }

    private fun create(parent: ViewGroup): VendorDetailsBinding =
            VendorDetailsBinding.inflate(INSTANCE.layoutInflater, parent, false)

    private inner class Binding {
        val root: View
        val vendorName: TextView
        val vendorMacs: TextView

        internal constructor(binding: VendorDetailsBinding) {
            root = binding.root
            vendorName = binding.vendorName
            vendorMacs = binding.vendorMacs
        }

        internal constructor(view: View) {
            root = view
            vendorName = view.findViewById(R.id.vendor_name)
            vendorMacs = view.findViewById(R.id.vendor_macs)
        }

    }

}