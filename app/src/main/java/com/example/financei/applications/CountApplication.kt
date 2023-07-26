package com.example.financei.applications

import android.app.Application
import com.example.financei.database.CountRoomDatabase
import com.example.financei.repositories.CountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CountApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {  CountRoomDatabase.getDatabase(scope = applicationScope, context = this)}
    val repository by lazy { CountRepository(database.countDao()) }

}