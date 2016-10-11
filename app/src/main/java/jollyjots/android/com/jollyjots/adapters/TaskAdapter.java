package jollyjots.android.com.jollyjots.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jollyjots.android.com.jollyjots.R;
import jollyjots.android.com.jollyjots.models.Task;

// TaskAdapter binds data to item views (that are displayed within the RecyclerView)
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final static String TASKS_PREF = "TASKS_PREF";

    private final ArrayList<Task> tasks = new ArrayList<>();
    private final LayoutInflater inflater;

    private TaskAdapter(@NonNull final LayoutInflater inflater) {
        this.inflater = inflater;
    }

    private TaskAdapter(@NonNull final LayoutInflater inflater,
                        @NonNull final ArrayList<Task> tasks) {
        this.inflater = inflater;
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    // inflate the item layout from XML and create the holder
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_task_row, parent, false);
        return new TaskViewHolder(view);
    }

    // to set the item view attributes based on the data
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(position);
    }

    // to determine the number of items
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // load data from System Preferences
    public static TaskAdapter loadFromData(@Nullable final Context context,
                                           @NonNull final LayoutInflater layoutInflater) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        if (pref != null) {
            // TODO read tasks from Shared Preferences
            final ArrayList<Task> tasks = null;
            if (tasks != null) {
                return new TaskAdapter(layoutInflater, tasks);
            }
        }

        return new TaskAdapter(layoutInflater);
    }

    public void addTask(@NonNull final Task task) {
        tasks.add(task);
        notifyDataSetChanged();
    }

    public void removeTask(@NonNull final Task task) {
        tasks.remove(task);
        notifyDataSetChanged();
    }

    public void removeTask(@NonNull final int position) {
        if (tasks.size() <= position) {
            return;
        }
        tasks.remove(position);
        notifyItemRemoved(position);
    }


    // Caches references to the views in item layout file
    // so that resource lookups are not repeated unnecessarily.
    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // store all subviews that will be set as the row item is rendered
        private final TextView name;
        private final TextView priority;

        // constructor should accept the entire item row
        // and do the view lookups to find each subview
        public TaskViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.item_todo_row_name);
            priority = (TextView) itemView.findViewById(R.id.item_todo_row_priority);

            itemView.setOnClickListener(this);
        }

        public void bind(final int position) {
            final Task task = tasks.get(position);

            name.setText(task.getTaskTitle());
            priority.setText("Norm");
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position

            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                removeTask(position);
            }
        }
    }
}
