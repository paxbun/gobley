{% include "ffi/RustBufferTemplate.kt" %}

{{ visibility() }}open class RustBufferStruct(
    {{ visibility() }}var capacity: Long,
    {{ visibility() }}var len: Long,
    {{ visibility() }}var data: Pointer?,
) : Struct {
    {{ visibility() }}constructor(): this(0.toLong(), 0.toLong(), null)

    {{ visibility() }}class ByValue(
        capacity: Long,
        len: Long,
        data: Pointer?,
    ): RustBufferStruct(capacity, len, data), StructByValue {
        {{ visibility() }}constructor(): this(0.toLong(), 0.toLong(), null)
    }

    override fun encode(memory: Uint8Array, pointer: Pointer, offset: Int) {
        capacity.encode(memory, pointer)
        len.encode(memory, pointer, 8)
        (data?.toLong() ?: 0).encode(memory, pointer, 16)
    }

    override fun decode(memory: Uint8Array, pointer: Pointer, offset: Int) {
        capacity = decodeLong(memory, pointer)
        len = decodeLong(memory, pointer, 8)
        data = decodeLong(memory, pointer, 16).takeIf { it != 0L }?.toPointer()
    }
}

{{ visibility() }}typealias RustBuffer = RustBufferStruct
{{ visibility() }}typealias RustBufferByValue = RustBufferStruct.ByValue

internal fun RustBuffer.asByteBuffer(): ByteBuffer? {
    {% call kt::check_rust_buffer_length("this.len") %}
    return ByteBuffer(data?.getByteBuffer(0L, this.len) ?: return null)
}

internal fun RustBufferByValue.asByteBuffer(): ByteBuffer? {
    {% call kt::check_rust_buffer_length("this.len") %}
    return ByteBuffer(data?.getByteBuffer(0L, this.len) ?: return null)
}