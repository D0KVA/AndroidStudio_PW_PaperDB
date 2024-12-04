package com.example.classwork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private EditText titleText, authorText, imageLinkText;
    private Button addButton, updateButton, deleteButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String selectedBookTitles;
    private Handler handler = new Handler();
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; // 300ms для двойного клика
    private Runnable singleClickRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Paper.init(this);

        titleText = findViewById(R.id.titleText);
        authorText = findViewById(R.id.AuthorText);
        imageLinkText = findViewById(R.id.imageLinkText);
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getBookTitles());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedBookTitles = adapter.getItem(position);
            Book book = Paper.book().read(selectedBookTitles, null);

            if (book != null) {
                titleText.setText(book.getTitle());
                authorText.setText(book.getAuthor());
                imageLinkText.setText(book.getImagePath());
            }

            // Обработка одиночного клика (задержка)
            handler.removeCallbacksAndMessages(null);  // удаляем предыдущие запланированные действия

            singleClickRunnable = () -> {
                // Одиночный клик — оставляем данные в полях
                Toast.makeText(this, "Редактирование книги: " + selectedBookTitles, Toast.LENGTH_SHORT).show();
            };
            handler.postDelayed(singleClickRunnable, DOUBLE_CLICK_TIME_DELTA);  // Ожидаем двойной клик

        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            selectedBookTitles = adapter.getItem(position);
            Book book = Paper.book().read(selectedBookTitles, null);

            if (book != null) {
                // Двойной клик — открытие DetailActivity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("BOOK_TITLE", selectedBookTitles);
                startActivity(intent);
            }

            return true;  // возвращаем true, чтобы избежать одиночного клика
        });

        addButton.setOnClickListener(view -> {
            String title = titleText.getText().toString();
            String author = authorText.getText().toString();
            String imagePath = imageLinkText.getText().toString();

            if (!title.isEmpty() && !author.isEmpty() && !imagePath.isEmpty()) {
                Book book = new Book(title, author, imagePath);
                Paper.book().write(title, book);
                updateBookList();
                clearInputs();
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(view -> {
            if (selectedBookTitles == null) {
                Toast.makeText(this, "Выберите книгу для обновления", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = titleText.getText().toString();
            String author = authorText.getText().toString();
            String imagePath = imageLinkText.getText().toString();

            if (!title.isEmpty() && !author.isEmpty() && !imagePath.isEmpty()) {
                Book book = new Book(title, author, imagePath);
                Paper.book().write(title, book);
                updateBookList();
                clearInputs();
            }
        });

        deleteButton.setOnClickListener(view -> {
            if (selectedBookTitles == null) {
                Toast.makeText(this, "Выберите книгу для удаления", Toast.LENGTH_SHORT).show();
                return;
            }
            Paper.book().delete(selectedBookTitles);
            updateBookList();
            clearInputs();
        });
    }

    private void clearInputs() {
        titleText.setText("");
        authorText.setText("");
        imageLinkText.setText("");
        selectedBookTitles = null;
    }

    private void updateBookList() {
        adapter.clear();
        adapter.addAll(getBookTitles());
        adapter.notifyDataSetChanged();
    }

    private List<String> getBookTitles() {
        return new ArrayList<>(Paper.book().getAllKeys());
    }
}
