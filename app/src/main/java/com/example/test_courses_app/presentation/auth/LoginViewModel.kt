package com.example.test_courses_app.presentation.auth

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    fun isValidEmail(email: String): Boolean =
        email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))

    fun isFormValid(email: String, password: String): Boolean =
        isValidEmail(email) && password.isNotBlank()
}