package com.br.noteapp.domain.usecase.delete_note

import com.br.noteapp.domain.model.Note
import com.br.noteapp.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}