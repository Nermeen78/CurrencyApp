package com.task.currency.app.util

import java.text.SimpleDateFormat
import java.util.*

object AppConst {

    const val APIKEY="uh7pyGH66sRItrVDkiW7NLp6tOjuIVML"
    val TODAY_DATE: String = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.ENGLISH
    ).format(Date())
    const val POPULAR_CURRENCIES= "USD,EUR,JPY,GBP,AUD,CAD,CHF,CNH,HKD,NZD"
    const val BASECURRENCY: String="EGP"


}