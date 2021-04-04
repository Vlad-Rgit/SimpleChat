package com.simplechat.core.mvi_base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * Базовый класс для всех фрагментов
 */
abstract class MviViewFragment<
        Intent,
        State,
        Binding: ViewBinding>: Fragment() {

    protected lateinit var binding: Binding

    private lateinit var viewModel: MviViewModel<Intent, State>

    private var stateCollectJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(getViewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Начать обработку событий после того как фрагмент
        //войдет в состояние CREATED
        stateCollectJob = lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                render(it)
            }
        }
    }


    /**
     * Отправить Intent к ViewModel асинхронно
     */
    fun sendIntent(intent: Intent) {
        lifecycleScope.launch {
            viewModel.intents.emit(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Закончить прослушивание новых состояний
        //при уничтожении интерфейса
        stateCollectJob?.cancel()
    }


    /**
     * Получить тип ViewModel для фрагмента
     */
    abstract fun getViewModelClass(): Class<out MviViewModel<Intent, State>>

    /**
     * Обработать новый State от ViewModel
     */
    abstract fun render(state: State)

    /**
     * Инициализировать Binding для фрагмента
     */
    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding
}