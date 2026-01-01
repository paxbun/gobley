{%- for def in ci.ffi_definitions() %}
{%- endfor %}

internal interface UniffiLib {
    companion object {
        internal val WASM_INSTANCE: gobley.wasm.{{ ci.namespace() }}

        internal val INSTANCE: UniffiLib by lazy {
            
        }
    }

    {% for func in ci.iter_ffi_function_definitions() -%}
    fun {{ func.name() }}(
        {%- call kt::arg_list_ffi_decl(func, 8) %}
    ): {% match func.return_type() %}{% when Some(return_type) %}{{ return_type.borrow()|ffi_type_name_by_value(ci) }}{% when None %}Unit{% endmatch %}
    {% endfor %}
}

internal class UniffiLibInstance: UniffiLib {
    {% for func in ci.iter_ffi_function_definitions() -%}
    {% endfor %}
}