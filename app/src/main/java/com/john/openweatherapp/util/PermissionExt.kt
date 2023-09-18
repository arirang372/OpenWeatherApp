
package com.john.openweatherapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 *  extension methods used for requesting permission or checking permissions
 *
 */
fun AppCompatActivity.checkSelfPermissionCompat(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission)


fun AppCompatActivity.requestPermissionsCompat(permissionsArray: Array<String>,
                                               requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}

