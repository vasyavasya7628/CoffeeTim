package com.example.foodike.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodike.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _email = mutableStateOf(
        CoffeeTimeFieldState(
            hint = "Почта или мобильный телефон"
        )
    )
    val email: State<CoffeeTimeFieldState> = _email

    private val _password = mutableStateOf(
        CoffeeTimeFieldState(
            hint = "Пароль"
        )
    )
    val password: State<CoffeeTimeFieldState> = _password

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )

            }
            is LoginEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.PerformLogin -> {
                if (email.value.text == "" && password.value.text == ""){
                    repository.toggleLoginState()
                    event.onClick()
                }
                else {
                    viewModelScope.launch{
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "Пожалуйста, введите правильный адрес электронной почты и пароль"
                            )
                        )
                    }
                }
            }
        }
    }
}