package com.so.chat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.chat.model.user.LocalUser
import com.so.chat.repository.MessageRepository
import com.so.chat.utils.UserIdentityManager
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class LocalUserChatViewModel (private val identityManager: UserIdentityManager, private val repository: MessageRepository): ViewModel() {

    var user by mutableStateOf<LocalUser?>(null)
        private set

    init {
        viewModelScope.launch {
            user = identityManager.getUser()
        }
    }

    fun getUserMessages(id: String) = repository.getMessages(id).distinctUntilChanged()
}