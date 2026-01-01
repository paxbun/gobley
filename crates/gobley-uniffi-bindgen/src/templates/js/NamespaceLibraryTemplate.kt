{%- for def in ci.ffi_definitions() %}
{%- match def %}
{%- when FfiDefinition::CallbackFunction(callback) %}
internal typealias {{ callback.name()|ffi_callback_name }} =
    (
        {%- for arg in callback.arguments() %}
        {{ arg.type_().borrow()|ffi_type_name_by_value(ci) }},
        {%- endfor %}
        {%- if callback.has_rust_call_status_arg() -%}
        uniffiCallStatus: UniffiRustCallStatus,
        {%- endif %}
    ) ->
    {%- if let Some(return_type) = callback.return_type() -%}
    {{ ' ' }}{{ return_type|ffi_type_name_by_value(ci) }}
    {%- else -%}
    {{ " Unit" }}
    {%- endif %}
{%- when FfiDefinition::Struct(ffi_struct) %}
// TODO: {{ ffi_struct.name()|ffi_struct_name }}Struct
{%- when FfiDefinition::Function(_) %}
{# functions are handled below #}
{%- endmatch %}
{%- endfor %}

internal object UniffiLib {
    internal val WASM_INSTANCE: gobley.wasm.{{ ci.namespace() }} 

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