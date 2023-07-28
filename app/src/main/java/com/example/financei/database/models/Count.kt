package com.example.financei.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Count(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val price: Double,
    @ColumnInfo val category: String,
    @ColumnInfo val date: String
)