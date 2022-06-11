package com.example.currency;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CurrencyFragment extends Fragment {
    private final long MAX = (long)Math.pow(10,5)*Integer.MAX_VALUE; // set max value is accepted when input
    private final long MIN = -MAX; // set min value is accepted when input

    View view;
    Spinner fromSpinner;
    Spinner toSpinner;
    TextView fromTextView;
    TextView toTextView;
    String fromCurrency;
    String toCurrency;

    HashMap<String, Double> currencyRate = new HashMap<>(); // currencyRate to convert to 1 dollar

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_currency, container, false);

        initCurrencyRate();
        numberClicked();
        clearEditText();
        backspaceClicked();

        List<String> currencyArray = new ArrayList<>();
        Set keySet = currencyRate.keySet();
        Iterator iterator = keySet.iterator();
        while(iterator.hasNext()) {
            currencyArray.add((String)iterator.next());
        }

        fromSpinner = view.findViewById(R.id.fromSpinner);
        toSpinner = view.findViewById(R.id.toSpinner);
        fromTextView = view.findViewById(R.id.fromTextView);
        toTextView = view.findViewById(R.id.toTextView);

        fromTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double currency = Double.parseDouble(s.toString());
                calculate(currency, currencyRate.get(fromCurrency), currencyRate.get(toCurrency));
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                 android.R.layout.simple_dropdown_item_1line, currencyArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromCurrency = currencyArray.get(position);
                if (!fromTextView.getText().toString().equals("0")) fromTextView.setText("0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toCurrency = currencyArray.get(position);
                if (!fromTextView.getText().toString().equals("0")) fromTextView.setText("0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    private void calculate(double currency, double fromExchangeRate, double toExchangeRate){
        double result = currency * fromExchangeRate / toExchangeRate;
        toTextView.setText(String.format("%.2f", result));
    }

    private void initCurrencyRate() {
        currencyRate.put("United State - Dolar", 1.0);
        currencyRate.put("Europe - Euro", 1.0519);
        currencyRate.put("Viet Nam - VNDong", 0.00004315);
        currencyRate.put("Japan - Yen", 0.007439);
        currencyRate.put("Kore - Won", 0.0007817);
    }


    private void numberClicked() {
        view.findViewById(R.id._0Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('0');
            }
        });

        view.findViewById(R.id._1Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('1');
            }
        });

        view.findViewById(R.id._2Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('2');
            }
        });

        view.findViewById(R.id._3Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('3');
            }
        });

        view.findViewById(R.id._4Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('4');
            }
        });

        view.findViewById(R.id._5Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('5');
            }
        });

        view.findViewById(R.id._6Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('6');
            }
        });

        view.findViewById(R.id._7Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('7');
            }
        });

        view.findViewById(R.id._8Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('8');
            }
        });

        view.findViewById(R.id._9Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNumber('9');
            }
        });
    }
    // handle after clicked on a number button
    private void appendNumber(char c) {
        Log.v("TAG", "clicked");

        String largeEditTextString = fromTextView.getText().toString();
        if (largeEditTextString.equals("0")) largeEditTextString = "" + c;
        else largeEditTextString += c;
        if((Long.parseLong(largeEditTextString) <= MAX && largeEditTextString.charAt(0) != '-')
                || (Long.parseLong(largeEditTextString) >= MIN && largeEditTextString.charAt(0) == '-')){
            fromTextView.setText(largeEditTextString);
        } else if(largeEditTextString.charAt(0) != '-') {
            AlertDialog alertDialogBuilder = new AlertDialog.Builder(view.getContext()).create();
            alertDialogBuilder.setMessage("Cannot calculate with too large number!");
            alertDialogBuilder.show();
        }else {
            AlertDialog alertDialogBuilder = new AlertDialog.Builder(view.getContext()).create();
            alertDialogBuilder.setMessage("Cannot calculate with too small number!");
            alertDialogBuilder.show();
        }
    }

    // handle when click on 'CE' button
    private void clearEditText() {
        view.findViewById(R.id.ceButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromTextView.setText("0");
            }
        });
    }

    // handle when clicked on backspace button
    private void backspaceClicked() {
        view.findViewById(R.id.backspaceButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder fromEditTextString = new StringBuilder(fromTextView.getText().toString());
                if (fromEditTextString.length() > 1) {
                    fromEditTextString.deleteCharAt(fromEditTextString.length()-1);
                    fromTextView.setText(fromEditTextString);
                } else if (fromEditTextString.length() == 1) {
                    fromEditTextString.setCharAt(0, '0');
                    fromTextView.setText(fromEditTextString);
                }
            }
        });
    }
}