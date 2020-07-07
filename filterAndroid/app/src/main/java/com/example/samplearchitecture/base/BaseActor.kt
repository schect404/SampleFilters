package com.example.samplearchitecture.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

abstract class BaseActor<VI : BaseViewIntent, SI : BaseModelIntent, S : BaseViewState, PC : BasePartialChange<S>> :
    ViewModel() {

    protected abstract val initialState: S

    protected val eventChannel = BroadcastChannel<SI>(capacity = Channel.BUFFERED)
    private val intentChannel = BroadcastChannel<VI>(capacity = Channel.CONFLATED)

    private val intentFlow = intentChannel.asFlow()

    val progressVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)

    suspend fun processIntent(intent: VI) {
        intentChannel.send(intent)
    }

    val singleEvent: Flow<SI> = eventChannel.asFlow()

    var viewState: MutableStateFlow<S?> = MutableStateFlow(null)
        private set

    val errorFlow = MutableStateFlow<String?>(null)

    open fun onCreate() {
        viewState = MutableStateFlow(initialState)
        intentFlow
            .handleIntent()
            .onEach { it.getSingleEvent()?.let { pushSingleEvent(it) } }
            .scan(viewState.value ?: initialState) { vs, change -> change.reduceToState(vs) }
            .onEach { viewState.value = it }
            .launchIn(viewModelScope)

    }

    protected suspend fun pushSingleEvent(event: SI) {
        eventChannel.send(event)
    }

    //handle view intent and returning flow with partial change
    open fun Flow<VI>.handleIntent(): Flow<PC> = flowOf()

    //get single event if needed
    open fun PC.getSingleEvent(): SI? = null

    protected fun <T> Flow<T>.runWithoutProgress(rethrowError: Boolean = false) =
        catch {
            if(rethrowError) throw it
            errorFlow.value = it.parseError()
        }

}


fun Throwable.parseError(): String? {
    val error = (this as? HttpException) ?: return message
    val bodyAsString = error.response()?.errorBody()?.string() ?: return message
    val bodyAsObject = JsonParser().parse(bodyAsString).asJsonObject
    return bodyAsObject?.get("text")?.asString ?: message
}