package jollyjots.android.com.jollyjots.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import jollyjots.android.com.jollyjots.R;
import jollyjots.android.com.jollyjots.adapters.TaskAdapter;
import jollyjots.android.com.jollyjots.models.Task;

public class MainActivity extends AppCompatActivity {

    private final static String TASKS_PREF = "TASKS_PREF";

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

        // TODO read from a database
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
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (adapter != null) {
            adapter.saveData(outState);
            // todo save to database
        }
        super.onSaveInstanceState(outState, outPersistentState);
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
        if(input.length() < 1){
            return;
        }
        adapter.addTask(new Task(input));
        addTaskEditText.setText("");
    }
}