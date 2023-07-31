package com.br.noteapp.domain.usecase.get_notebyid

import com.br.noteapp.domain.model.Note
import com.br.noteapp.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}