package com.example.samplearchitecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*

abstract class BaseDialogFragment<VI : BaseViewIntent,
        SI : BaseModelIntent, S : BaseViewState,
        PC: BasePartialChange<S>> : DialogFragment() {

    abstract val actor: BaseActor<VI, SI, S, PC>

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutRes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actor.onCreate()

        lifecycleScope.launchWhenStarted {
            actor.singleEvent
                .onEach { handleSingleEvent(it) }
                .catch { }
                .collect()
        }

        lifecycleScope.launchWhenStarted {
            actor.viewState
                .filterNotNull()
                .onEach { render(it) }
                .collect()
        }

    }

//    fun addScrollDelegate(@IdRes recyclerViewId: Int,
//                          @IdRes downViewId: Int) {
//        ScrollDownDelegate(recyclerViewId, downViewId).also(lifecycle::addObserver)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intents()
            ?.onEach { actor.processIntent(it) }
            ?.launchIn(requireActivity().lifecycleScope)

    }

    open fun intents(): Flow<VI>? = null

    open fun handleSingleEvent(event: SI) {}

    open fun render(state: S) {}

}