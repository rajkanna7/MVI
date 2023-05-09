package com.rk.reaction_popup


class FavouriteEmojiProviderImpl: FavouriteEmojiProvider {
    override fun invoke(): List<Emoji> = listOf(
        "\uD83D\uDC4D", "❤️", "\uD83D\uDE02", "\uD83D\uDE2E", "\uD83D\uDE25", "\uD83D\uDE4F"
    ).map(::EmojiModel)
}