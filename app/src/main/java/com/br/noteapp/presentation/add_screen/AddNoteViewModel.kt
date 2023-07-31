package com.br.noteapp.presentation.add_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.noteapp.domain.model.InvalidNoteException
import com.br.noteapp.domain.model.Note
import com.br.noteapp.domain.usecase.add_note.AddNoteUseCase
import com.br.noteapp.domain.usecase.get_notebyid.GetNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _title = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    )
    val title: State<NoteTextFieldState> = _title

    private val _content = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter some content"
        )
    )
    val content: State<NoteTextFieldState> = _content

    private val _color = mutableIntStateOf(Note.noteColors.random().toArgb())
    val color: State<Int> = _color

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    getNoteByIdUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _title.value = title.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _content.value = content.value.copy(
                            text = note.body,
                            isHintVisible = false
                        )
                        _color.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.EnteredTitle -> {
                _title.value = title.value.copy(
                    text = event.newTitle
                )
            }

            is AddNoteEvent.ChangeTitleFocus -> {
                _title.value = title.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            title.value.text.isBlank()
                )
            }

            is AddNoteEvent.EnteredContent -> {
                _content.value = content.value.copy(
                    text = event.newContent
                )
            }

            is AddNoteEvent.ChangeContentFocus -> {
                _content.value = content.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            content.value.text.isBlank()
                )
            }

            is AddNoteEvent.ChangeColor -> {
                _color.value = event.newColor
            }

            is AddNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        addNoteUseCase(
                            Note(
                                title = title.value.text,
                                body = content.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = color.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

}