package io.github.bibekshakya35.simplenoteapp.db;



import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import io.github.bibekshakya35.simplenoteapp.dao.DaoAccess;
import io.github.bibekshakya35.simplenoteapp.model.Note;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
