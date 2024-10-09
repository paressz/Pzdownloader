package self.paressz.pzdownloader.ui.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import self.paressz.pzdownloader.databinding.DialogUpdateBinding

class UpdateDialog(context: Context) : Dialog(context) {
    lateinit var versionTag : String
    lateinit var changelog : String
    var binding: DialogUpdateBinding = DialogUpdateBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            btnSkip.setOnClickListener { dismiss() }
            tvVersion.setText(versionTag)
            tvBody.setText(changelog)
        }
    }

    fun setTexts(vTag : String, cl : String) {
        this.versionTag  = vTag
        this.changelog = cl
    }
    fun setOnUpdateButtonClickListener(onClickListener: View.OnClickListener) {
        binding.btnUpdate.setOnClickListener(onClickListener)
    }
}