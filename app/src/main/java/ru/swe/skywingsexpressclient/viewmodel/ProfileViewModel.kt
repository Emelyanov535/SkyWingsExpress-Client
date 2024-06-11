package ru.swe.skywingsexpressclient.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.swe.skywingsexpressclient.data.repository.ProfileRepo


class ProfileViewModel(private val profileRepository: ProfileRepo) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    @SuppressLint("StaticFieldLeak")
    private lateinit var activity: Activity

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    private var _tokenAccess = MutableStateFlow<String?>(null)
    val tokenAccess: StateFlow<String?> = _tokenAccess.asStateFlow()

    fun setActivity(activity: Activity) {
        this.activity = activity
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("359809661776-d4fq32or2jevfoigrhgrc06458qkvkbp.apps.googleusercontent.com")
            .requestServerAuthCode("359809661776-d4fq32or2jevfoigrhgrc06458qkvkbp.apps.googleusercontent.com", true)
            .requestProfile()
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun setSignInLauncher(launcher: ActivityResultLauncher<Intent>) {
        this.signInLauncher = launcher
    }

    fun getToken() = viewModelScope.launch {
        val res = profileRepository.getToken(email, password)
    }

    fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    fun sendGoogleTokenToServer(tokenAccess: String) = viewModelScope.launch {
        try {
            val response = profileRepository.getGoogleToken(tokenAccess)
            println(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getAccessToken(token: String) = viewModelScope.launch{
        profileRepository.getAccessToken(token).collect{
            token -> _tokenAccess.value = token
        }
    }
}