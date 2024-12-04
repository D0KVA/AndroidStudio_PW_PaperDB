package com.example.classwork;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import io.paperdb.Paper;

public class DetailActivity extends AppCompatActivity {
    private TextView titleTextView, authorTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        titleTextView = findViewById(R.id.titleTextView);
        authorTextView = findViewById(R.id.authorTextView);
        imageView = findViewById(R.id.imageView);

        String bookTitle = getIntent().getStringExtra("BOOK_TITLE");
        Book book = Paper.book().read(bookTitle, null);

        if (book != null) {
            titleTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthor());
            if (book.getImagePath() != null && !book.getImagePath().isEmpty()) {
                Glide.with(this)
                        .load(book.getImagePath())
                        .into(imageView);
            }
        }

        // Настройка кнопки "Назад"
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Включить кнопку "Назад"
            getSupportActionBar().setTitle("Детали книги");       // Установить заголовок
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Закрытие активити при нажатии на "Назад"
        onBackPressed();
        return true;
    }

}
