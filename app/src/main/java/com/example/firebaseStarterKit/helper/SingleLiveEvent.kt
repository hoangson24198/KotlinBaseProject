package com.atmaneuler.hsdps.helper

import androidx.lifecycle.*

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val liveDataToObserve: LiveData<T>
    private val pendingMap: MutableMap<Int, Boolean>

    init {
        val outputLiveData = MediatorLiveData<T>()
        outputLiveData.addSource(this) { currentValue ->
            outputLiveData.value = currentValue
        }
        liveDataToObserve = outputLiveData
        pendingMap = HashMap()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        pendingMap[observer.hashCode()] = false

        // Observe the internal MutableLiveData
        liveDataToObserve.observe(owner, Observer { t ->
            if (pendingMap[observer.hashCode()] == true) { // don't trigger if the observer wasn't registered
                observer.onChanged(t)
                pendingMap[observer.hashCode()] = false
            }
        })
    }

    override fun setValue(t: T?) {
        pendingMap.forEach { pendingMap[it.key] = true }
        super.setValue(t)
    }
}