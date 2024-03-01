package com.example.englishstudy.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.englishstudy.model.entity.BoHocTap

@Dao
interface BoHocTapDAO {
    // get list bo hoc tap
    @Query("SELECT * FROM bo_hoc_tap ORDER BY id DESC")
    fun getListBoHocTap(): LiveData<List<BoHocTap>>

    // create bo hoc tap
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBoHocTap(boHocTap: BoHocTap)

    // get bo hoc tap detail
    @Query("SELECT * FROM bo_hoc_tap WHERE id = :id")
    fun getBoHocTapDetail(id: Int): BoHocTap

    // update bo hoc tap detail
    @Update
    suspend fun updateBoHocTap(boHocTap: BoHocTap)

    // delete bo hoc tap
    @Delete
    suspend fun deleteBoHocTap(boHocTap: BoHocTap)

    // get a specific bo hoc tap
    @Query("SELECT * FROM bo_hoc_tap LIMIT 1 OFFSET :position")
    fun getBoHocTapAtPosition(position: Int): LiveData<BoHocTap>

    // get max stt bo hoc tap
    @Query("SELECT MAX(stt) FROM bo_hoc_tap")
    fun getMaxSTTBoHocTap(): Int

    // delete all bo hoc tap
    @Query("DELETE FROM bo_hoc_tap")
    fun deleteAll()

    // insert list bo hoc tap
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg boHocTaps: BoHocTap)
}