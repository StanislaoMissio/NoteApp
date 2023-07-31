package com.br.noteapp.di

import android.app.Application
import androidx.room.Room
import com.br.noteapp.data.data_source.NoteDatabase
import com.br.noteapp.data.repository.NoteRepositoryImpl
import com.br.noteapp.domain.repository.NoteRepository
import com.br.noteapp.domain.usecase.add_note.AddNoteUseCase
import com.br.noteapp.domain.usecase.delete_note.DeleteNoteUseCase
import com.br.noteapp.domain.usecase.get_notebyid.GetNoteByIdUseCase
import com.br.noteapp.domain.usecase.get_notes.GetNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "db_notes"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCases(repository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNoteUseCases(repository: NoteRepository): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSingleNoteUseCases(repository: NoteRepository): GetNoteByIdUseCase {
        return GetNoteByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddNoteUseCases(repository: NoteRepository): AddNoteUseCase {
        return AddNoteUseCase(repository)
    }

}