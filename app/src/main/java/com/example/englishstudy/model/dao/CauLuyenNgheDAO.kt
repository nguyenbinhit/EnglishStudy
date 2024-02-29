package com.example.englishstudy.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.englishstudy.model.entity.CauLuyenNghe

@Dao
interface CauLuyenNgheDAO {
    // get list cau luyen nghe
    @Query("SELECT * FROM cau_luyen_nghe ORDER BY id DESC")
    fun getListCauLuyenNghe(): LiveData<List<CauLuyenNghe>>

    // get list cau luyen nghe by id bo
    @Query("SELECT * FROM cau_luyen_nghe WHERE id_bo = :idBo ORDER BY id DESC")
    fun getListCauLuyenNgheByIdBo(idBo: Int): LiveData<List<CauLuyenNghe>>

    // create cau luyen nghe
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCauLuyenNghe(cauLuyenNghe: CauLuyenNghe)

    // get cau luyen nghe detail
    @Query("SELECT * FROM cau_luyen_nghe WHERE id = :id")
    fun getCauLuyenNgheDetail(id: Int): CauLuyenNghe

    // update cau luyen nghe detail
    @Update
    suspend fun updateCauLuyenNghe(cauLuyenNghe: CauLuyenNghe)

    // delete cau luyen nghe
    @Delete
    suspend fun deleteCauLuyenNghe(cauLuyenNghe: CauLuyenNghe)

    // get list cau luyen nghe by id
    @Query("SELECT * FROM cau_luyen_nghe WHERE id = :id")
    fun getListCauLuyenNgheById(id: Int): CauLuyenNghe
}