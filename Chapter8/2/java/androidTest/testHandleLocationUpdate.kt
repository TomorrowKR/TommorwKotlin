package com.example.myapplication

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var context: Context

    @Test
    fun testHandleLocationUpdate() = runTest {
        // Mock 객체 생성
        val mockApi = MockTourGuideApi()
        val mockTtsHelper = TtsHelper(context) // context가 필요하다면 테스트에서 주입하거나 Mocking

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            // Mock 객체 주입
            activity.apiClient = mockApi
            activity.ttsHelper = mockTtsHelper

            // 샌프란시스코 좌표
            runBlocking {
                activity.handleLocationUpdate(37.7749, -122.4194)
            }
        }
    }
}