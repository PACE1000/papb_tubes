package com.example.papb_tubes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import kotlinx.coroutines.flow.first


class DataStoreManager(private val context: Context) {

    suspend fun addUser(Username: String, Password: String ){
        context.datastore.edit{
                preferences ->
            preferences[COUNTER_USERNAME]= Username
            preferences[COUNTER_PASSWORD]= Password
        }
    }

    suspend fun updateUser(nama:String, tanggal:String, alamat:String){
        context.datastore.edit { preferences->
            preferences[COUNTER_NAMA] = nama
            preferences[COUNTER_TANGGAL]= tanggal
            preferences[COUNTER_ALAMAT] = alamat
        }
    }

    suspend fun getUserData(Username: String, Password: String): UserModel?{
        val preferences = context.datastore.data.first()
        val storedUsername = preferences[COUNTER_USERNAME]
        val storedPassword = preferences[COUNTER_PASSWORD]

        if (storedUsername == Username && storedPassword == Password) {
            val nama = preferences[COUNTER_NAMA] ?: ""
            val alamat = preferences[COUNTER_ALAMAT] ?: ""
            val tanggal = preferences[COUNTER_TANGGAL] ?: ""
            return UserModel(Username, Password, nama, alamat, tanggal)
        }
        return null
    }

    suspend fun getNama(Username: String?):String{
        val preferences = context.datastore.data.first()
        val userstore = preferences[COUNTER_USERNAME]

        if(Username == userstore){
            return preferences[COUNTER_USERNAME]?:""
        }
        return ""
    }

    suspend fun getAlamat(Username:String?):String{
        val preferences = context.datastore.data.first()
        val userstore = preferences[COUNTER_USERNAME]

        if(Username == userstore){
            return preferences[COUNTER_ALAMAT]?:""
        }
        return ""
    }

    suspend fun getTanggal(Username: String?):String{
        val preferences = context.datastore.data.first()
        val userstore = preferences[COUNTER_USERNAME]

        if(userstore == Username){
            return preferences[COUNTER_TANGGAL]?:""
        }
        return ""
    }


    companion object{
        val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = "counter")
        private val COUNTER_USERNAME = stringPreferencesKey("username")
        private val COUNTER_PASSWORD = stringPreferencesKey("password")
        private val COUNTER_ALAMAT = stringPreferencesKey("alamat")
        private val COUNTER_TANGGAL = stringPreferencesKey("tanggal")
        private val COUNTER_NAMA = stringPreferencesKey("nama")
    }
}