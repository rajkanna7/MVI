package com.rk.snackbar

import androidx.compose.animation.core.Easing

data class InfoBarEasing(
    val enterEasing: Easing,
    val exitEasing: Easing
) {
    constructor(easing: Easing) : this(easing, easing)
}
