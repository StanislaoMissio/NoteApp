package com.br.noteapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.br.noteapp.presentation.theme.Pink40
import com.br.noteapp.presentation.theme.Purple40
import com.br.noteapp.presentation.theme.PurpleGrey40
import java.lang.Exception

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val body: String,
    val timestamp: Long,
    val color: Int
) {

    companion object {
        val noteColors = listOf(Purple40, Pink40, PurpleGrey40)
    }

}

class InvalidNoteException(message: String) : Exception(message)