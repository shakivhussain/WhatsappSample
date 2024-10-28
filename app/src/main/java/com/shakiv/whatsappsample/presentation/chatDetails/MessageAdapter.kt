package com.shakiv.whatsappsample.presentation.chatDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.databinding.ItemMessageBinding
import com.shakiv.whatsappsample.utils.DateUtils.getFormattedTime

class MessageAdapter(
    private val onMessageClick: (Message, Int) -> Unit
) : PagingDataAdapter<Message, MessageAdapter.MessageViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding, onMessageClick)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MessageViewHolder(
        private val binding: ItemMessageBinding,
        onMessageClick: (Message, Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var _message: Message? = null
        private var _position: Int? = null

        init {
            binding.root.setOnClickListener {

                if (_message != null && _position != null) {
                    onMessageClick.invoke(
                        _message ?: return@setOnClickListener,
                        _position ?: return@setOnClickListener
                    )
                }
            }
        }

        fun bind(position: Int) {
            val message = getItem(position)
            _position = position
            _message = message
            binding.messageText.text = message?.text.orEmpty()
            binding.messageTime.text = getFormattedTime(message?.date?:0)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }
    }
}