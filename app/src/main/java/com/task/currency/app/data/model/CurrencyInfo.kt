package com.task.currency.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text
import java.sql.Date

@Entity(tableName = "currency_table")
data class CurrencyInfo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "fromCurrency") val from: String,
    @ColumnInfo(name = "toCurrency") val to: String,
    @ColumnInfo(name = "rate") val rate: String,
)
