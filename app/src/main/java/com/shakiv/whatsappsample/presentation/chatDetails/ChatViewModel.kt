package com.shakiv.whatsappsample.presentation.chatDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.repository.MessageRepository
import com.shakiv.whatsappsample.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject constructor(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun addMessage(message: Message) = messageRepository.addMessage(message)

    suspend fun getAllMessages(userId: Long) = messageRepository.getAllMessages(userId)

    fun updateMessage(message: Message) {

        viewModelScope.launch {
            messageRepository.updateMessage(message)
        }
    }

    fun addLastMessage(userId: Long, message: Message) {
        userRepository.updateUser(userId, message)
    }

}