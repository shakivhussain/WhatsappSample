package com.shakiv.whatsappsample.presentation.chatList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.whatsappsample.data.model.User
import com.shakiv.whatsappsample.databinding.ItemUserBinding
import com.shakiv.whatsappsample.utils.DateUtils.getFormattedDate
import com.shakiv.whatsappsample.utils.imageLoader.ImageLoader
import com.shakiv.whatsappsample.utils.imageLoader.Result
import kotlinx.coroutines.launch

class UsersAdapter(
    private val onItemClick: (User) -> Unit,
    private val lifecycleOwner: LifecycleOwner // Pass the LifecycleOwner
) : PagingDataAdapter<User, UsersAdapter.UserViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersAdapter.UserViewHolder {

        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: UsersAdapter.UserViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class UserViewHolder(private val binding: ItemUserBinding, onItemClick: (User) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private var _user: User? = null

        init {
            binding.root.setOnClickListener {
                _user?.let {
                    onItemClick(it)
                }
            }

        }

        fun bind(position: Int) {
            val user = getItem(position)
            _user = user

            binding.tvTitle.text = user?.username.toString()
            binding.tvDesc.text = user?.lastMessage?.text.toString()
            binding.tvDate.text = getFormattedDate(user?.lastMessage?.date ?: 0)

            lifecycleOwner.lifecycleScope.launch {
                when (val result = ImageLoader.loadImage(user?.profileUrl.orEmpty())) {
                    is Result.Success -> {
                        binding.icProfile.setImageBitmap(result.value)
                    }

                    is Result.Failure -> {
                        result.exception.printStackTrace()
                    }
                }

            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.userId == newItem.userId

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem

        }
    }
}












