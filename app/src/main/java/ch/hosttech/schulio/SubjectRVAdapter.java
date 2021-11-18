package ch.hosttech.schulio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectRVAdapter extends RecyclerView.Adapter<SubjectRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<SubjectModal> subjectModalArrayList;
    private Context context;

    // constructor
    public SubjectRVAdapter(ArrayList<SubjectModal> subjectModalArrayList, Context context) {
        this.subjectModalArrayList = subjectModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        SubjectModal modal = subjectModalArrayList.get(position);
        holder.subjectName.setText(modal.getSubjectName());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return subjectModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView subjectName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            subjectName = itemView.findViewById(R.id.subjectName);
        }
    }
}