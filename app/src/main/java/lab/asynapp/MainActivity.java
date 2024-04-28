package lab.asynapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.View;
import lab.asynapp.databinding.ActivityMainBinding;
import lab.asynapp.utilities.CurrenciesDataLoader;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListView currenciesView;
    private ArrayAdapter listAdapter;
    private Toast loadingToast;
    private Button loadDataButton;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.currenciesView = findViewById(R.id.currenciesView);
        ArrayList<String> items = new ArrayList<>();

        this.listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);

        this.currenciesView.setAdapter(this.listAdapter);

        loadDataButton = findViewById(R.id.buttonLoadData);

        loadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrenciesAsync();
            }
        });

        getCurrenciesAsync();
    }

    @SuppressLint("StaticFieldLeak")
    public void getCurrenciesAsync() {
        showLoadingLabel();

        new CurrenciesDataLoader() {
            @Override
            protected void onPostExecute(String result) {
                hideLoadingLabel();
                String[] currencies = result.split(",");
                listAdapter.clear();
                for (String currency : currencies) {
                    listAdapter.add(currency);
                }
                listAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
    private void hideLoadingLabel() {
        if (loadingToast != null) {
            new Handler().postDelayed(() -> loadingToast.cancel(), 2000);
        }
    }

    private void showLoadingLabel() {
        loadingToast = Toast.makeText(MainActivity.this, "Loading data...", Toast.LENGTH_SHORT);
        loadingToast.show();
    }
}