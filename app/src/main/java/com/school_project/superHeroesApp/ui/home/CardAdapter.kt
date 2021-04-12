package com.school_project.superHeroesApp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.school_project.superHeroesApp.data.models.superhero.Content
import com.school_project.superHeroesApp.databinding.HeroCardItemBinding
import com.school_project.superHeroesApp.databinding.LoadingItemBinding

class CardAdapter(
    private val onItemClick: (superheroCard: Content) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cardList: List<Content> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var loadingMore = false
        set(value) {
            field = value
            notifyItemChanged(itemCount - 1)
        }

    private val onClickListener = View.OnClickListener { v ->
        val card = v?.tag as Content
        onItemClick.invoke(card)
    }


    override fun getItemViewType(position: Int): Int {
        return if (itemCount - 1 == position) VIEW_TYPE_LOADER else VIEW_TYPE_CARD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            VIEW_TYPE_LOADER -> LoadingViewHolder(
                LoadingItemBinding.inflate(LayoutInflater.from(parent.context))
            )
            VIEW_TYPE_CARD -> CardViewHolder(
                binding = HeroCardItemBinding.inflate(LayoutInflater.from(parent.context)),
                onClickListener = onClickListener
            )
            else -> throw RuntimeException("unknown ViewType")
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                val item = cardList[position]
                holder.binding.nameTV.text = item.name
                Glide.with(holder.itemView)
                    .load(item.image?.url)
                    .into(holder.binding.cardIV)
                holder.binding.root.tag = item
            }
            is LoadingViewHolder -> {
                holder.binding.loader.visibility = if (loadingMore) View.VISIBLE else View.GONE
            }
        }
    }

    override fun getItemCount() = cardList.size + 1

    class CardViewHolder(
        val binding: HeroCardItemBinding,
        onClickListener: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(onClickListener)
        }
    }

    class LoadingViewHolder(
        val binding: LoadingItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class LoaderSpanSizeLookup(private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) :
        GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (adapter.itemCount - 1 == position) 2 else 1
        }
    }


    companion object {
        const val VIEW_TYPE_CARD = 1
        const val VIEW_TYPE_LOADER = 2
    }

}


