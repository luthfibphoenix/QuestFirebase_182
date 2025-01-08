package com.example.pamfirebase.repository

import com.example.pamfirebase.Model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface MahasiswaRepository {
    suspend fun getAllMahasiswa(): Flow<List<Mahasiswa>>

    suspend fun getMahasiswaByID(nim: String): Flow<Mahasiswa>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)

    suspend fun deleteMahasiswa(nim: String)
}