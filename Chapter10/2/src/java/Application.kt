package com.example.myapplication

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "KAKAO_NATIVE_APP_KEY")
        NaverIdLoginSDK.initialize(this, "Client ID", "Client Secret", "Client Name")
    }
}