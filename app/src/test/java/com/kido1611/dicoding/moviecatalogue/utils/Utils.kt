package com.kido1611.dicoding.moviecatalogue.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope

@ExperimentalCoroutinesApi
fun runTest(testCoroutineRule: TestCoroutineRule, scope: suspend TestCoroutineScope.() -> Unit) {
    testCoroutineRule.runBlockingTest(scope)
}