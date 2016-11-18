package com.example.win7.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Calisma_Alani extends AppCompatActivity {
    Spinner spinner;
    String namemak;
    public static String GIREN_TURU = "giren_turu";
    public static String BOLUM = "bolum";
    public static String SICIL = "sicil";
    public static String MAKINE_ADI = "makine_adi";
    public static String KAYIT_TUR = "kayit_turu";
    public static String KAYIT_TUR_POS = "kayit_turu_position";
    Spinner spinner1;
    String name1tur;

    private String grnTur, strbol, sicil = null;
    private Bundle extras = null;

    Button btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calisma__alani);

        spinner = (Spinner) findViewById(R.id.spinMak);
        spinner1 = (Spinner) findViewById(R.id.spinMak1);

        btn = (Button) findViewById(R.id.btnKayit);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /** Bundle olusturup verileri bundle'a ekleyelim */
                Bundle extras = new Bundle();
                extras.putString(GIREN_TURU, grnTur);
                extras.putString(BOLUM, strbol);
                extras.putString(SICIL, sicil);
                extras.putString(MAKINE_ADI, namemak);
                extras.putString(KAYIT_TUR, name1tur);
                extras.putString(KAYIT_TUR_POS, String.valueOf(spinner1.getSelectedItemPosition()));

                /** Intent olusturalim */
                Intent intent = new Intent();

                /** Bundle'i intente ekleyelim */
                intent.putExtras(extras);
                if (name1tur.equals("F1  - SET_UP NEDENLERİ") || name1tur.equals("F2  - ARIZA NEDENLERİ")) {
                    // TODO Auto-generated method stub
                    try {
                        /** Yeni sayfayi cagiralim */
                        intent.setClass(getApplicationContext(), ArizaGiris.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        /** Yeni sayfayi cagiralim */
                        intent.setClass(getApplicationContext(), SayiGir.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        init();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dbConnect("10.3.0.107", "1433", "astg", "asd.1234");


    }


    private void init() {


        /** Bir onceki sayfadan verileri alalim */
        extras = getIntent().getExtras();

        /** Verileri alip atamalari yapalim */
        grnTur = extras.getString(MainActivity.NAME);
        strbol = extras.getString(MainActivity.BOLUM_ADI);
        sicil = extras.getString(MainActivity.SICIL);


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

            Statement statement = conn.createStatement();
            Statement statement1 = conn.createStatement();

            String queryString = "SELECT S.Makina_Adi FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Makina_Tanimlari] S INNER JOIN [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Bolum_Tanimlari] A ON S.Calisilan_Bolum = A.Sira_No WHERE A.Calisilan_Bolum ='" + strbol + "'";
            String queryString1 = "SELECT [Durus_Turu_Basligi] FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Durus_Tanimlari] where [Calisilan_Bolum] = 1 group by Durus_Turu_Basligi";
            ResultSet rs = statement.executeQuery(queryString);
            ResultSet rs1 = statement1.executeQuery(queryString1);

            ArrayList<String> data = new ArrayList<String>();// kayit giren türü
            ArrayList<String> data1 = new ArrayList<String>();// kayit giren türü


            while (rs.next()) {
                String id = rs.getString("Makina_Adi");
                data.add(id);
            }

            while (rs1.next()) {
                String id = rs1.getString("Durus_Turu_Basligi");
                data1.add(id);
            }


            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            spinner.setAdapter(NoCoreAdapter);
            ArrayAdapter NoCoreAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data1);
            spinner1.setAdapter(NoCoreAdapter1);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    namemak = spinner.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });


            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    name1tur = spinner1.getSelectedItem().toString();
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
