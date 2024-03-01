package com.example.englishstudy.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.englishstudy.model.dao.BoHocTapDAO
import com.example.englishstudy.model.dao.CauDienKhuyetDAO
import com.example.englishstudy.model.dao.CauLuyenNgheDAO
import com.example.englishstudy.model.dao.CauSapXepDAO
import com.example.englishstudy.model.dao.CauTracNghiemDAO
import com.example.englishstudy.model.dao.TuVungDAO
import com.example.englishstudy.model.dao.UserDAO
import com.example.englishstudy.model.entity.BoHocTap
import com.example.englishstudy.model.entity.CauDienKhuyet
import com.example.englishstudy.model.entity.CauLuyenNghe
import com.example.englishstudy.model.entity.CauSapXep
import com.example.englishstudy.model.entity.CauTracNghiem
import com.example.englishstudy.model.entity.TuVung
import com.example.englishstudy.model.entity.User

@Database(
    entities = [BoHocTap::class, CauDienKhuyet::class, CauLuyenNghe::class, CauSapXep::class, CauTracNghiem::class, User::class, TuVung::class],
    version = 4,
    exportSchema = false
)
abstract class StudyEnglishDB : RoomDatabase() {
    abstract fun boHocTapDao(): BoHocTapDAO
    abstract fun cauDienKhuyetDao(): CauDienKhuyetDAO
    abstract fun cauLuyenNgheDao(): CauLuyenNgheDAO
    abstract fun cauSapXepDao(): CauSapXepDAO
    abstract fun cauTracNghiemDao(): CauTracNghiemDAO
    abstract fun userDao(): UserDAO
    abstract fun tuVungDao(): TuVungDAO

    companion object {
        const val DATABASE_NAME = "english_study.db"

        private var instance: StudyEnglishDB? = null

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn't alter the table, there's nothing else to do here.
            }
        }

        public fun getInstance(context: Context): StudyEnglishDB {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyEnglishDB::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_3_4)
                    .build()
            }

            return instance!!
        }
    }
}