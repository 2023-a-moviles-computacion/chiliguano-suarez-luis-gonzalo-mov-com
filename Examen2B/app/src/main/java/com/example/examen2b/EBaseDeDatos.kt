package com.example.examenib

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class EBaseDeDatos {
    companion object{
        var BDatos: ESqliteHelper?=null
        fun getInstance(context: Context): ESqliteHelper {
            if (instance == null) {
                BDatos = ESqliteHelper(context.applicationContext)
            }
            return BDatos!!
        }


    }
}