package com.lexgorbunov.gisthub.app.di

import com.lexgorbunov.gisthub.gists.router.GistsRouter
import com.lexgorbunov.gisthub.gists.router.GistsRouterImpl
import dagger.Binds
import dagger.Module

@Module
interface RouterBinds {

    @Binds
    fun bindGistsRouter(router: GistsRouterImpl): GistsRouter
}
