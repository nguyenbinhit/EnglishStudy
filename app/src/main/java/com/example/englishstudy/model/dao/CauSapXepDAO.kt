package com.example.englishstudy.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.englishstudy.model.entity.CauSapXep

@Dao
interface CauSapXepDAO {
    // get list cau sap xep
    @Query("SELECT * FROM cau_sap_xep ORDER BY id DESC")
    fun getListCauSapXep(): LiveData<List<CauSapXep>>

    // get list cau sap xep by id bo
    @Query("SELECT * FROM cau_sap_xep WHERE id_bo = :idBo ORDER BY id DESC")
    fun getListCauSapXepByIdBo(idBo: Int): LiveData<List<CauSapXep>>

    // create cau sap xep
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCauSapXep(cauSapXep: CauSapXep)

    // get cau sap xep detail
    @Query("SELECT * FROM cau_sap_xep WHERE id = :id")
    fun getCauSapXepDetail(id: Int): CauSapXep

    // update cau sap xep detail
    @Update
    suspend fun updateCauSapXep(cauSapXep: CauSapXep)

    // delete cau sap xep
    @Delete
    suspend fun deleteCauSapXep(cauSapXep: CauSapXep)

    // get cau sap xep by id
    @Query("SELECT * FROM cau_sap_xep WHERE id = :id")
    fun getCauSapXepById(id: Int): CauSapXep
}