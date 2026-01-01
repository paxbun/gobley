{%- call kt::docstring_value(ci.namespace_docstring(), 0) %}

@file:Suppress("RemoveRedundantBackticks")

package {{ config.package_name() }}

import org.khronos.webgl.get
import org.khronos.webgl.set
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.Uint16Array
import org.khronos.webgl.Uint32Array

{%- for req in self.imports() %}
{{ req.render() }}
{%- endfor %}

{% include "PointerHelper.kt" %}

{% include "ByteBuffer.kt" %}
{% include "StructHelper.kt" %}
{% include "RustBufferTemplate.kt" %}
{% include "ffi/FfiConverterTemplate.kt" %}
{% include "Helpers.kt" %}

// Contains loading, initialization code,
// and the FFI Function declarations.
{% include "NamespaceLibraryTemplate.kt" %}

// Public interface members begin here.
{{ type_helper_code }}

{% import "macros.kt" as kt %}

{%- for func in ci.function_definitions() %}
{%- include "TopLevelFunctionTemplate.kt" %}
{%- endfor %}
