package com.example.todolistmvvm_room_livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private Button buttonSaveNote;

//    private NoteDatabase noteDatabase;
//    private Handler handler = new Handler(Looper.getMainLooper());

    private CreateNoteViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        viewModel = new ViewModelProvider(this).get(CreateNoteViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                finish();
            }
        });
        initViews();

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void initViews(){
        editTextNote = findViewById(R.id.etEnterNote);
        radioButtonLow = findViewById(R.id.radioButtonPriorityLow);
        radioButtonMedium = findViewById(R.id.radioButtonPriorityMedium);
        buttonSaveNote = findViewById(R.id.btnSaveNote);
    }

    private void saveNote(){
        String text = editTextNote.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Поле не может быть пустым", Toast.LENGTH_SHORT).show();
        } else {
            int priority = getPriority();
            Note note = new Note(text, priority);
            viewModel.saveNote(note);
        }
    }

    private int getPriority() {
        int priority;
        if (radioButtonLow.isChecked()) priority=0;
        else if (radioButtonMedium.isChecked()) priority=1;
        else priority=2;
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CreateNoteActivity.class);
    }
}