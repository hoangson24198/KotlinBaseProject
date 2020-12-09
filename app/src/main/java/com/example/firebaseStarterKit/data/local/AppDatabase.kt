package com.atmaneuler.hsdps.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atmaneuler.hsdps.data.local.mappingmachine.MMDetail
import com.atmaneuler.hsdps.data.local.mappingmachine.MMDetailDao
import com.atmaneuler.hsdps.data.local.mappingmachine.MMHeader
import com.atmaneuler.hsdps.data.local.mappingmachine.MMHeaderDao

@Database(
    entities = [LUser::class, MMHeader::class, MMDetail::class],//thêm table vô đây
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun LUserDao(): LUserDao
    abstract fun MMHeaderDao(): MMHeaderDao
    abstract fun MMDetailDao(): MMDetailDao
}