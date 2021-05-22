package edu.lewisu.cs.todotext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoHolder>{
    private List<ToDo> mToDos;
    public interface TodoListAdapterOnClickHandler {
        void onClick(ToDo toDo);
    }
    private final TodoListAdapterOnClickHandler clickHandler;

    public ToDoListAdapter(TodoListAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item,parent,false);
        return new ToDoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        if (mToDos != null) {
            ToDo current = mToDos.get(position);
            holder.mTitleTextView.setText(current.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if(mToDos != null){
            return mToDos.size();
        }
        return 0;
    }

    //update list
    public void setToDos(List<ToDo> toDos) {
        mToDos = toDos;
        notifyDataSetChanged();
    }


    class ToDoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView mTitleTextView;

        public ToDoHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.titleTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            ToDo toDo = mToDos.get(adapterPosition);
            clickHandler.onClick(toDo);
        }
    }
}