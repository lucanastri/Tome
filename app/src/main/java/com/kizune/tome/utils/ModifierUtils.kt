package com.kizune.tome.utils

import androidx.compose.ui.Modifier

/**
 * Per inserire attributi modifiers imposti da una condizione logica
 */
fun Modifier.conditional(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}