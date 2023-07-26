package com.example.financei.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.financei.database.daos.CountDao
import com.example.financei.database.models.Count
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
                ).addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.countDao())
                }
            }
        }

        suspend fun populateDatabase(countDao: CountDao) {
            // Delete all content here.
            countDao.deleteAll()

            // Add sample words.
            var word = Count( 200.00, "Alimento")
            countDao.insert(word)
            word = Count( 450.50, "Alimento")
            countDao.insert(word)
            word = Count( 80.50, "Manutenção")
            countDao.insert(word)
            word = Count( 45.00, "Entretenimento")
            countDao.insert(word)
        }
    }

}