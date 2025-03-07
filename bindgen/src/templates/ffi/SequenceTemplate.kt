
{%- let inner_type_name = inner_type|type_name(ci) %}

object {{ ffi_converter_name }}: FfiConverterRustBuffer<List<{{ inner_type_name }}>> {
    override fun read(buf: ByteBuffer): List<{{ inner_type_name }}> {
        val len = buf.getInt()
        return List<{{ inner_type_name }}>(len) {
            {{ inner_type|read_fn }}(buf)
        }
    }

    override fun allocationSize(value: List<{{ inner_type_name }}>): ULong {
        val sizeForLength = 4UL
        val sizeForItems = value.sumOf { {{ inner_type|allocation_size_fn }}(it) }
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<{{ inner_type_name }}>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.iterator().forEach {
            {{ inner_type|write_fn }}(it, buf)
        }
    }
}
