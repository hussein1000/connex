package com.so.chat.utils

import com.so.chat.model.message.ChatMessage
import com.so.chat.model.message.MessageEntity

object Utilities {
    fun ChatMessage.toEntity(): MessageEntity {
        return MessageEntity(
            messageId = messageId,
            chatId = chatId,
            senderId = senderId,
            receiverId = receiverId,
            timestamp = timestamp,
            content = content ?: "",
            contactName = contactName,
            username = username
        )
    }

    fun MessageEntity.toChatMessage(): ChatMessage {
        return ChatMessage(
            type = "MESSAGE", // hardcoded since ACKs removed
            chatId = chatId,
            senderId = senderId,
            receiverId = receiverId,
            timestamp = timestamp,
            content = content,
            messageId = messageId,
            contactName = contactName,
            username = username
        )
    }
}