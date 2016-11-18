package com.example.win7.myapplication;


import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String name;// operatör click adı
    String bolumAdi;// operatör click adı
    Button btnGirs;
    EditText sic;


    public static String NAME = "name";
    public static String BOLUM_ADI = "bolum_adi";
    public static String SICIL = "sicil";


    Spinner spinnercountry;
    Spinner spinnercountry1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnercountry = (Spinner) findViewById(R.id.spinKayitGirenTur);
        spinnercountry1 = (Spinner) findViewById(R.id.spinBolum);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dbConnect("10.3.0.107", "1433", "astg", "asd.1234");

        sic = (EditText) findViewById(R.id.txtSicilNo);
        sic.setEnabled(true);
        btnGirs = (Button) findViewById(R.id.btnSisteme_Gir);
        btnGirs.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    /** Bundle olusturup verileri bundle'a ekleyelim */
                    Bundle extras = new Bundle();
                    extras.putString(NAME, name);
                    extras.putString(BOLUM_ADI, bolumAdi);
                    extras.putString(SICIL, sic.getText().toString());


                    /** Intent olusturalim */
                    Intent intent = new Intent();

                    /** Bundle'i intente ekleyelim */
                    intent.putExtras(extras);

                    /** Yeni sayfayi cagiralim */
                    intent.setClass(getApplicationContext(), Calisma_Alani.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnGirs.setEnabled(false);/// sicil no girmediyse sisteme giriş yapamayacaktır
        sic.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() == 0) {
                    btnGirs.setEnabled(false);
                } else {
                    btnGirs.setEnabled(true);
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public List<String> dbConnect(String Host, String Port, String db_userid,
                                  String db_password) {


        List<String> Db_list = new ArrayList<String>();

        try {
            String ConnectionString = "jdbc:jtds:sqlserver://" + Host + ":"
                    + Port;
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            final Connection conn = DriverManager.getConnection(ConnectionString,
                    db_userid, db_password);

            System.out.println("connected");

            Statement statement1 = conn.createStatement();
            Statement statement2 = conn.createStatement();


            String queryString1 = "SELECT Kayit_Giren_Turu FROM SMARTPHONE_TEST.dbo.Industry_4_0_Makina_Durus_Kayitlari_Kayit_Giren_Turleri_Tanimlari";
            String queryString2 = "SELECT  [Calisilan_Bolum] FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Bolum_Tanimlari]";

            ResultSet rs1 = statement1.executeQuery(queryString1);
            ResultSet rs2 = statement2.executeQuery(queryString2);


            ArrayList<String> data = new ArrayList<String>();// kayit giren türü
            ArrayList<String> data1 = new ArrayList<String>();// kayit giren türü


            while (rs1.next()) {
                String id = rs1.getString("Kayit_Giren_Turu");
                data.add(id);

            }

            while (rs2.next()) {
                String id = rs2.getString("Calisilan_Bolum");
                data1.add(id);

            }

            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            spinnercountry.setAdapter(NoCoreAdapter);
            ArrayAdapter NoCoreAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data1);
            spinnercountry1.setAdapter(NoCoreAdapter1);

            spinnercountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    name = spinnercountry.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            spinnercountry1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    bolumAdi = spinnercountry1.getSelectedItem().toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            conn.close();

        } catch (Exception e) {
            Db_list.add("Error");
            e.printStackTrace();
        }
        return Db_list;
    }


}

