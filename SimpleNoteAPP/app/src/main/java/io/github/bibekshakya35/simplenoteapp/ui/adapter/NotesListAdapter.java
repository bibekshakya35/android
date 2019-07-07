package io.github.bibekshakya35.simplenoteapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.bibekshakya35.simplenoteapp.R;
import io.github.bibekshakya35.simplenoteapp.model.Note;
import io.github.bibekshakya35.simplenoteapp.util.AppUtils;
import io.github.bibekshakya35.simplenoteapp.util.NoteDiffUtil;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.CustomViewHolder> {
    private List<Note> notes;
    public NotesListAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Note note = getItem(position);

        holder.itemTitle.setText(note.getTitle());
        holder.itemTime.setText(AppUtils.getFormattedDateString(note.getCreatedAt()));

        if(note.isEncrypt()) {
            holder.itemTime.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_lock, 0);

        } else {
            holder.itemTime.setCompoundDrawablesWithIntrinsicBounds(0,0, 0, 0);
        }
    }

    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void addTasks(List<Note> newNotes) {
        NoteDiffUtil noteDiffUtil = new NoteDiffUtil(notes, newNotes);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(noteDiffUtil);
        notes.clear();
        notes.addAll(newNotes);
        diffResult.dispatchUpdatesTo(this);
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTitle, itemTime;
        public CustomViewHolder(View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.item_title);
            itemTime = itemView.findViewById(R.id.item_desc);
        }
    }
}
