package com.example.pamfirebase.Container

import com.example.pamfirebase.repository.MahasiswaRepository
import com.example.pamfirebase.repository.NetworkMahasiswaRespository
import com.google.firebase.firestore.FirebaseFirestore


interface AppContainer{
    val mahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer: AppContainer{

    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()

    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRespository(firebase)
    }
}