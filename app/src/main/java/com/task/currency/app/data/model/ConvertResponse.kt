package com.task.currency.app.data.model

data class ConvertResponse(
    var date: String,
    var historical: String,
    var info: Info,
    var query: Query,
    var result: Double,
    var success: Boolean,
)