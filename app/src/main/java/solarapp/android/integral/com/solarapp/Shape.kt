package solarapp.android.integral.com.solarapp

sealed class Shape {

    class Circle(var radius: Float) : Shape();
    class Square(var length: Float) : Shape();
    class Rectangle(var length: Float, var height: Float) : Shape()
}

class Sample : Shape() {


}