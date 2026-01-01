{% include "ffi/Helpers.kt" %}

internal sealed class UniffiRustCallStatusStruct(
    public abstract val code: Byte,
    public abstract val errorBuf: RustBufferByValue,
) {
    internal class ByValue(

    )

    internal class ByReference(
        override
    )
}

internal typealias UniffiRustCallStatus = UniffiRustCallStatusStruct.ByReference
internal typealias UniffiRustCallStatusByValue = UniffiRustCallStatusStruct.ByValue

internal object UniffiRustCallStatusHelper {
    internal fun allocValue() = UniffiRustCallStatusByValue()
    internal fun <U> withReference(block: (UniffiRustCallStatus) -> U): U {
        val status = UniffiRustCallStatus()
        return block(status)
    }
}