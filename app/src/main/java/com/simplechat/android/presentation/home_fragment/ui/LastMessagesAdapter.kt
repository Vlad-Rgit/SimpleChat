package com.simplechat.android.presentation.home_fragment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.simplechat.android.databinding.ItemLastMessageBinding
import com.simplechat.android.framework.utils.SimpleDiffCallback
import com.simplechat.android.framework.utils.getBestFormat
import com.simplechat.core.domain.LastMessageDomain

typealias OnLastMessageClicked = (LastMessageDomain) -> Unit

class LastMessagesAdapter
    : RecyclerView.Adapter<LastMessagesAdapter.LastMessageViewHolder>() {


    private var items: List<LastMessageDomain> = emptyList()
    private var onLastMessageClicked: OnLastMessageClicked? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMessageViewHolder {

        val binding = ItemLastMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LastMessageViewHolder(binding).apply {
            setOnLastMessageClicked(onLastMessageClicked)
        }
    }

    override fun onBindViewHolder(holder: LastMessageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnLastMessageClicked(onLastMessageClicked: OnLastMessageClicked?) {
        this.onLastMessageClicked = onLastMessageClicked
    }

    fun setItems(newItems: List<LastMessageDomain>) {
        val callback = SimpleDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(callback)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    class LastMessageViewHolder(
        private val binding: ItemLastMessageBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentItem: LastMessageDomain
        private var onLastMessageClicked: OnLastMessageClicked? = null

        init {
            binding.root.setOnClickListener {
                onLastMessageClicked?.invoke(currentItem)
            }
        }

        fun setOnLastMessageClicked(onLastMessageClicked: OnLastMessageClicked?) {
            this.onLastMessageClicked = onLastMessageClicked
        }

        fun bind(item: LastMessageDomain) {
            currentItem = item

            binding.run {

                if(item.isChatPrivate) {
                    tvTitle.text = item.displayUserName
                }
                else {
                    tvTitle.text = item.chatName
                }

                tvBody.text = item.text
                tvDateSent.text = item.dateSent.getBestFormat()
            }
        }


    }


}