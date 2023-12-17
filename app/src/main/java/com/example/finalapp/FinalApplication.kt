package com.example.finalapp

import android.app.Application
import com.example.finalapp.repository.FinalAppRepository

class FinalApplication : Application() {
    private lateinit var repository: FinalAppRepository
    override fun onCreate() {
        super.onCreate()
        repository = FinalAppRepository(this)
    }

    fun getRepository(): FinalAppRepository {
        return repository
    }

}