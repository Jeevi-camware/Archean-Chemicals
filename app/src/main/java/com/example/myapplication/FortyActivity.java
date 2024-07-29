package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.opencsv.CSVReader;

public class FortyActivity extends AppCompatActivity {

    private TextView selectedDateTextView;
    private Button calendarButton;
    private TextView PumpstartTextView;
    private TextView flowlevelTextView;
    private TextView totalTextView;

    private static final String CHANNEL_ID = "2581906"; // Replace with your actual channel ID
    private static final String READ_API_KEY = "2EYX3R3SQPNAIS34"; // Replace with your actual read API key

    private static final int PERMISSION_REQUEST_CODE = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forty);

        selectedDateTextView = findViewById(R.id.selectedDate);
        calendarButton = findViewById(R.id.calendar);
        PumpstartTextView = findViewById(R.id.pumpbox);
        flowlevelTextView = findViewById(R.id.flowbox);
        totalTextView = findViewById(R.id.totalbox);

        calendarButton.setClickable(true);
        calendarButton.setEnabled(true);

        checkAndRequestPermissions();

        new FetchThingSpeakData().execute();

        calendarButton.setOnClickListener(v -> openDatePicker());
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivityIfNeeded(intent, PERMISSION_REQUEST_CODE);
                } catch (Exception exception) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    startActivityIfNeeded(intent, PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
                Log.e("Permission", "Permission granted");
            } else {
                Toast.makeText(this, "Permissions denied. Please enable storage permissions in app settings.", Toast.LENGTH_SHORT).show();
                Log.e("Permission", "Permission denied");

            }
        }
    }

    private void openDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Start Date");
        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Long>) selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = format.format(calendar.getTime());
            selectedDateTextView.setText(formattedDate);
            Log.d("FortyActivity", "Selected start date: " + formattedDate);

            new FetchCSVFileTask().execute(formattedDate);
        });
    }

    private class FetchThingSpeakData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String response = "";
            try {
                URL url = new URL("https://api.thingspeak.com/channels/" + CHANNEL_ID +
                        "/feeds.json?api_key=" + READ_API_KEY + "&results=1000"); // Fetch only latest entry
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    response = result.toString();
                    Log.d("ThingSpeakResponse", response);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ThingSpeakError", "Error fetching data", e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("ThingSpeakResponse", response); // Log the complete response
            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray feeds = jsonResponse.getJSONArray("feeds");

                for (int i = 0; i < feeds.length(); i++) {
                    JSONObject feed = feeds.getJSONObject(i);
                    Log.d("FeedEntry", feed.toString());

                    // Replace with actual field names from the JSON response
                    String Pumpstart = feed.optString("field1", "N/A");
                    String flow = feed.optString("field2", "N/A");
                    String total = feed.optString("field3", "N/A");

                    // You can update the UI here if you want to display multiple entries
                    // For simplicity, this example logs the data
                    Log.d("Entry " + i, "Pumpstart: " + Pumpstart + ", Flow: " + flow + ", Total: " + total);
                }

                if (feeds.length() > 0) {

                    JSONObject latestFeed = feeds.getJSONObject(feeds.length() - 1);
                    String Pumpstart = latestFeed.optString("field1", "N/A");
                    String flow = latestFeed.optString("field2", "N/A");
                    String total = latestFeed.optString("field3", "N/A");

                    PumpstartTextView.setText(Pumpstart);
                    flowlevelTextView.setText(flow);
                    totalTextView.setText(total);
                }

            } catch (Exception e) {
                Log.e("ThingSpeakError", "Error parsing data", e);
            }
        }
    }

    private class FetchCSVFileTask extends AsyncTask<String, Void, List<String[]>> {

        @Override
        protected List<String[]> doInBackground(String... params) {
            List<String[]> csvData = null;
            String startDate = params[0];
            String endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());

            try {
                String urlString = "https://api.thingspeak.com/channels/" + CHANNEL_ID +
                        "/feeds.csv?api_key=" + READ_API_KEY  + "&start=" + startDate + "&end=" + endDate;

                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    CSVReader csvReader = new CSVReader(reader);
                    csvData = csvReader.readAll();

                    csvReader.close();
                    reader.close();
                    in.close();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("CSVDownloadError", "Error downloading or parsing CSV file", e);
            }
            return csvData;
        }

        @Override
        protected void onPostExecute(List<String[]> csvData) {
            if (csvData != null && !csvData.isEmpty()) {
                if (csvData.size() > 1) {
                    saveCsvToFile(csvData);
                } else {
                    Toast.makeText(FortyActivity.this, "No data found for the selected date range", Toast.LENGTH_SHORT).show();
                }
            }  else {
                Toast.makeText(FortyActivity.this, "Failed to download or parse CSV file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveCsvToFile(List<String[]> csvData) {
        try {
            File csvFile = new File(getExternalFilesDir(null), "data.csv");
            FileOutputStream fos = new FileOutputStream(csvFile);
            StringBuilder csvContent = new StringBuilder();

            for (String[] row : csvData) {
                csvContent.append(String.join(",", row)).append("\n");
            }

            fos.write(csvContent.toString().getBytes());
            fos.close();

            if (csvFile.exists()) {
                Log.d("FileSaveSuccess", "CSV file saved successfully: " + csvFile.getAbsolutePath());
                openCsvFile(csvFile);
            } else {
                Log.e("FileSaveError", "CSV file was not saved");
                Toast.makeText(this, "Failed to save CSV file", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("FileSaveError", "Error saving CSV file", e);
            Toast.makeText(this, "Error saving CSV file", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCsvFile(File csvFile) {
        try {
            Uri fileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", csvFile);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/csv");
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Use a chooser to allow the user to select the app to open the CSV file
            Intent chooser = Intent.createChooser(intent, "Open CSV file with");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            } else {
                Toast.makeText(this, "No app found to open CSV files. Try searching for CSV reader apps on the Play Store.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("FileOpenError", "Error opening CSV file", e);
            Toast.makeText(this, "Error opening CSV file", Toast.LENGTH_SHORT).show();
        }
    }
}


