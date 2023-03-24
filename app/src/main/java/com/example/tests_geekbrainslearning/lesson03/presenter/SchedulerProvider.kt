package com.example.tests_geekbrainslearning.lesson03.presenter

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}
