package com.br.noteapp.domain.usecase.add_note

import com.br.noteapp.domain.model.InvalidNoteException
import com.br.noteapp.domain.model.Note
import com.br.noteapp.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The note title is blank")
        }
        repository.insertNote(note)
    }

}