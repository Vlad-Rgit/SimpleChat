package com.simplechat.core.mvi_base

import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Базовый класс для всех ViewModel
 */
abstract class MviViewModel<Intent, State>: ViewModel() {


    /**
     * Flow представляюший события интерфейса
     */
    val intents = MutableSharedFlow<Intent>()

    /**
     * Канал для отправки State к View
     * Используем здесь Channel, чтобы гарантировать, что
     * все State не будут потеряны до того, как View
     * подписался на Channel
     */
    protected val _state = Channel<State>(5)
    val state: Flow<State> = _state.receiveAsFlow()

    init {
        intents
            .onEach { handleIntent(it) }
            .launchIn(viewModelScope)
    }


    /**
     * Обработать здесь новый Intent
     */
    abstract fun handleIntent(intent: Intent)

    /**
     * Отправит новый State к фрагменту
     */
    protected fun sendState(state: State) {
        viewModelScope.launch {
            _state.send(state)
        }
    }

    override fun onCleared() {
        super.onCleared()
        _state.close()
    }


}