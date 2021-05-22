package edu.lewisu.cs.example.todofragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoHolder>{
    private List<ToDo> mToDos;

    public interface ToDoListAdapterOnClickHandler{
        void onClick(ToDo toDo);
    }

    private final ToDoListAdapterOnClickHandler mClickHandler;

    public ToDoListAdapter(ToDoListAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ToDoHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        if(mToDos !=null){
            ToDo current = mToDos.get(position);
            holder.mTitleTestView.setText(current.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        if (mToDos !=null)
            return mToDos.size();
        return 0;
    }

    public void setToDos(List<ToDo> toDos) {
        mToDos = toDos;
    }

    class ToDoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitleTestView;

        public ToDoHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTestView = itemView.findViewById(R.id.titleTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            ToDo toDo = mToDos.get(adapterPosition);
            mClickHandler.onClick(toDo);
        }
    }
}
