package com.simplechat.android.presentation.chat_fragment.ui

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.simplechat.android.R
import com.simplechat.android.databinding.ItemMessageBinding
import com.simplechat.android.framework.utils.SimpleDiffCallback
import com.simplechat.android.framework.utils.getBestFormat
import com.simplechat.core.domain.MessageDomain

typealias OnMessageClicked = (MessageDomain) -> Unit

class MessagesAdapter: RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    private var items: List<MessageDomain> = emptyList()
    private var onMessageDeleteClicked: OnMessageClicked? = null

    var currentUserId: Long = -1

    fun setItems(newItems: List<MessageDomain>) {
        val callback = SimpleDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(callback)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnMessageDeleteClicked(onMessageDeleteClicked: OnMessageClicked?) {
        this.onMessageDeleteClicked = onMessageDeleteClicked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MessageViewHolder(binding).apply {
            setOnMessageDeleteClicked(onMessageDeleteClicked)
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(items[position], currentUserId)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class MessageViewHolder(
        private val binding: ItemMessageBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentItem: MessageDomain
        private var currentUserId: Long = -1

        private val popupMenu: PopupMenu by lazy {
            PopupMenu(
                binding.root.context,
                binding.card
            ).apply {
                inflate(R.menu.menu_item_message)

                setOnMenuItemClickListener {
                    if(it.itemId == R.id.menu_delete) {
                        onMessageDeleteClicked?.invoke(currentItem)
                    }
                    false
                }
            }
        }

        private var onMessageDeleteClicked: OnMessageClicked? = null


        init {
            binding.card.setOnLongClickListener {
                if(currentItem.senderId == currentUserId) {
                    popupMenu.show()
                }
                false
            }
        }

        fun setOnMessageDeleteClicked(onMessageClicked: OnMessageClicked?) {
            this.onMessageDeleteClicked = onMessageClicked
        }

        fun bind(message: MessageDomain, currentUserId: Long) {
            this.currentUserId = currentUserId
            currentItem = message

            binding.run {
                tvText.text = message.text
                tvDateSent.text = message.dateSent.getBestFormat()

                val layoutParams = binding.card.layoutParams
                    as FrameLayout.LayoutParams

                layoutParams.gravity = if(message.senderId == currentUserId) {
                    Gravity.END
                }
                else {
                    Gravity.START
                }

                card.layoutParams = layoutParams
            }
        }

    }


}