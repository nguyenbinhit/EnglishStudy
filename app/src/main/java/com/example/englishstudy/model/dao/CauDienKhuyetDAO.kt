package com.example.englishstudy.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.englishstudy.model.entity.CauDienKhuyet

@Dao
interface CauDienKhuyetDAO {
    // get list cau dien khuyet
    @Query("SELECT * FROM cau_dien_khuyet ORDER BY id DESC")
    fun getListCauDienKhuyet(): LiveData<List<CauDienKhuyet>>

    // get list cau dien khuyet by id bo
    @Query("SELECT * FROM cau_dien_khuyet WHERE id_bo = :idBo ORDER BY id DESC")
    fun getListCauDienKhuyetByIdBo(idBo: Int): LiveData<List<CauDienKhuyet>>

    // create cau dien khuyet
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCauDienKhuyet(cauDienKhuyet: CauDienKhuyet)

    // get cau dien khuyet detail
    @Query("SELECT * FROM cau_dien_khuyet WHERE id = :id")
    fun getCauDienKhuyetDetail(id: Int): CauDienKhuyet

    // update cau dien khuyet detail
    @Update
    fun updateCauDienKhuyet(cauDienKhuyet: CauDienKhuyet): Int

    // delete cau dien khuyet
    @Delete
    suspend fun deleteCauDienKhuyet(cauDienKhuyet: CauDienKhuyet)

    // get cau dien khuyet by id
    @Query("SELECT * FROM cau_dien_khuyet LIMIT 1 OFFSET :position")
    fun getCauDienKhuyetAtPosition(position: Int): LiveData<CauDienKhuyet>

    // get cau dien khuyet by id
    @Query("SELECT * FROM cau_dien_khuyet WHERE id = :id")
    fun getCauDienKhuyetById(id: Int): LiveData<CauDienKhuyet>
}