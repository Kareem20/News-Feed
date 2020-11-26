package com.example.news.Adapters;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.modals.newsList;
import com.example.news.textBody;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    /* @param context is the context of the app*/
    private final Context mContext;
    /* List of article items.*/
    private final List<newsList> mNewsLists;

    public NewsAdapter(Context context, List<newsList> newsLists) {
        mContext = context;
        mNewsLists = newsLists;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        // Find the current news that was clicked on
        final newsList currentNews = mNewsLists.get(position);
        holder.titleTextView.setText(currentNews.getTitleOfArticle());
        holder.sectionTextView.setText(currentNews.getSection());
        holder.dateTextView.setText(formatDate(currentNews.getDateOfArticle()));
        holder.trailTextView.setText(currentNews.getTrailText());
        // If the author does not exist, hide the authorTextView
        if (currentNews.getAuthor() == null) {
            holder.authorTextView.setVisibility(View.GONE);
        } else {
            holder.authorTextView.setVisibility(View.VISIBLE);
            holder.authorTextView.setText(currentNews.getAuthor());
        }
        holder.trailTextView.setText(currentNews.getTrailText());
        // If the image does not exist, hide the Image.
        if (currentNews.getImage() == null) {
            holder.thumbnailImageView.setVisibility(View.GONE);
        } else {
            holder.thumbnailImageView.setVisibility(View.VISIBLE);

            Picasso.get()
                    .load(currentNews.getImage())
                    .into(holder.thumbnailImageView);
        }
        // Set an OnClickListener to open the article page.
        //sent the data to textBody class.
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, textBody.class);

                intent.putExtra("textBody", currentNews.getArticlesText());
                intent.putExtra("author", currentNews.getAuthor());
                intent.putExtra("section", currentNews.getSection());
                intent.putExtra("image", currentNews.getImage());
                intent.putExtra("lastModified", currentNews.getLastModified());
                intent.putExtra("newsUri", currentNews.getUrl());
                intent.putExtra("imageOfAuthor", currentNews.getImageOfAuthor());
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(mContext, R.anim.fade_in, R.anim.fade_out);
                mContext.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNewsLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView sectionTextView;
        private final TextView authorTextView;
        private final TextView dateTextView;
        private final ImageView thumbnailImageView;
        private final TextView trailTextView;
        private final CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_article);
            sectionTextView = itemView.findViewById(R.id.section);
            authorTextView = itemView.findViewById(R.id.author);
            dateTextView = itemView.findViewById(R.id.date_article);
            thumbnailImageView = itemView.findViewById(R.id.image_article);
            trailTextView = itemView.findViewById(R.id.trail_text_card);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Convert date and time in UTC (webPublicationDate) into a more readable representation
     * in Local time
     *
     * @param dateStringUTC is the web publication date of the article (i.e. 2014-02-04T08:00:00Z)
     * @return the formatted date string in Local time(i.e "Jan 1, 2000  2:15 AM")
     * from a date and time in UTC
     */
    private String formatDate(String dateStringUTC) {
        // Parse the dateString into a Date object
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        Date dateObject = null;
        try {
            dateObject = simpleDateFormat.parse(dateStringUTC);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Initialize a SimpleDateFormat instance and configure it to provide a more readable
        // representation according to the given format, but still in UTC
        SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy  h:mm a", Locale.ENGLISH);
        String formattedDateUTC = df.format(dateObject);
        // Convert UTC into Local time
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(formattedDateUTC);
            df.setTimeZone(TimeZone.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(date);
    }

    /**
     * Clear all data (a list of {@link newsList} objects)
     */
    public void clear() {
        mNewsLists.clear();
        notifyDataSetChanged();
    }

    /**
     * Add  a list of {@link newsList}
     *
     * @param newsList is the list of news, which is the data source of the adapter
     */
    public void addAll(List<newsList> newsList) {
        mNewsLists.clear();
        mNewsLists.addAll(newsList);
        notifyDataSetChanged();
    }
}
