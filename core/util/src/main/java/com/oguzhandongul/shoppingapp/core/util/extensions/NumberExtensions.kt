package com.oguzhandongul.shoppingapp.core.util.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


fun Double.toCurrencyString(symbol: String? = null): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        decimalSeparator = ','
        groupingSeparator = '.'
    }
    val decimalFormat = DecimalFormat("#,##0.00", symbols)
    val formattedValue = decimalFormat.format(this)

    return if (symbol == null) decimalFormat.format(this) else "$formattedValue $symbol"
}
