package com.example.firebaseStarterKit.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    const val NORMAL_DATE_FORMAT = "MM/dd/yyyy"
    const val IMAGE_DATE_FORMAT = "yyyyMMdd_HHmmss"
    const val RECEIPT_DATE_FORMAT = "MMddyyyy"
    const val MAPPING_DATE_FORMAT = "yyyy.MM.dd"
    const val KEEPING_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    fun format(date: Date, format: String): String {
        return SimpleDateFormat(format).format(date)
    }
}


fun Date.keepingFormat(): String {
    return DateHelper.format(this, DateHelper.KEEPING_DATE_FORMAT)
}


fun Date.normalDateFormat(): String {
    return DateHelper.format(this, DateHelper.NORMAL_DATE_FORMAT)
}

fun Date.imageDateFormat(): String {
    return DateHelper.format(this, DateHelper.IMAGE_DATE_FORMAT)
}

fun Date.receiptDateFormat(): String {
    return DateHelper.format(this, DateHelper.RECEIPT_DATE_FORMAT)
}

fun Date.mappingDateFormat(): String {
    return DateHelper.format(this, DateHelper.MAPPING_DATE_FORMAT)
}