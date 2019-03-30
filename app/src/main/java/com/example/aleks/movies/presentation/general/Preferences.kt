package com.example.aleks.movies.presentation.general

import android.content.Context
import com.google.gson.Gson

object Preferences{

    fun saveGenreToSharedPreference(context: Context, preferenceFileName: String, serializedObjectKey: String, `object`: Any) {
        val sharedPreferences = context.getSharedPreferences(preferenceFileName, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        val gson = Gson()
        val serializedObject = gson.toJson(`object`)
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject)
        sharedPreferencesEditor.apply()
    }

    fun <GenericClass> getSavedObjectFromPreference(context: Context, preferenceFileName: String, preferenceKey: String, classType: Class<GenericClass>): GenericClass? {
        val sharedPreferences = context.getSharedPreferences(preferenceFileName, 0)
        if (sharedPreferences.contains(preferenceKey)) {
            val gson = Gson()
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType)
        }
        return null
    }
}