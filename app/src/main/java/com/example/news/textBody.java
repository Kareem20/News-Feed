package com.example.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class textBody extends AppCompatActivity {
    //texView for the article text.
    private TextView body;
    //ImageView for the image of article
    private ImageView image;
    //textView for the author of article.
    private TextView author;
    //textView for the date of the articls.
    private TextView date;
    //ImageView for the image of author.
    private ImageView imageViewOfAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_body);

        //find reference of article text.
        body = findViewById(R.id.textBody);
        //find reference of image of article.
        image = findViewById(R.id.image_article_textBody);
        //find reference of author of article.
        author = findViewById(R.id.author_textBody);
        //find reference of  date of article.
        date = findViewById(R.id.date_article_textBody);
        //find reference of image of article.
        imageViewOfAuthor = findViewById(R.id.image_of_author);

        //get the data of article from baseArticles class.
        final Intent i = getIntent();
        //get the article and put it on screen.
        String textBody = i.getStringExtra("textBody");
        body.setText(textBody);
        //get the author of article and put it on screen.
        String authorS = i.getStringExtra("author");
        author.setText(authorS);
        //get the date of article and put it on screen.
        String lastModifiedS = i.getStringExtra("lastModified");
        date.setText(getTimeDifference(formatDate(lastModifiedS)));
        //get the image of article and put it if it exsist on screen.
        String image2 = i.getStringExtra("image");
        if (image == null) {
            image.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
            // Load thumbnail with picasso
            //Picasso.with(this).load(image2).into(image);
            //TODO:change Picasso here.
            Picasso.get().load(image2).into(image);

        }
        //get the image of author and put it if it exsist on screen.
        String imageOfAuthor = i.getStringExtra("imageOfAuthor");
        if (imageOfAuthor == null) {
            imageViewOfAuthor.setVisibility(View.GONE);
        } else {
//            Picasso.with(this)
//                    .load(imageOfAuthor).transform(new CircleTransform())
//                    .into(imageViewOfAuthor);
            //TODO:change Picasso here.
            Picasso.get()
                    .load(imageOfAuthor).transform(new CircleTransform())
                    .into(imageViewOfAuthor);
        }


        //go back to Main activity.
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setDisplayShowHomeEnabled(true);

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
     * Get the formatted web publication date string in milliseconds
     *
     * @param formattedDate the formatted web publication date string
     * @return the formatted web publication date in milliseconds
     */
    private static long getDateInMillis(String formattedDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("MMM d, yyyy  h:mm a");
        long dateInMillis;
        Date dateObject;
        try {
            dateObject = simpleDateFormat.parse(formattedDate);
            dateInMillis = dateObject.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            Log.e("Problem parsing date", Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get the time difference between the current date and web publication date
     *
     * @param formattedDate the formatted web publication date string
     * @return time difference (i.e "9 hours ago")
     */
    private CharSequence getTimeDifference(String formattedDate) {
        long currentTime = System.currentTimeMillis();
        long publicationTime = getDateInMillis(formattedDate);
        return DateUtils.getRelativeTimeSpanString(publicationTime, currentTime,
                DateUtils.SECOND_IN_MILLIS);
    }

    // Go back to the MainActivity when up button in action bar is clicked on.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent myIntent = new Intent(this, MainActivity.class);
            //  myIntent.putExtra("key", value); //Optional parameters
            textBody.this.startActivity(myIntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void back(View view) {
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        textBody.this.startActivity(myIntent);
    }
}

/*inside class to make image circle by picasso*/
class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}