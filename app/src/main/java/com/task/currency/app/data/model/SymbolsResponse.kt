package com.task.currency.app.data.model

data class SymbolsResponse(
    var success: Boolean,
    var symbols: Map<String, String>,
)