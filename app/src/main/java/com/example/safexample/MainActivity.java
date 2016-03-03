package com.example.safexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.google.common.net.MediaType;

public class MainActivity extends AppCompatActivity {
    private Optional<SafDocument> opened = Optional.absent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setTypeAndNormalize(MediaType.ANY_TEXT_TYPE.toString());
                startActivityForResult(intent, 10);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if (opened.isPresent()) {
                    opened.get().write(getContentResolver(),
                            ((EditText) findViewById(R.id.text)).getText().toString());
                    Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.rename:
                if (opened.isPresent()) {
                    String newName = "renamed" + System.currentTimeMillis() + ".txt";
                    opened = Optional.of(opened.get().rename(getContentResolver(), newName));
                    ((EditText) findViewById(R.id.text)).setText(opened.get().read(getContentResolver()));
                    Toast.makeText(this, "Renamed successfully!", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    opened = Optional.of(new SafDocument(uri));
                    ((EditText) findViewById(R.id.text)).setText(opened.get().read(getContentResolver()));
                    setTitle(opened.get().filename(getContentResolver()));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
