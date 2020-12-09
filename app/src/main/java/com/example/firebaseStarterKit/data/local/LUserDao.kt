package com.atmaneuler.hsdps.data.local

import androidx.room.*

@Dao
interface LUserDao {
    @Query("SELECT * FROM luser")
    fun getAll(): List<LUser>

    @Query("SELECT * FROM luser WHERE username LIKE :username")
    fun findByUsername(username: String): LUser

    @Query("SELECT * FROM luser WHERE nfc LIKE :nfc")
    fun findByNFC(nfc: String): LUser

    @Insert
    fun insertAll(vararg todo: LUser)

    @Delete
    fun delete(todo: LUser)

    @Update
    fun update(vararg todos: LUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: LUser)
}