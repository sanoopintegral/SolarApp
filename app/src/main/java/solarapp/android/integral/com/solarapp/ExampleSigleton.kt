package solarapp.android.integral.com.solarapp

object ExampleSigleton {

    val User by lazy { solarapp.android.integral.com.solarapp.User().apply { name = "Sanoop" } }
}