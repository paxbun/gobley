
internal interface Struct {
    fun encode(memory: Uint8Array, pointer: Pointer, offset: Int = 0)
    fun decode(memory: Uint8Array, pointer: Pointer, offset: Int = 0)
}

internal interface StructByValue : Struct

internal fun Long.encode(memory: Uint8Array, pointer: Pointer, offset: Int = 0) {
    Uint32Array(memory.buffer, pointer.value + offset).also {
        it[0] = toInt()
        it[1] = ushr(32).toInt()
    }
}

internal fun decodeLong(memory: Uint8Array, pointer: Pointer, offset: Int = 0): Long {
    return Uint32Array(memory.buffer, pointer.value + offset).let {
        (it[0].toLong() shl 32) or it[1].toLong()
    }
}

internal fun Int.encode(memory: Uint8Array, pointer: Pointer, offset: Int = 0) {
    Uint32Array(memory.buffer, pointer.value + offset).also {
        it[0] = this
    }
}

internal fun decodeInt(memory: Uint8Array, pointer: Pointer, offset: Int = 0): Int {
    return Uint32Array(memory.buffer, pointer.value + offset).let { it[0] }
}

internal fun Short.encode(memory: Uint8Array, pointer: Pointer, offset: Int = 0) {
    Uint16Array(memory.buffer, pointer.value + offset).also {
        it[0] = this
    }
}

internal fun decodeShort(memory: Uint8Array, pointer: Pointer, offset: Int = 0): Short {
    return Uint16Array(memory.buffer, pointer.value + offset).let { it[0] }
}

internal fun Byte.encode(memory: Uint8Array, pointer: Pointer, offset: Int = 0) {
    memory[pointer.value + offset] = this
}

internal fun decodeByte(memory: Uint8Array, pointer: Pointer, offset: Int = 0): Byte {
    return memory[pointer.value + offset]
}
