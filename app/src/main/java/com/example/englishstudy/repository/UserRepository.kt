package com.example.englishstudy.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.englishstudy.model.database.StudyEnglishDB
import com.example.englishstudy.model.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(val context: Context) {
    private val studyEnglishDB = StudyEnglishDB.getInstance(context)
    private val userDao = studyEnglishDB.userDao()

    // get user by id
    fun getUser(id: Int): LiveData<User> {
        return userDao.getUserDetail(id)
    }

    // create user
    public suspend fun createUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    // update user
    public suspend fun updateUser(user: User): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                userDao.updateUser(user)
            }

            true
        } catch (e: Exception) {
            false
        }
    }

    // delete user
    public suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.deleteUser(user)
        }
    }

    // login user
    public suspend fun loginUser(email: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.loginUser(email, password)
        }
    }

    // check user
    public suspend fun checkUser(email: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.checkUser(email)
        }
    }

    // update point user
    public suspend fun updatePointUser(id: Int, point: Int) {
        withContext(Dispatchers.IO) {
            userDao.updatePointUser(id, point)
        }
    }

    // list user rank
    public fun listUserRank() = userDao.listUserRank()
}