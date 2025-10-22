package com.so.chat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.so.chat.model.user.LocalUser
import com.so.chat.repository.MessageRepository
import com.so.chat.utils.ServerSocketManager
import com.so.chat.utils.UserIdentityManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ServerUsersListViewModel (private val socketManager: ServerSocketManager, private val userIdentityManager: UserIdentityManager,
    private val repository: MessageRepository
    ): ViewModel() {

    private val _connectedUsers = MutableStateFlow<List<LocalUser>>(emptyList())
    val connectedUsers: StateFlow<List<LocalUser>> = _connectedUsers
    var user by mutableStateOf<LocalUser?>(null)
        private set

    fun getAllUsers() = repository.getAllUsers().distinctUntilChanged()

    fun disconnect() = socketManager.stopServer()

    init {
        viewModelScope.launch {
            user = userIdentityManager.getUser()
        }
        observeConnections()
    }

    private fun observeConnections() {
        viewModelScope.launch {
            socketManager.connectedUserFlow.collect { userList ->
                _connectedUsers.value = userList
            }
        }
    }
}