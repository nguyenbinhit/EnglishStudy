package com.example.englishstudy.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.englishstudy.model.entity.User

@Dao
interface UserDAO {
    // get all user
    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<User>>

    // create user
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    // get user detail
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserDetail(id: Int): LiveData<User>

    // update user detail
    @Update
    suspend fun updateUser(user: User)

    // delete user
    @Delete
    suspend fun deleteUser(user: User)

    // check tai khoan
    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun checkUser(email: String): User?

    // update point user
    @Query("UPDATE user SET point = :point WHERE id = :id")
    fun updatePointUser(id: Int, point: Int): Int

    // login user
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): User?

    // list user rank
    @Query("SELECT * FROM user ORDER BY point DESC")
    fun listUserRank(): LiveData<List<User>>

    // get current user
    @Query("SELECT * FROM user WHERE id = :id")
    fun getCurrentUser(id: Int): LiveData<User>

    // delete all user
    @Query("DELETE FROM user")
    fun deleteAll()

    // insert list user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: User)
}