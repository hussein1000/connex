package com.so.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.chat.model.user.LocalUser
import com.so.chat.utils.UserIdentityManager
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import java.util.UUID

class UserIdentityViewModel (private val userIdentityManager: UserIdentityManager): ViewModel() {

    fun saveUsername(username: String, onComplete: () -> Unit) = viewModelScope.launch {
        val uuid = UUID.randomUUID()
        userIdentityManager.setUsername(username)
        userIdentityManager.setUserId(uuid)
        onComplete()
    }

    fun getUser() = viewModelScope.produce<LocalUser> {
        userIdentityManager.getUser()
    }
}