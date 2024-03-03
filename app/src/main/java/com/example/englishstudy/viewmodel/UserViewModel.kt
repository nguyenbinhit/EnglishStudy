package com.example.englishstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application.applicationContext)

    // get user by id
    fun getUser(id: Int): LiveData<User> {
        return userRepository.getUser(id)
    }

    // create user
    public fun insert(user: User) {
        viewModelScope.launch {
            userRepository.createUser(user)
        }
    }

    // update user
    public fun updateUser(user: User): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value = userRepository.updateUser(user)
        }

        return result
    }

    // delete user
    public fun delete(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    // login user
    public suspend fun loginUser(email: String, password: String): User? {
        return userRepository.loginUser(email, password)
    }

    // check user
    public suspend fun checkUser(email: String): User? {
        return userRepository.checkUser(email)
    }

    // update point user
    public fun updatePointUser(id: Int, point: Int) = viewModelScope.launch {
        userRepository.updatePointUser(id, point)
    }

    // list user rank
    public fun listUserRank() = userRepository.listUserRank()
}