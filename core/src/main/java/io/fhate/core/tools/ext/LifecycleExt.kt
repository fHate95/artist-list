package io.fhate.core.tools.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import io.fhate.core.data.wrapper.SingleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/* Observe any LiveData */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this) { it?.let { t -> action(t) } }
}
/* Observe SingleEvent */
fun <T> LifecycleOwner.observeEvent(liveData: LiveData<SingleEvent<T>>, action: (t: SingleEvent<T>) -> Unit) {
    liveData.observe(this) { it?.let { t -> action(t) } }
}
/* Launch UI block in CoroutineScope */
fun CoroutineScope.ui(
    block: suspend CoroutineScope.() -> Unit
): Job = launch(Dispatchers.Main, block = block)