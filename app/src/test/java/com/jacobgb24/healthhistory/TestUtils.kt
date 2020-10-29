package com.jacobgb24.healthhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.Assert
import kotlin.reflect.KClass


/**
 * Allows asserting the value of any live data by wrapping the assertion in an observable.
 * Automatically interprets null boolean return in assertion as false
 */
fun <T> LiveData<T>.assertValue(pred: (T) -> Boolean?) {
    val obs = Observer<T> {
        println("${it.toString()} -> ${pred.invoke(it)}")
        Assert.assertTrue(pred.invoke(it) ?: false)
    }
    this.observeForever(obs)
    this.removeObserver(obs)
}

/**
 * Helper function to bind an observer to all data passed in. This is necessary as values
 * don't update unless they are being observed. So if x depends on y, we need to observe
 * y for x to update
 */
fun bindObserver(vararg datas: LiveData<out Any?>) {
    for (d in datas) {
        d.observeForever {}
    }
}

fun assertThrows(exception: KClass<out Exception>, code: () -> Unit) {
    try {
        code.invoke()
        Assert.fail("Expected Exception ${exception::class.simpleName} to be raised, but non thrown")
    } catch (e: Exception) {
        if (!exception.isInstance(e)) {
            Assert.fail("Expected Exception ${exception::class.simpleName} to be raised, but ${e::class.simpleName} thrown")
        }
    }
}