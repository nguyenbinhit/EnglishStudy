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
import java.util.concurrent.Executors

@Database(
    entities = [BoHocTap::class, CauDienKhuyet::class, CauLuyenNghe::class, CauSapXep::class, CauTracNghiem::class, User::class, TuVung::class],
    version = 5,
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
                val callback = object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        Executors.newSingleThreadExecutor().execute {
                            // User
                            createDataUser(instance!!)

                            // Bo hoc tap
                            createDataBoHocTap(instance!!)

                            // Cau dien khuyet
                            createDataDienKhuyet(instance!!)

                            // luyen nghe

                            // sap xep
                            createDataSapXep(instance!!)

                            // trac nghiem
                            createDataTracNghiem(instance!!)

                            // tu vung
                            createDataTuVung(instance!!)
                        }
                    }
                }

                instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyEnglishDB::class.java,
                    DATABASE_NAME
                )
                    .addCallback(callback)
                    .build()
            }

            return instance!!
        }

        // create data user
        private fun createDataUser(database: StudyEnglishDB) {
            val userDao = database.userDao()
            userDao?.deleteAll()
            val user1 = User("admin", "admin@gmail.com", "admin", 0, 1)
            val user2 = User("client", "client@gmail.com", "client", 0, 0)
            userDao?.insert(user1, user2)
        }

        // create bo hoc tap
        private fun createDataBoHocTap(database: StudyEnglishDB) {
            val boHocTapDao = database.boHocTapDao()
            boHocTapDao?.deleteAll()
            val boHocTap1 = BoHocTap(1, "Bộ học tập số 1")
            val boHocTap2 = BoHocTap(2, "Bộ học tập số 2")
            val boHocTap3 = BoHocTap(3, "Bộ học tập số 3")
            val boHocTap4 = BoHocTap(4, "Bộ học tập số 4")
            boHocTapDao?.insert(boHocTap1, boHocTap2, boHocTap3, boHocTap4)
        }

        // create data dien khuyet
        private fun createDataDienKhuyet(database: StudyEnglishDB) {
            val cauDienKhuyetDao = database.cauDienKhuyetDao()
            cauDienKhuyetDao?.deleteAll()
            val cauDienKhuyet1 = CauDienKhuyet(1, "The frog can___", "jump", "eat run can jump")
            val cauDienKhuyet2 = CauDienKhuyet(1, "The duck___swim", "can", "eat run can jump")
            val cauDienKhuyet3 =
                CauDienKhuyet(1, "The rabbit likes to___carrots", "eat", "eat run can jump")
            val cauDienKhuyet4 = CauDienKhuyet(1, "My dogs___fast", "run", "eat run can jump")
            val cauDienKhuyet5 = CauDienKhuyet(2, "Our family listen to___", "music", "music dance")
            val cauDienKhuyet6 = CauDienKhuyet(2, "My parent like to___", "dance", "music dance")
            val cauDienKhuyet7 = CauDienKhuyet(3, "I sit at the___", "piano", "piano guitar")
            val cauDienKhuyet8 =
                CauDienKhuyet(3, "My brother plays an electric___", "guitar", "piano guitar")
            val cauDienKhuyet9 =
                CauDienKhuyet(4, "My little brother bangs on the snare___", "drum", "drum, new")
            val cauDienKhuyet10 = CauDienKhuyet(4, "She has a ___ dress", "new", "drum, new")

            cauDienKhuyetDao?.insert(
                cauDienKhuyet1,
                cauDienKhuyet2,
                cauDienKhuyet3,
                cauDienKhuyet4,
                cauDienKhuyet5,
                cauDienKhuyet6,
                cauDienKhuyet7,
                cauDienKhuyet8,
                cauDienKhuyet9,
                cauDienKhuyet10
            )
        }

        // create data luyen nghe

        // create data sap xep
        private fun createDataSapXep(database: StudyEnglishDB) {
            val cauSapXepDao = database.cauSapXepDao()
            cauSapXepDao?.deleteAll()
            val cauSapXep1 = CauSapXep(
                1,
                "They are required to inform the human resources department when resigning due to a disagreement over company policy.",
                "They are required to inform",
                "the human resources department",
                "when resigning due to",
                "a disagreement over company policy."
            )
            val cauSapXep2 = CauSapXep(
                1,
                "All the important files were organized first by color and then alphabetized by the title and name.",
                "All the important files",
                "were organized first",
                "by color and then alphabetized",
                "by the title and name."
            )
            val cauSapXep3 =
                CauSapXep(
                    1,
                    "Many companies interviewed plan to hire more personnel, while 20 percent expect to reduce their payrolls.",
                    "Many companies interviewed",
                    "plan to hire more personnel,",
                    "while 20 percent expect",
                    "to reduce their payrolls."
                )
            val cauSapXep4 = CauSapXep(
                1,
                "Employees who wish to enroll in the marketing seminar are urged to do so by this Friday.",
                "Employees",
                "who wish to enroll in",
                "the marketing seminar",
                "are urged to do so by this Friday."
            )
            val cauSapXep5 = CauSapXep(
                1,
                "The secretary in the 2nd flood office answers e-mails between 8 a.m. and noon.",
                "The secretary",
                "in the 2nd flood office",
                "answers e-mails",
                "between 8 a.m. and noon."
            )

            cauSapXepDao?.insert(
                cauSapXep1,
                cauSapXep2,
                cauSapXep3,
                cauSapXep4,
                cauSapXep5
            )
        }

        // create data trac nghiem
        private fun createDataTracNghiem(database: StudyEnglishDB) {
            val tracNghiemDAO = database.cauTracNghiemDao()
            tracNghiemDAO?.deleteAll()

            val tracNghiem1 = CauTracNghiem(
                1,
                "They are required to inform the human resources department when resigning due .......... a disagreement over company policy.",
                "to",
                "by",
                "on",
                "for",
                "1"
            )

            val tracNghiem2 = CauTracNghiem(
                1,
                "All the important files were organized first by color and .......... alphabetized by the title and name.",
                "since",
                "here",
                "then",
                "much",
                "3"
            )

            tracNghiemDAO?.insert(tracNghiem1, tracNghiem2)
        }

        // create data tu vung
        private fun createDataTuVung(database: StudyEnglishDB) {
            val tuVungDao = database.tuVungDao()
            tuVungDao?.deleteAll()
            val tuVung1 = TuVung(
                1,
                "president",
                "Tổng thống",
                "Danh từ",
                "https://github.com/nguyenbinhit/Android/blob/main/TuVung_Bo01_CSDL_TuVung_Bo01_president.mp3",
                null
            )
            val tuVung2 = TuVung(
                1,
                "customer",
                "Khách hàng",
                "Danh từ",
                "https://github.com/nguyenbinhit/Android/blob/main/TuVung_Bo01_CSDL_TuVung_Bo01_customer.mp3",
                null,
            )
            val tuVung3 = TuVung(
                1,
                "purchase",
                "Mua",
                "Động từ",
                "https://github.com/nguyenbinhit/Android/blob/main/TuVung_Bo01_CSDL_TuVung_Bo01_purchase.mp3",
                null,
            )
            val tuVung4 = TuVung(
                1,
                "item",
                "Món hàng",
                "Danh từ",
                "https://github.com/nguyenbinhit/Android/blob/main/TuVung_Bo01_CSDL_TuVung_Bo01_item.mp3",
                null,
            )
            val tuVung5 = TuVung(
                1,
                "consultant",
                "Tư vấn viên",
                "Danh từ",
                "https://github.com/nguyenbinhit/Android/blob/main/TuVung_Bo01_CSDL_TuVung_Bo01_consultant.mp3",
                null,
            )

            tuVungDao?.insert(
                tuVung1,
                tuVung2,
                tuVung3,
                tuVung4,
                tuVung5,
            )
        }
    }
}