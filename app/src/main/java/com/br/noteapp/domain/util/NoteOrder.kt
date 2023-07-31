package com.br.noteapp.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)

    fun copy(noteOrder: OrderType): NoteOrder {
        return when (this) {
            is Title -> Title(noteOrder)
            is Date -> Date(noteOrder)
            is Color -> Color(noteOrder)
        }
    }
}