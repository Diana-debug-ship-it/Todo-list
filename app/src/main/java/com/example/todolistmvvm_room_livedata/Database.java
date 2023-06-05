package com.example.todolistmvvm_room_livedata;

import android.provider.ContactsContract;

import java.util.ArrayList;

public class Database {
    private ArrayList<Note> notes = new ArrayList<>();

    public static Database instance = null;

    public static Database getInstance() {
        if (instance==null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        for (int i = 0; i < 20; i++) {
            notes.add(new Note(i, "Note " + i, (int) (Math.random() * 3)));
        }
    }

    public void add(Note note) {
        notes.add(note);
    }

    public void removeAt(int id){
        for (int i=0; i<notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getId()==id) {
                notes.remove(note);
            }
        }
    }

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }
}
