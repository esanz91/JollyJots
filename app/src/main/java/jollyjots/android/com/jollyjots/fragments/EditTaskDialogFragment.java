package jollyjots.android.com.jollyjots.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import jollyjots.android.com.jollyjots.models.TaskModel;

public class EditTaskDialogFragment extends DialogFragment{
    public EditTaskDialogFragment() {
        // Empty constructor is required for DialogFragment
    }

    public static EditTaskDialogFragment newInstance(TaskModel taskModel) {
        EditTaskDialogFragment frag = new EditTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", taskModel.get(title));
        frag.setArguments(args);
        return frag;
    }

}
