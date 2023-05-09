package com.rk.reaction_popup

interface FavouriteEmojiProvider {

    operator fun invoke(): List<Emoji>
}