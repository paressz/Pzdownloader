package self.paressz.pzdownloader.ui.setting

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import self.paressz.pzdownloader.databinding.ItemSettingActivityBinding
import self.paressz.pzdownloader.model.SettingItem

class SettingAdapter(val settingItems: List<SettingItem>) : RecyclerView.Adapter<SettingAdapter.SettingViewHolder>() {
    init {
        Log.d("SETTING ADAPTER", "ADAPTERINIT ${settingItems.size}")
    }
    inner class SettingViewHolder(val binding: ItemSettingActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SettingItem) {
            binding.apply {
                textView.text = item.title
                textView2.text = item.description
            }
            Log.d("SETTING ADAPTER", "bind: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val binding = ItemSettingActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("SETTING ADAPTER", "onCreateViewHolder: caled")
        return SettingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        settingItems.size.let {
            Log.d("SETTING ADAPTER", "getItemCount: $it")
            return it
        }
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        val item = settingItems.get(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            item.onClick()
        }
        Log.d("SETTING ADAPTER", "onBindViewHolder: $item")
    }
}