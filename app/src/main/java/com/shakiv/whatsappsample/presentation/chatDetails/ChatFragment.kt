package com.shakiv.whatsappsample.presentation.chatDetails

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.model.User
import com.shakiv.whatsappsample.databinding.FragmentChatBinding
import com.shakiv.whatsappsample.presentation.base.BaseFragment
import com.shakiv.whatsappsample.presentation.chatList.ChatListFragment.Companion.KEY_USER
import com.shakiv.whatsappsample.utils.imageLoader.ImageLoader
import com.shakiv.whatsappsample.utils.imageLoader.Result
import com.shakiv.whatsappsample.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var imageLoader: ImageLoader

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var messageAdapter: MessageAdapter

    var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        user = arguments?.getSerializable(KEY_USER) as? User

        messageAdapter = MessageAdapter() { message: Message, position: Int ->
            showUpdateDialog(message, position)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindUI()
        bindObserver()
        bindListener()

    }


    override fun bindObserver() {
        super.bindObserver()

        lifecycleScope.launch {


            chatViewModel.getAllMessages(user?.userId ?: 0).collectLatest {
                messageAdapter.submitData(it)
            }
        }


        messageAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                }

                is LoadState.NotLoading -> {
                    binding.rvMessage.scrollToPosition(messageAdapter.snapshot().size)
                }

                is LoadState.Error -> {}
            }
        }
    }

    override fun bindUI() {
        super.bindUI()

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomBar) { view, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            view.updatePadding(bottom = if (imeVisible) insets.getInsets(WindowInsetsCompat.Type.ime()).bottom else 0)
            insets
        }

        binding.mainHeader.apply {
            tvHeading.text = user?.username.toString()
        }

        binding.rvMessage.adapter = messageAdapter


//        ImageLoader.loadImageFromUrl(user?.profileUrl.orEmpty(),
//            onSuccess = {
//                binding.mainHeader.icProfile.setImageBitmap(it)
//            },
//            onFailure = {}
//        )

        // Initialize the ImageLoader


        lifecycleScope.launch {
            when (val result = ImageLoader.loadImage(user?.profileUrl.orEmpty())) {
                is com.shakiv.whatsappsample.utils.imageLoader.Result.Success -> {
                    binding.mainHeader.icProfile.setImageBitmap(result.value)
                }

                is Result.Failure -> {
                    Log.e("ImageLoader", "Error loading image: ${result.exception.message}")
                }
            }

        }
    }

    override fun bindListener() {
        super.bindListener()

        binding.apply {
            ivSent.setOnClickListener {
                sentMessage()
            }
        }

        binding.mainHeader.apply {
            icProfile.setOnClickListener { }
            icBackButton.setOnClickListener {
                findNavController().navigateUp()
            }

        }

    }

    private fun sentMessage() {

        val text = binding.etMessage.text

        if (text?.isEmpty() == true) {
            Toast.makeText(context, "Empty Message Not Allowed ", Toast.LENGTH_SHORT).show()
            return
        } else {
            binding.etMessage.setText("")
        }

        val message = Message(
            text = text.toString(),
            username = user?.username.orEmpty(),
            date = System.currentTimeMillis(),
            userId = user?.userId ?: 0
        )

        lifecycleScope.launch {
            chatViewModel.addMessage(message = message).collectLatest {
                val isInserted = it.toInt() != -1
                if (isInserted) {
                    messageAdapter.refresh()
                    binding.rvMessage.smoothScrollToPosition(messageAdapter.snapshot().size + 1)
                }
            }

        }


        lifecycleScope.launch {
            chatViewModel.addLastMessage(user?.userId ?: return@launch, message)
        }

    }


    private fun showUpdateDialog(message: Message, position: Int) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Update Message")

        val input = EditText(context)
        input.background = null
        input.hint = "Enter new message"
        input.setText(message.text)
        input.showKeyboard()
        builder.setView(input)

        builder.setPositiveButton("Update") { dialog, _ ->
            val newMessageContent = input.text.toString()
            message.also {
                it.text = newMessageContent
            }
            chatViewModel.updateMessage(message)
            messageAdapter.notifyItemChanged(position)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}