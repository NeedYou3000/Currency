package com.example.currency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction ft;
    CalculatorFragment calculatorFragment;
    CurrencyFragment currencyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft = getSupportFragmentManager().beginTransaction();
        calculatorFragment = new CalculatorFragment();
        currencyFragment = new CurrencyFragment();

        ft.add(R.id.fragment_layout, calculatorFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.calculator_menu){
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_layout, calculatorFragment);
            ft.commit();
        }else if(item.getItemId() == R.id.currency_menu){
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_layout, currencyFragment);
            ft.commit();
        }
        return true;
    }
}