package io.github.bibekshakya35.simplenoteapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;


import io.github.bibekshakya35.simplenoteapp.AppConstants;
import io.github.bibekshakya35.simplenoteapp.R;
import io.github.bibekshakya35.simplenoteapp.listener.OnRecyclerViewItemClickListener;
import io.github.bibekshakya35.simplenoteapp.model.Note;
import io.github.bibekshakya35.simplenoteapp.repository.NoteRepository;
import io.github.bibekshakya35.simplenoteapp.ui.adapter.NotesListAdapter;
import io.github.bibekshakya35.simplenoteapp.util.NavigatorUtils;
import io.github.bibekshakya35.simplenoteapp.util.RecyclerItemClickListener;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener, OnRecyclerViewItemClickListener, AppConstants {
    private TextView emptyView;
    private RecyclerView recyclerView;
    private NotesListAdapter notesListAdapter;
    private FloatingActionButton floatingActionButton;

    private NoteRepository noteRepository;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        this.noteRepository = new NoteRepository(getApplicationContext());

        recyclerView = findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        emptyView = findViewById(R.id.empty_view);

        updateTaskList();
    }

    private void updateTaskList() {
        noteRepository.getTasks().observe(this, (notes) -> {
            if (!notes.isEmpty()) {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (notesListAdapter == null) {
                    notesListAdapter = new NotesListAdapter(notes);
                    recyclerView.setAdapter(notesListAdapter);
                } else {
                    notesListAdapter.addTasks(notes);
                }
            } else updateEmptyView();
        });
    }

    private void updateEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(NoteListActivity.this, AddNoteActivity.class);
        startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onItemClick(View parentView, View childView, int position) {
        Note note = notesListAdapter.getItem(position);
        if (note.isEncrypt()) {
            NavigatorUtils.redirectToPwdScreen(this, note);

        } else {
            NavigatorUtils.redirectToEditTaskScreen(this, note);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data.hasExtra(INTENT_TASK)) {
                if(data.hasExtra(INTENT_DELETE)) {
                    noteRepository.deleteTask((Note) data.getSerializableExtra(INTENT_TASK));

                } else {
                    noteRepository.updateTask((Note) data.getSerializableExtra(INTENT_TASK));
                }
            } else {
                String title = data.getStringExtra(INTENT_TITLE);
                String desc = data.getStringExtra(INTENT_DESC);
                String pwd = data.getStringExtra(INTENT_PWD);
                boolean encrypt = data.getBooleanExtra(INTENT_ENCRYPT, false);
                noteRepository.insertTask(title, desc, encrypt, pwd);
            }
            updateTaskList();
        }
    }
}

