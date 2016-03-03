package com.example.safexample;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SafDocument implements Parcelable {
    public static final Creator<SafDocument> CREATOR = new Creator<SafDocument>() {
        @Override
        public SafDocument createFromParcel(Parcel in) {
            return new SafDocument(in);
        }

        @Override
        public SafDocument[] newArray(int size) {
            return new SafDocument[size];
        }
    };

    private final Uri document;

    public SafDocument(Uri document) {
        this.document = document;
    }

    protected SafDocument(Parcel in) {
        document = in.readParcelable(Uri.class.getClassLoader());
    }

    public String filename(ContentResolver context) {
        try (Cursor cursor = context.query(document, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        }
        String path = document.getPath();
        int cut = path.lastIndexOf('/');
        return cut != -1 ? path.substring(cut + 1) : path;
    }

    public SafDocument rename(ContentResolver context, String newFilename) {
        Uri newLocation = DocumentsContract.renameDocument(context, document, newFilename);
        return new SafDocument(newLocation);
    }

    public ByteSource source(final ContentResolver reader) {
        return new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return reader.openInputStream(document);
            }
        };
    }

    public String read(ContentResolver reader) {
        try {
            return source(reader).asCharSource(Charsets.UTF_8).read();
        } catch (IOException e) {
            Log.w("grok", e);
            try {
                return tryAsFileDescriptor(reader);
            } catch (IOException e2) {
                throw new IllegalArgumentException("Cannot read from " + document, e2);
            }
        }
    }

    private String tryAsFileDescriptor(final ContentResolver resolver) throws IOException {
        return new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                AssetFileDescriptor r = resolver.openAssetFileDescriptor(document, "r");
                return new FileInputStream(r.getFileDescriptor());
            }
        }.asCharSource(Charsets.UTF_8).read();
    }

    public ByteSink sink(final ContentResolver destination) {
        return new ByteSink() {
            @Override
            public OutputStream openStream() throws IOException {
                return destination.openOutputStream(document);
            }
        };
    }

    public void write(final ContentResolver writer, String newContent) {
        try {
            ByteSource.wrap(newContent.getBytes()).copyTo(sink(writer));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot write to " + document, e);
        }
    }

    @Override
    public int describeContents() {
        return document.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        document.writeToParcel(dest, flags);
    }
}
