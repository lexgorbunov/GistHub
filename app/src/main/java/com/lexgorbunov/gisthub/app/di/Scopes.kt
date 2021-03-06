package com.lexgorbunov.gisthub.app.di

import javax.inject.Scope

//@Scope
//@Retention(AnnotationRetention.RUNTIME)
//annotation class ServiceScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope
