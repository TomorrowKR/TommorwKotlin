package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val signUpButton: Button = findViewById(R.id.signUpButton)

        // 로그인 버튼 클릭 리스너
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            signInWithEmail(email, password)
        }

        // 회원가입 버튼 클릭 리스너
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            signUpWithEmail(email, password)
        }
        val kakaoLoginButton: Button = findViewById(R.id.kakaoLoginButton)
        val naverLoginButton: NidOAuthLoginButton = findViewById(R.id.naverLoginButton)

        kakaoLoginButton.setOnClickListener {
            signInWithKakao()
        }
        naverLoginButton.setOnClickListener {
            signInWithNaver()
        }
    }

    // 이메일 로그인
    private fun signInWithEmail(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    val user = auth.currentUser
                    Toast.makeText(this, "로그인 성공: ${user?.email}", Toast.LENGTH_SHORT).show()
                } else {
                    // 로그인 실패
                    Log.w("SignIn", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "로그인 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // 이메일 회원가입
    private fun signUpWithEmail(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공
                    val user = auth.currentUser
                    Toast.makeText(this, "회원가입 성공: ${user?.email}", Toast.LENGTH_SHORT).show()
                    sendEmailVerification(user) // FirebaseUser를 인자로 전달
                } else {
                    // 회원가입 실패
                    Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "회원가입 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // 이메일 인증 전송
    private fun sendEmailVerification(user: FirebaseUser?) {
        user?.sendEmailVerification()?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "이메일 인증 전송 완료", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "이메일 인증 전송 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithKakao() {
        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
            if (error != null) {
                Log.e("KakaoLogin", "로그인 실패", error)
                Toast.makeText(this, "카카오 로그인 실패: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                Log.i("KakaoLogin", "로그인 성공	${token.accessToken}")
            }
        }
    }

    private fun signInWithNaver() {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                val accessToken = NaverIdLoginSDK.getAccessToken()
                Log.i("NaverLogin", "로그인 성공	$accessToken")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@MainActivity, "errorCode:$errorCode, errorDesc:$errorDescription", Toast.LENGTH_SHORT).show()
                Log.e(
                    "NaverLogin",
                    "네이버 로그인 실패: $message, errorCode: $errorCode, errorDesc: $errorDescription"
                )
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }
}



