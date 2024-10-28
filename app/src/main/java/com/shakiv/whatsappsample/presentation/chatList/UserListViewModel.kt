package com.shakiv.whatsappsample.presentation.chatList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakiv.whatsappsample.data.model.User
import com.shakiv.whatsappsample.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val usersStateFlow = _usersStateFlow.asStateFlow().cachedIn(viewModelScope)


    fun getAllUserData(query: String) {
        viewModelScope.launch {
            userRepository.getUsers(query).collectLatest {
                _usersStateFlow.emit(it)
            }

        }
    }

    fun getUserById(userId: String) = userRepository.getUserId(userId)

    fun addUser(user: List<User>) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

}