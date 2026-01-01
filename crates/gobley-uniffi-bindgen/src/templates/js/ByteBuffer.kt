
{{ visibility() }}class ByteBuffer(
    internal val buffer: Uint8Array,
    internal var position: Int = 0,
) {
    private val capacity: Int
        get() = buffer.length

    {{ visibility() }}fun position(): Int = position

    {{ visibility() }}fun hasRemaining(): Boolean = capacity != position

    private fun checkRemaining(bytes: Int) {
        val remaining = capacity - position
        require(bytes <= remaining) {
            "buffer is exhausted: required: $bytes, remaining: $remaining, capacity: $capacity, position: $position"
        }
    }

    {{ visibility() }}fun get(): Byte {
        checkRemaining(1)
        return buffer[position++]
    }

    {{ visibility() }}fun get(bytesToRead: Int): ByteArray {
        checkRemaining(bytesToRead)
        val result = ByteArray(bytesToRead)
        if (result.isNotEmpty()) {
            // TODO: use faster way to copy byte contents from a WASM memory
            for (idx in result.indices) {
                result[idx] = buffer[position++]
            }
        }
        return result
    }

    {{ visibility() }}fun getShort(): Short {
        checkRemaining(2)
        return (((buffer[position++].toInt() and 0xff) shl 8)
                or (buffer[position++].toInt() and 0xff)).toShort()
    }

    {{ visibility() }}fun getInt(): Int {
        checkRemaining(4)
        return (((buffer[position++].toInt() and 0xff) shl 24)
                or ((buffer[position++].toInt() and 0xff) shl 16)
                or ((buffer[position++].toInt() and 0xff) shl 8)
                or (buffer[position++].toInt() and 0xff))
    }

    {{ visibility() }}fun getLong(): Long {
        checkRemaining(8)
        return (((buffer[position++].toLong() and 0xffL) shl 56)
                or ((buffer[position++].toLong() and 0xffL) shl 48)
                or ((buffer[position++].toLong() and 0xffL) shl 40)
                or ((buffer[position++].toLong() and 0xffL) shl 32)
                or ((buffer[position++].toLong() and 0xffL) shl 24)
                or ((buffer[position++].toLong() and 0xffL) shl 16)
                or ((buffer[position++].toLong() and 0xffL) shl 8)
                or (buffer[position++].toLong() and 0xffL))
    }

    {{ visibility() }}fun getFloat(): Float = Float.fromBits(getInt())

    {{ visibility() }}fun getDouble(): Double = Double.fromBits(getLong())

    {{ visibility() }}fun put(value: Byte) {
        checkRemaining(1)
        buffer[position++] = value
    }

    {{ visibility() }}fun put(src: ByteArray) {
        checkRemaining(src.size)
        if (src.isNotEmpty()) {
            // TODO: use faster way to copy byte contents to a WASM memory
            for (idx in src.indices) {
                buffer[position++] = src[idx]
            }
            position += src.size
        }
    }

    {{ visibility() }}fun putShort(value: Short) {
        checkRemaining(2)
        buffer[position++] = (value.toInt() ushr 8 and 0xff).toByte()
        buffer[position++] = (value.toInt() and 0xff).toByte()
    }

    {{ visibility() }}fun putInt(value: Int) {
        checkRemaining(4)
        buffer[position++] = (value ushr 24 and 0xff).toByte()
        buffer[position++] = (value ushr 16 and 0xff).toByte()
        buffer[position++] = (value ushr 8 and 0xff).toByte()
        buffer[position++] = (value and 0xff).toByte()
    }

    {{ visibility() }}fun putLong(value: Long) {
        checkRemaining(8)
        buffer[position++] = (value ushr 56 and 0xffL).toByte()
        buffer[position++] = (value ushr 48 and 0xffL).toByte()
        buffer[position++] = (value ushr 40 and 0xffL).toByte()
        buffer[position++] = (value ushr 32 and 0xffL).toByte()
        buffer[position++] = (value ushr 24 and 0xffL).toByte()
        buffer[position++] = (value ushr 16 and 0xffL).toByte()
        buffer[position++] = (value ushr 8 and 0xffL).toByte()
        buffer[position++] = (value and 0xffL).toByte()
    }

    {{ visibility() }}fun putFloat(value: Float): Unit = putInt(value.toRawBits())

    {{ visibility() }}fun putDouble(value: Double): Unit = putLong(value.toRawBits())
}