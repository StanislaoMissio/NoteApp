package com.br.noteapp.presentation.add_screen

import androidx.compose.ui.focus.FocusState

sealed class AddNoteEvent {
    data class EnteredTitle(val newTitle: String) : AddNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddNoteEvent()
    data class EnteredContent(val newContent: String) : AddNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddNoteEvent()
    data class ChangeColor(val newColor: Int) : AddNoteEvent()
    object SaveNote : AddNoteEvent()
}