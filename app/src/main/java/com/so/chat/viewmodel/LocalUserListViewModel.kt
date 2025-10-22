package com.so.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.so.chat.repository.MessageRepository
import kotlinx.coroutines.flow.distinctUntilChanged

class LocalUserListViewModel (private val repository: MessageRepository): ViewModel() {

    fun getAllUsers() = repository.getAllUsers().distinctUntilChanged()
}