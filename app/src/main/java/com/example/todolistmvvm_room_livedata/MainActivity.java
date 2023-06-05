package com.example.todolistmvvm_room_livedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private NotesAdapter notesAdapter;
    private FloatingActionButton buttonAddNote;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
//        viewModel.getCount().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                Toast.makeText(
//                        MainActivity.this,
//                        String.valueOf(integer),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        initViews();
        setAdapter();

        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateNoteActivity.newIntent(MainActivity.this));
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        viewModel.refreshList();
//    }

    private void initViews() {
        rvNotes = findViewById(R.id.rvNotes);
        buttonAddNote = findViewById(R.id.floatingActionButtonAdd);
    }

    private void setAdapter() {
        notesAdapter = new NotesAdapter();
//        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
//            @Override
//            public void onNoteClick(Note note) {
//                viewModel.showCount();
//            }
//        });
        rvNotes.setAdapter(notesAdapter);
        setSwipeToDelete();
    }

    private void setSwipeToDelete() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target
            ) {
                return false;
            }

            @Override
            public void onSwiped(
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    int direction
            ) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
                viewModel.remove(note);
            }
        });
        itemTouchHelper.attachToRecyclerView(rvNotes);
    }
}