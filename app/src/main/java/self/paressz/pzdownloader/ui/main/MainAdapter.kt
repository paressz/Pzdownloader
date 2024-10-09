package self.paressz.pzdownloader.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import self.paressz.pzdownloader.databinding.ItemMainActivityBinding
import self.paressz.pzdownloader.model.MainItem
import self.paressz.pzdownloader.model.MainType
import self.paressz.pzdownloader.ui.fb.FbDownloadActivity
import self.paressz.pzdownloader.ui.ig.IgDownloadActivity
<<<<<<< HEAD
import self.paressz.pzdownloader.ui.tiktok.TiktokDownloadActivity
=======
>>>>>>> master
import self.paressz.pzdownloader.ui.x.XDownloadActivity
import self.paressz.pzdownloader.util.ToastUtil

class MainAdapter(val items: List<MainItem>, val context: Context) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    inner class MainViewHolder(val binding: ItemMainActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainItem) {
            Glide.with(context)
                .load(item.icon)
                .into(binding.platformIcon)
            binding.platformName.text = item.title
            binding.description.text = item.description
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            bind(item)
            itemView.setOnClickListener { view ->
                when(item.type) {
                    MainType.INSTAGRAM -> {
                        Intent(context, IgDownloadActivity::class.java).also { context.startActivity(it) }
                    }
                    MainType.FACEBOOK -> {
                        Intent(context, FbDownloadActivity::class.java).also { context.startActivity(it) }
                    }
                    MainType.X -> {
                        Intent(context, XDownloadActivity::class.java).also { context.startActivity(it) }
                    }
                    MainType.TIKTOK  -> {
<<<<<<< HEAD
                        Intent(context, TiktokDownloadActivity::class.java).also { context.startActivity(it) }
=======
                        ToastUtil.showToast(context, "Coming soon")
>>>>>>> master
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemMainActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size
}