package jollyjots.android.com.jollyjots.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import jollyjots.android.com.jollyjots.R;
import jollyjots.android.com.jollyjots.models.Task;

// TaskAdapter binds data to item views (that are displayed within the RecyclerView)
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

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
    public static TaskAdapter loadFromData(@NonNull final Context context,
                                           @NonNull final LayoutInflater layoutInflater) {

        final String TASKS_PREF = context.getString(R.string.TASKS_PREF);
        final String TASKS_PREF_SIZE = context.getString(R.string.TASKS_PREF_SIZE);
        final String TASK_JSON = context.getString(R.string.TASK_JSON);

        SharedPreferences pref = context.getSharedPreferences(TASKS_PREF, Context.MODE_PRIVATE);

        if (pref != null) {
            // get size
            int taskListSize = pref.getInt(TASKS_PREF_SIZE, 0);

            // add tasks to new list
            final ArrayList<Task> taskList = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < taskListSize; i++) {
                String taskJson = pref.getString(TASK_JSON + i, "");
                Task task = gson.fromJson(taskJson, Task.class);
                taskList.add(task);
            }

            // return new task adapter
            if (taskList != null) {
                return new TaskAdapter(layoutInflater, taskList);
            }
        }

        return new TaskAdapter(layoutInflater);
    }

    public Task getTask(final int position) {
        return tasks.get(position);
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
    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        // store all subviews that will be set as the row item is rendered
        private final TextView name;
        private final TextView priority;

        // constructor should accept the entire item row
        // and do the view lookups to find each subview
        public TaskViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.item_todo_row_name);
            priority = (TextView) itemView.findViewById(R.id.item_todo_row_priority);
            itemView.setOnLongClickListener(this);
        }

        public void bind(final int position) {
            final Task task = tasks.get(position);

            name.setText(task.getTaskTitle());
            priority.setText("Norm");
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition(); // gets item position

            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                removeTask(position);
            }
            return true;
        }

    }
}
