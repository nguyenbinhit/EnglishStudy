package com.example.englishstudy.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.englishstudy.model.entity.CauTracNghiem

@Dao
interface CauTracNghiemDAO {
    // get list cau trac nghiem
    @Query("SELECT * FROM cau_trac_nghiem ORDER BY id DESC")
    fun getListCauTracNghiem(): LiveData<List<CauTracNghiem>>

    // get list cau trac nghiem by id bo
    @Query("SELECT * FROM cau_trac_nghiem WHERE id_bo = :idBo ORDER BY id DESC")
    fun getListCauTracNghiemByIdBo(idBo: Int): LiveData<List<CauTracNghiem>>

    // create cau trac nghiem
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCauTracNghiem(cauTracNghiem: CauTracNghiem)

    // get cau trac nghiem detail
    @Query("SELECT * FROM cau_trac_nghiem WHERE id = :id")
    fun getCauTracNghiemDetail(id: Int): CauTracNghiem

    // update cau trac nghiem detail
    @Update
    suspend fun updateCauTracNghiem(cauTracNghiem: CauTracNghiem)

    // delete cau trac nghiem
    @Delete
    suspend fun deleteCauTracNghiem(cauTracNghiem: CauTracNghiem)

    // delete all cau trac nghiem
    @Query("DELETE FROM cau_trac_nghiem")
    fun deleteAll()

    // insert list cau trac nghiem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg cauTracNghiems: CauTracNghiem)
}