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
import ru.swe.skywingsexpressclient.data.utils.PreferencesManager
import android.app.Application
import com.auth0.android.jwt.JWT
import ru.swe.skywingsexpressclient.data.models.AuthGoogleDto
import ru.swe.skywingsexpressclient.data.models.SignUpDto
import ru.swe.skywingsexpressclient.data.models.TwoFaDto
import ru.swe.skywingsexpressclient.data.models.responseFor2FA
import ru.swe.skywingsexpressclient.data.network.AuthService

class ProfileViewModel(application: Application, private val profileRepository: ProfileRepo) : ViewModel() {

    private val preferencesManager: PreferencesManager = PreferencesManager(application)
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var regEmail by mutableStateOf("")
    var regPassword by mutableStateOf("")
    var regName by mutableStateOf("")
    var regSurname by mutableStateOf("")

    private var _tokenAccess = MutableStateFlow<String?>(null)
    val tokenAccess: StateFlow<String?> = _tokenAccess.asStateFlow()

    private var _twoFactorCode = MutableStateFlow<TwoFaDto?>(null)
    val twoFactorCode: StateFlow<TwoFaDto?> = _twoFactorCode.asStateFlow()

    var confirmCode by mutableStateOf("")

    var showTwoFactorDialog by mutableStateOf(false)
        private set

    var checkShowTwoFactorDialog by mutableStateOf(false)
        private set

    init {
        loadAccessToken()
    }
    fun register() = viewModelScope.launch {
        val data = SignUpDto(
            regEmail,
            regPassword,
            regName,
            regSurname
        )
        profileRepository.register(data)
    }

    fun checkUserOnOtp() = viewModelScope.launch {
        profileRepository.checkUserOnOtp(email, password).collect { res ->
            checkShowTwoFactorDialog = res
            if (!res) getToken()
        }
    }

    fun getTokenWithOtp() = viewModelScope.launch {
        profileRepository.getTokenWithOtp(email, password, confirmCode).collect { res ->
            preferencesManager.saveAccessToken(res.accessToken)
            preferencesManager.saveRefreshToken(res.refreshToken)
            _tokenAccess.value = res.accessToken
        }
        cleanCredential()
    }
    fun getToken() = viewModelScope.launch {
        profileRepository.getToken(email, password).collect { res ->
            preferencesManager.saveAccessToken(res.accessToken)
            preferencesManager.saveRefreshToken(res.refreshToken)
            _tokenAccess.value = res.accessToken
        }
        cleanCredential()
    }

    private fun cleanCredential(){
        email = ""
        password = ""
        confirmCode = ""
    }

    fun extractEmailFromToken() : String {
        val jwt = JWT(preferencesManager.getAccessToken()!!)
        val emailFromToken = jwt.getClaim("email").asString()
        return emailFromToken!!
    }

    fun loadAccessToken() {
        val token = preferencesManager.getAccessToken()
        _tokenAccess.value = token
    }

    fun logout() {
        viewModelScope.launch {
            preferencesManager.clearTokens()
            _tokenAccess.value = null
        }
    }

    fun getTwoFactorQrCode() = viewModelScope.launch {
        val code = profileRepository.generateTwoFactorCode().collect { res ->
            _twoFactorCode.value = res
            showTwoFactorDialog = true
        }
    }

    fun confirmTwoFactorAuthCode() = viewModelScope.launch {
        val data = responseFor2FA(
            confirmCode,
            _twoFactorCode.value!!.encodedTotpSecret
        )
        profileRepository.submitTwoFactorCode(data)
        confirmCode = ""
    }

    fun dismissTwoFactorDialog() {
        showTwoFactorDialog = false
    }

    fun dismissTwoFactorDialogCode() {
        checkShowTwoFactorDialog = false
    }

    //GOOGLE
    @SuppressLint("StaticFieldLeak")
    private lateinit var activity: Activity
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    fun getAccessToken(token: String) = viewModelScope.launch{
        profileRepository.getAccessToken(token).collect{
            token -> _tokenAccess.value = token
        }
    }
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
    fun sendGoogleTokenToServer(token: String) = viewModelScope.launch {
        profileRepository.getGoogleToken(AuthGoogleDto(token)).collect{ res ->
            preferencesManager.saveAccessToken(res.accessToken)
            preferencesManager.saveRefreshToken(res.refreshToken)
            _tokenAccess.value = res.accessToken
        }
    }

    fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }
}