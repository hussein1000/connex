package com.so.chat.di

import android.content.Context
import androidx.compose.runtime.rememberCoroutineScope
import androidx.room.Room
import com.so.chat.database.AppDatabase
import com.so.chat.database.Converters
import com.so.chat.repository.ChatRepository
import com.so.chat.repository.MessageRepository
import com.so.chat.repository.UserRepository
import com.so.chat.screens.ClientUserChatScreen
import com.so.chat.screens.LocalUserListScreen
import com.so.chat.utils.ClientSocketManager
import com.so.chat.utils.ServerSocketManager
import com.so.chat.utils.UserIdentityManager
import com.so.chat.utils.provideDataStore
import com.so.chat.viewmodel.ClientUserChatViewModel
import com.so.chat.viewmodel.ClientUsersListViewModel
import com.so.chat.viewmodel.ConnectingViewModel
import com.so.chat.viewmodel.HostSetupViewModel
import com.so.chat.viewmodel.JoinServerViewModel
import com.so.chat.viewmodel.LocalUserChatViewModel
import com.so.chat.viewmodel.LocalUserListViewModel
import com.so.chat.viewmodel.ServerUserChatViewModel
import com.so.chat.viewmodel.ServerUsersListViewModel
import com.so.chat.viewmodel.UserIdentityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.getScopeId
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.coroutineContext

val appModule = module {
    single { provideDataStore(androidContext()) }
    single { UserIdentityManager(get()) }

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "so_chat.db")
        .addTypeConverter(Converters())
        .fallbackToDestructiveMigration(false)
        .build() }

    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().chatDao() }
    single { get<AppDatabase>().messageDao() }

    single { UserRepository(get(), get()) }
    single { ChatRepository(get()) }
    single { MessageRepository(get()) }

    single { ClientSocketManager(get<MessageRepository>()) }
    single {
        ServerSocketManager(get<MessageRepository>()) }

    viewModel { UserIdentityViewModel(get()) }
    viewModel { ConnectingViewModel(get()) }
    viewModel { LocalUserListViewModel(get()) }
    viewModel { HostSetupViewModel(get<ServerSocketManager>(), get<UserIdentityManager>()) }
    viewModel { LocalUserChatViewModel(get<UserIdentityManager>(), get<MessageRepository>()) }
    viewModel { JoinServerViewModel(get<ClientSocketManager>(), get<UserIdentityManager>()) }
    viewModel { ClientUsersListViewModel(get<ClientSocketManager>(), get<UserIdentityManager>(), get<MessageRepository>()) }
    viewModel { ServerUsersListViewModel(get<ServerSocketManager>(), get<UserIdentityManager>(), get<MessageRepository>()) }
    viewModel { ClientUserChatViewModel(get<ClientSocketManager>(), get<MessageRepository>(), get<UserIdentityManager>()) }
    viewModel { ServerUserChatViewModel(get<ServerSocketManager>(), get<MessageRepository>(), get<UserIdentityManager>()) }
}