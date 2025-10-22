package com.so.chat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.chat.utils.ServerSocketManager
import com.so.chat.utils.UserIdentityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HostSetupViewModel (
    private val socketManager: ServerSocketManager,
    private val userIdentityManager: UserIdentityManager
): ViewModel() {

    var password by mutableStateOf("")
    var serverStarted by mutableStateOf(false)
    var serverIp by mutableStateOf("")

    init {
        viewModelScope.launch {
            serverIp = socketManager.getLocalIpAddress()
        }
    }

    fun onPasswordChanged(new: String) {
        password = new
    }


    fun startServer(onStarted: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val user = userIdentityManager.getUser()
            try {
                socketManager.startServer(password = password, hostUser = user, scope = viewModelScope)
                serverStarted = true
                onStarted()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to start server")
            }
        }
    }
}