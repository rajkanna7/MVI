package com.rk.reaction_popup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


interface Emoji : Parcelable {
    val emoji: String
}

@Parcelize
data class EmojiModel(
    override val emoji: String,
) : Emoji

