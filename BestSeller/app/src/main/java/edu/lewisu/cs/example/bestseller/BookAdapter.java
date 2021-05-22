package edu.lewisu.cs.example.bestseller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private List<Book> books;
    private Context context;

    private final BookAdapterOnClickHandler clickHandler;

    public BookAdapter(BookAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public interface BookAdapterOnClickHandler {
        void onClick(Book book);
    }

    @Override @NonNull
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = books.get(position);

        String title = book.getTitle();
        int rank = book.getRank();
        String titleRank = context.getResources().getString(R.string.rank_title, rank, title);
        holder.bookDataTextView.setText(titleRank);

        String author = book.getAuthor();
        holder.authorDataTextView.setText(author);

        String link = book.getAmazon();
        holder.linkDataTextView.setText(link);
    }

    @Override
    public int getItemCount() {
        if(books != null)
            return books.size();
        return 0;
    }

    //update list
    public void setBookData(List<Book> bookData) {
        books = bookData;
        notifyDataSetChanged();
    }


    protected class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView bookDataTextView;
        private final TextView authorDataTextView;
        private final TextView linkDataTextView;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookDataTextView = itemView.findViewById(R.id.bookDataTextView);
            authorDataTextView = itemView.findViewById(R.id.bookAuthorTextView);
            linkDataTextView = itemView.findViewById(R.id.bookLinkTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Book book = books.get(adapterPosition);
            clickHandler.onClick(book);
        }
    }
}
