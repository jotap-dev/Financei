package com.example.financei.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Count(
    @PrimaryKey @ColumnInfo val price: Double,
    @ColumnInfo val category: String
)