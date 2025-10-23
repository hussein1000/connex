package com.so.chat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.so.chat.model.chat.ChatSessionEntity
import com.so.chat.model.message.MessageEntity
import com.so.chat.model.user.LocalUser
import com.so.chat.model.user.UserEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE uuid = :uuid")
    suspend fun getUserById(uuid: UUID): UserEntity?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>
}

@Dao
interface ChatSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ChatSessionEntity)

    @Query("SELECT * FROM chat_sessions WHERE chatId = :id")
    suspend fun getChatById(id: UUID): ChatSessionEntity?

    @Query("SELECT * FROM chat_sessions")
    fun getAllSessions(): Flow<List<ChatSessionEntity>>
}

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    fun getMessagesForChat(chatId: UUID): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("SELECT chatId AS uuid, contactName AS username FROM messages GROUP BY chatId")
    fun getAllUsers(): Flow<List<LocalUser>>

    // 🗑️ Delete all messages for a specific chat
    @Query("DELETE FROM messages WHERE chatId = :chatId")
    suspend fun deleteMessagesByChatId(chatId: UUID)
}