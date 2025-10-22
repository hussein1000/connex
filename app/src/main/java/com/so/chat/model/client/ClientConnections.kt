package com.so.chat.model.client

import com.so.chat.model.user.LocalUser
import java.io.BufferedReader
import java.io.BufferedWriter
import java.net.Socket
import java.util.UUID

data class ClientConnection(
    val socket: Socket,
    val user: LocalUser,
    var lastHeartbeat: Long = System.currentTimeMillis()
)

