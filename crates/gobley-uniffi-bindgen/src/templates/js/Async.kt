{% include "ffi/Async.kt" %}

internal uniffiRustFutureContinuationCallbackCallback: UniffiRustFutureContinuationCallback =
    { data: Long, pollResult: Byte ->
        uniffiContinuationHandleMap.remove(data).resume(pollResult)
    }

{%- if ci.has_async_callback_interface_definition() %}

internal uniffiForeignFutureFreeImpl: UniffiForeignFutureFree = 
    { handle: Long  ->
        val job = uniffiForeignFutureHandleMap.remove(handle)
        if (!job.isCompleted) {
            job.cancel()
        }
    }

{%- endif %}