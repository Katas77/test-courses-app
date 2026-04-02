package com.example.test_courses_app.di


import com.example.test_courses_app.domain.repository.CoursesRepository
import com.example.test_courses_app.domain.repository.CoursesRepositoryImpl
import com.example.test_courses_app.presentation.auth.LoginViewModel
import com.example.test_courses_app.presentation.favorite.FavoriteViewModel
import com.example.test_courses_app.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::FavoriteViewModel)
    
    single<CoursesRepository> { CoursesRepositoryImpl(get(), get()) }
}