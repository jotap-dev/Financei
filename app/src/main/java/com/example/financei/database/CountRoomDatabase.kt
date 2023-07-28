package com.example.financei.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.financei.MainActivity
import com.example.financei.database.daos.CountDao
import com.example.financei.database.models.Count
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(entities = [Count::class], version = 1, exportSchema = false )
abstract class CountRoomDatabase : RoomDatabase() {

    abstract fun countDao() : CountDao

    companion object {
        @Volatile
        private var INSTANCE: CountRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CountRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CountRoomDatabase::class.java,
                    "word_database"
                ).addCallback(CountDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class CountDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.countDao())
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun populateDatabase(countDao: CountDao) {
            // Delete all content here.
            countDao.deleteAll()

            // Add sample counts.
            var word = Count( 0,200.00, "Alimento", setDateNow())
            countDao.insert(word)
            word = Count( 0,450.50, "Alimento", setDateNow())
            countDao.insert(word)
            word = Count( 0,80.50, "Manutenção", setDateNow())
            countDao.insert(word)
            word = Count( 0,45.00, "Entretenimento", setDateNow())
            countDao.insert(word)
        }
        @RequiresApi(Build.VERSION_CODES.O)
        fun setDateNow(): String {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyy"))
        }
    }

}