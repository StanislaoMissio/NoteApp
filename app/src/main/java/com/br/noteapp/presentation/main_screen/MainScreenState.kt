package com.br.noteapp.presentation.main_screen

import com.br.noteapp.domain.model.Note
import com.br.noteapp.domain.util.NoteOrder
import com.br.noteapp.domain.util.OrderType

data class MainScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)