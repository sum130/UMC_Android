package com.example.flo

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {

    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user : User){
        val signUpService = getRetrofit().create(AuthRetrofitInterface::class.java)

        signUpService.signUp(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                //Log.d("SIGNUP/SUCCESS", reponse.toString())
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000-> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                //실패처리
            }
        })
    }

    fun login(user: User) {
        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)


        loginService.login(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val loginResponse: AuthResponse = response.body()!!

                    when (val code = loginResponse.code) {
                        1000 -> loginView.onLoginSuccess(code,loginResponse.result!! )
                        else -> loginView.onLoginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                //실패처리
            }
        })
    }
}