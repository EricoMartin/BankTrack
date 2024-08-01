package com.example.banktrack.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.banktrack.data.models.BankDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDetailDao {

    @Query("SELECT * from bank_details WHERE accountName Like :acctName Limit 1")
    fun getBankDetail(acctName: String): BankDetail

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBankDetail(detail: BankDetail)

    @Query("SELECT * from bank_details ORDER BY accountName ASC")
    fun getAllBankDetails(): Flow<List<BankDetail>>

    @Query("DELETE FROM bank_details")
    suspend fun deleteAll()

}