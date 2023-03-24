package com.example.tests_geekbrainslearning.stubs

import com.example.tests_geekbrainslearning.lesson03.presenter.SchedulerProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class ScheduleProviderStub : SchedulerProvider {
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}
