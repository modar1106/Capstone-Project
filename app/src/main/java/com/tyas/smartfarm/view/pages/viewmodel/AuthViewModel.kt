package com.tyas.smartfarm.view.pages.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.tyas.smartfarm.model.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: FirebaseUser) : AuthState()
    data class Error(val message: String?) : AuthState()
}

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> get() = _authState

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = AuthState.Error("Email tidak valid.")
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            _authState.value = AuthState.Error("Password minimal 6 karakter.")
            return false
        }
        return true
    }

    fun register(email: String, password: String) {
        if (!validateInput(email, password)) return

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val user = repository.register(email, password)
                if (user != null) {
                    _authState.value = AuthState.Success(user)
                } else {
                    _authState.value = AuthState.Error("Registrasi gagal. Tidak ada data pengguna.")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(parseFirebaseAuthException(e))
            }
        }
    }

    fun login(email: String, password: String) {
        if (!validateInput(email, password)) return

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val user = repository.login(email, password)
                if (user != null) {
                    _authState.value = AuthState.Success(user)
                } else {
                    _authState.value = AuthState.Error("Login gagal. Tidak ada data pengguna.")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(parseFirebaseAuthException(e))
            }
        }
    }

    fun resetState(state: AuthState = AuthState.Idle) {
        _authState.value = state
    }
    fun logout() {
        repository.logout()
    }

    private fun parseFirebaseAuthException(exception: Exception): String {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> "Password terlalu lemah."
            is FirebaseAuthInvalidCredentialsException -> "Email atau password tidak valid."
            is FirebaseAuthUserCollisionException -> "Email sudah terdaftar."
            is FirebaseAuthException -> "Terjadi kesalahan autentikasi."
            else -> exception.message ?: "Terjadi kesalahan."
        }
    }
}
