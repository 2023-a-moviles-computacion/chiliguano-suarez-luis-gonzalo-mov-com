package com.example.examenib

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class EBaseDeDatos {
    companion object{
        var coBDatos: ESqliteHelper?=null
        fun getInstance(context: Context): ESqliteHelper {
            if (instance == null) {
                coBDatos = ESqliteHelper(context.applicationContext)
            }
            return coBDatos!!
        }


    }
}