package io.fhate.core.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

object TextUtils {

    /* Format count number like: 2800 = 2.8k, 1400000 = 1.4M, and etc. */
    fun formatPrettyCount(count: Long, roundingMode: RoundingMode = RoundingMode.UP): String {
        val array = arrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val value = floor(log10(count.toDouble())).toInt()
        val base = value / 3

        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator = '.'

        return if (value >= 3 && base < array.size) {
            val df = DecimalFormat("0.#", symbols)
            df.roundingMode = roundingMode
            df.format(count / 10.0.pow((base * 3).toDouble())) + array[base]
        } else {
            val df = DecimalFormat("#,##0.##", symbols)
            df.roundingMode = roundingMode
            df.format(count / 10.0.pow((base * 3).toDouble())) + array[base]
        }
    }

}