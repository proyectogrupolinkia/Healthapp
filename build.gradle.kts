plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    //plugin de google services necesario para firebase
    id("com.google.gms.google-services") version "4.4.2" apply false
}

