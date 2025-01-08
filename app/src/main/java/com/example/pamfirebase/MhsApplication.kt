package com.example.pamfirebase

import android.app.Application
import com.example.pamfirebase.Container.AppContainer
import com.example.pamfirebase.Container.MahasiswaContainer

class MahasiswaApplications: Application(){
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = MahasiswaContainer()
    }
}