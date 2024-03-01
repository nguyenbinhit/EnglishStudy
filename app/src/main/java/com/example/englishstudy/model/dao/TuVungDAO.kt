package com.example.englishstudy.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.englishstudy.model.entity.TuVung

@Dao
interface TuVungDAO {
    // get list tu vung
    @Query("SELECT * FROM tu_vung ORDER BY id DESC")
    fun getListTuVung(): LiveData<List<TuVung>>

    // get tu vung by id
    @Query("SELECT * FROM tu_vung WHERE id = :id")
    fun getTuVungById(id: Int): TuVung

    // get tu vung by id bo
    @Query("SELECT * FROM tu_vung WHERE id_bo = :idBo ORDER BY id DESC")
    fun getListTuVungByIdBo(idBo: Int): LiveData<List<TuVung>>

    // create tu vung
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTuVung(tuVung: TuVung)

    // update tu vung
    @Update
    suspend fun updateTuVung(tuVung: TuVung)

    // delete tu vung
    @Delete
    suspend fun deleteTuVung(tuVung: TuVung)

    // delete all tu vung
    @Query("DELETE FROM tu_vung")
    fun deleteAll()

    // insert list tu vung
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg tuVungs: TuVung)
}