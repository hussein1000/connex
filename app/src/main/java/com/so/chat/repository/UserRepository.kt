package com.so.chat.repository

import com.so.chat.database.UserDao
import com.so.chat.model.user.LocalUser
import com.so.chat.model.user.UserEntity
import com.so.chat.utils.UserIdentityManager
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class UserRepository(
    private val userDao: UserDao,
    private val identityManager: UserIdentityManager
) {
    suspend fun getCurrentUser(): LocalUser = identityManager.getUser()

    suspend fun setUsername(username: String) = identityManager.setUsername(username)

    suspend fun saveUser(user: UserEntity) = userDao.insertUser(user)

    fun getAllUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()

    suspend fun getUserById(id: UUID): UserEntity? = userDao.getUserById(id)
}
