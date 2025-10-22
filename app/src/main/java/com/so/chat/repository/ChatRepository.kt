package com.so.chat.repository

import com.so.chat.database.ChatSessionDao
import com.so.chat.model.chat.ChatSessionEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ChatRepository(
    private val chatDao: ChatSessionDao
) {
    suspend fun saveChatSession(session: ChatSessionEntity) = chatDao.insertSession(session)

    suspend fun getChatById(id: UUID): ChatSessionEntity? = chatDao.getChatById(id)

    fun getAllChatSessions(): Flow<List<ChatSessionEntity>> = chatDao.getAllSessions()
}
