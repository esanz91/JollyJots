package jollyjots.android.com.jollyjots.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import jollyjots.android.com.jollyjots.R;
import jollyjots.android.com.jollyjots.adapters.TaskAdapter;
import jollyjots.android.com.jollyjots.models.Task;

public class MainActivity extends AppCompatActivity {

    RecyclerView taskRecyclerView;
    TaskAdapter adapter;
    Button addTaskButton;
    EditText addTaskEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
        addTaskEditText = (EditText) findViewById(R.id.activity_main_add_task_et);
        addTaskButton = (Button) findViewById(R.id.activity_main_add_button);

        adapter = TaskAdapter.loadFromData(this, getLayoutInflater());
        taskRecyclerView.setAdapter(adapter);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddItem();
            }
        });
    }

    @Override
    protected void onStop() {
        if (adapter != null) {
            // get SharedPreference Editor
            SharedPreferences pref = getSharedPreferences(getString(R.string.TASKS_PREF), MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            // save size of list
            int taskCount = adapter.getItemCount();
            editor.putInt(getString(R.string.TASKS_PREF_SIZE), taskCount);

            // save each task object as json
            Gson gson = new Gson();
            for (int i = 0; i < taskCount; i++) {
                Task task = adapter.getTask(i);
                String taskJson = gson.toJson(task);
                editor.putString(getString(R.string.TASK_JSON) + i, taskJson);
            }
            editor.apply();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter = null;
        taskRecyclerView = null;
        addTaskButton = null;
        addTaskEditText = null;
    }

    public void onAddItem() {
        String input = addTaskEditText.getText().toString();
        if (input.length() < 1) {
            return;
        }
        adapter.addTask(new Task(input));
        addTaskEditText.setText("");
    }
}