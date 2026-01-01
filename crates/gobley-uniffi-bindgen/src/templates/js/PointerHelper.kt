
{{ visibility() }}value class Pointer(internal val value: Int) {
    init {
        if (value == 0) error("Null pointer is not allowed")
    }
}
internal val NullPointer: Pointer? = null
internal fun Pointer.toLong(): Long = value.toLong()
internal fun kotlin.Long.toPointer(): Pointer {
    if (ushr(32) != 0L) error("Higher 32 bits are not zero")
    return Pointer(toInt())
}
