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
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SayiGir extends AppCompatActivity {
    public static String GIREN_TURU = "giren_turu";
    public static String BOLUM = "bolum";
    public static String SICIL = "sicil";
    public static String MAKINE_ADI = "makine_adi";
    public static String KAYIT_TUR = "kayit_turu";
    public static String KAYIT_TUR_POS = "kayit_turu_position";

    EditText sayi;
    String idBolum, idMak, idGiren, idNeden;

    private String grnTur, strbol, sicil, mak, durtur, ndnTurIn = null;
    private Bundle extras = null;
    ListView lstvi1;
    Button btn, f1, f2, f3, f4;
    String neden;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sayi_gir);
        sayi = (EditText) findViewById(R.id.editText25);
        lstvi1 = (ListView) findViewById(R.id.lstNedenGir);
        init();
        sayi.setEnabled(false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dbConnect("10.3.0.107", "1433", "astg", "asd.1234");

        f1 = (Button) findViewById(R.id.button5);
        f2 = (Button) findViewById(R.id.button10);
        f3 = (Button) findViewById(R.id.button11);
        f4 = (Button) findViewById(R.id.button12);


        btn = (Button) findViewById(R.id.btnSayiGir);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getApplicationContext(), "İlgili Kayıt Sayısı Sisteme Girilmiştir...", Toast.LENGTH_SHORT).show();
                dbConnect1("10.3.0.107", "1433", "astg", "asd.1234");
                btn.setEnabled(false);
                sayi.setEnabled(false);
                sayi.setText("");
                //finish();


            }
        });

        sayi.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() == 0) {
                    btn.setEnabled(false);
                } else {
                    btn.setEnabled(true);
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

        btn.setEnabled(false);

        lstvi1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                        neden = lstvi1.getItemAtPosition(position).toString();
                        sayi.setEnabled(true);
                        //btn.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Seçilen Durum: " + neden, Toast.LENGTH_SHORT).show();

                    }
                }
        );

        if (ndnTurIn.equals("2")) {
            f3.setEnabled(false);
        } else if (ndnTurIn.equals("3")) {
            f4.setEnabled(false);
        }

        f1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    /** Bundle olusturup verileri bundle'a ekleyelim */
                    Bundle extras = new Bundle();
                    extras.putString(GIREN_TURU, grnTur);
                    extras.putString(BOLUM, strbol);
                    extras.putString(SICIL, sicil);
                    extras.putString(MAKINE_ADI, mak);
                    extras.putString(KAYIT_TUR, "F1  - SET_UP NEDENLERİ");
                    extras.putString(KAYIT_TUR_POS, "0");


                    /** Intent olusturalim */
                    Intent intent = new Intent();

                    /** Bundle'i intente ekleyelim */
                    intent.putExtras(extras);

                    /** Yeni sayfayi cagiralim */
                    intent.setClass(getApplicationContext(), ArizaGiris.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        f2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    /** Bundle olusturup verileri bundle'a ekleyelim */
                    Bundle extras = new Bundle();
                    extras.putString(GIREN_TURU, grnTur);
                    extras.putString(BOLUM, strbol);
                    extras.putString(SICIL, sicil);
                    extras.putString(MAKINE_ADI, mak);
                    extras.putString(KAYIT_TUR, "F2  - ARIZA NEDENLERİ");
                    extras.putString(KAYIT_TUR_POS, "1");


                    /** Intent olusturalim */
                    Intent intent = new Intent();

                    /** Bundle'i intente ekleyelim */
                    intent.putExtras(extras);

                    /** Yeni sayfayi cagiralim */
                    intent.setClass(getApplicationContext(), ArizaGiris.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        f3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    /** Bundle olusturup verileri bundle'a ekleyelim */
                    Bundle extras = new Bundle();
                    extras.putString(GIREN_TURU, grnTur);
                    extras.putString(BOLUM, strbol);
                    extras.putString(SICIL, sicil);
                    extras.putString(MAKINE_ADI, mak);
                    extras.putString(KAYIT_TUR, "F3  - BOYA RÖTÜŞ SAYISI");
                    extras.putString(KAYIT_TUR_POS, "2");



                    /** Intent olusturalim */
                    Intent intent = new Intent();

                    /** Bundle'i intente ekleyelim */
                    intent.putExtras(extras);

                    /** Yeni sayfayi cagiralim */
                    intent.setClass(getApplicationContext(), SayiGir.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        f4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    /** Bundle olusturup verileri bundle'a ekleyelim */
                    Bundle extras = new Bundle();

                    extras.putString(GIREN_TURU, grnTur);
                    extras.putString(BOLUM, strbol);
                    extras.putString(SICIL, sicil);
                    extras.putString(MAKINE_ADI, mak);
                    extras.putString(KAYIT_TUR, "F4  - PARÇA RET NEDENLERİ");
                    extras.putString(KAYIT_TUR_POS, "3");


                    /** Intent olusturalim */
                    Intent intent = new Intent();

                    /** Bundle'i intente ekleyelim */
                    intent.putExtras(extras);

                    /** Yeni sayfayi cagiralim */
                    intent.setClass(getApplicationContext(), SayiGir.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public List<String> dbConnect1(String Host, String Port, String db_userid,
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

            String queryString = "INSERT INTO [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari] ([Calisilan_Bolum],[Makina_Adi],[Kayit_Giren_Türü],[Operator_Sicili] ,[Durus_Turu_Nedeni],[Durus_Neden_Sayi],[Durus_Baslangici]) VALUES ('" + dbBolumID("10.3.0.107", "1433", "astg", "asd.1234") + "','" + dbMakID("10.3.0.107", "1433", "astg", "asd.1234") + "','" + dbGirenID("10.3.0.107", "1433", "astg", "asd.1234") + "','" + sicil + "','" + dbDurusNedenID("10.3.0.107", "1433", "astg", "asd.1234") + "'," + sayi.getText().toString() + ",GETDATE())";

            statement.executeQuery(queryString);
            conn.close();

        } catch (Exception e) {
            Db_list.add("Error");
            e.printStackTrace();
        }
        return Db_list;
    }


    private void init() {


        /** Bir onceki sayfadan verileri alalim */
        extras = getIntent().getExtras();


        if (!extras.getString(ArizaGiris.GIREN_TURU).equals(null)) {
            grnTur = extras.getString(ArizaGiris.GIREN_TURU);
            strbol = extras.getString(ArizaGiris.BOLUM);
            sicil = extras.getString(ArizaGiris.SICIL);
            mak = extras.getString(ArizaGiris.MAKINE_ADI);
            durtur = extras.getString(ArizaGiris.KAYIT_TUR);
            ndnTurIn = extras.getString(ArizaGiris.KAYIT_TUR_POS);
        } else {
            /** Verileri alip atamalari yapalim */
            grnTur = extras.getString(Calisma_Alani.GIREN_TURU);
            strbol = extras.getString(Calisma_Alani.BOLUM);
            sicil = extras.getString(Calisma_Alani.SICIL);
            mak = extras.getString(Calisma_Alani.MAKINE_ADI);
            durtur = extras.getString(Calisma_Alani.KAYIT_TUR);
            ndnTurIn = extras.getString(Calisma_Alani.KAYIT_TUR_POS);
        }
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

            String queryString = "SELECT [Durus_Turu_Nedeni] FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Durus_Tanimlari] WHERE [Durus_Turu_Basligi] ='" + durtur + "'";
            ResultSet rs = statement.executeQuery(queryString);

            ArrayList<String> data = new ArrayList<String>();// kayit giren türü


            while (rs.next()) {
                String id = rs.getString("Durus_Turu_Nedeni");
                data.add(id);
            }
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            lstvi1.setAdapter(NoCoreAdapter);


            conn.close();

        } catch (Exception e) {
            Db_list.add("Error");
            e.printStackTrace();
        }
        return Db_list;
    }

    public String dbDurusNedenID(String Host, String Port, String db_userid,
                              String db_password) {


        try {
            String ConnectionString = "jdbc:jtds:sqlserver://" + Host + ":"
                    + Port;
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            final Connection conn = DriverManager.getConnection(ConnectionString,
                    db_userid, db_password);

            System.out.println("connected");

            Statement statement = conn.createStatement();


            String queryString = "SELECT [Sira_No] FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Durus_Tanimlari] where [Calisilan_Bolum]='" + idBolum + "' AND [Durus_Turu_Basligi]='" + durtur + "' AND [Durus_Turu_Nedeni]= '" + neden + "'";
            ResultSet rs = statement.executeQuery(queryString);


            while (rs.next()) {
                idNeden = rs.getString("Sira_No");

            }


            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return idNeden;
    }


    public String dbGirenID(String Host, String Port, String db_userid,
                              String db_password) {


        try {
            String ConnectionString = "jdbc:jtds:sqlserver://" + Host + ":"
                    + Port;
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            final Connection conn = DriverManager.getConnection(ConnectionString,
                    db_userid, db_password);

            System.out.println("connected");

            Statement statement = conn.createStatement();


            String queryString = "SELECT  [Sira_No] FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Kayit_Giren_Turleri_Tanimlari] where [Calisilan_Bolum]='" + idBolum + "'  AND  [Kayit_Giren_Turu] ='" + grnTur + "'";
            ResultSet rs = statement.executeQuery(queryString);


            while (rs.next()) {
                idGiren = rs.getString("Sira_No");

            }


            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return idGiren;
    }

    public String dbMakID(String Host, String Port, String db_userid,
                              String db_password) {


        try {
            String ConnectionString = "jdbc:jtds:sqlserver://" + Host + ":"
                    + Port;
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            final Connection conn = DriverManager.getConnection(ConnectionString,
                    db_userid, db_password);

            System.out.println("connected");

            Statement statement = conn.createStatement();


            String queryString = "SELECT [Sira_No] FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Makina_Tanimlari] where [Calisilan_Bolum]='" + idBolum + "'  AND  [Makina_Adi] ='" + mak + "'";
            ResultSet rs = statement.executeQuery(queryString);


            while (rs.next()) {
                idMak = rs.getString("Sira_No");

            }


            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return idMak;
    }


    public String dbBolumID(String Host, String Port, String db_userid,
                              String db_password) {


        try {
            String ConnectionString = "jdbc:jtds:sqlserver://" + Host + ":"
                    + Port;
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            final Connection conn = DriverManager.getConnection(ConnectionString,
                    db_userid, db_password);

            System.out.println("connected");

            Statement statement = conn.createStatement();


            String queryString = "SELECT [Sira_No] FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Bolum_Tanimlari] where [Calisilan_Bolum]='" + strbol + "'";
            ResultSet rs = statement.executeQuery(queryString);


            while (rs.next()) {
                idBolum = rs.getString("Sira_No");

            }


            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return idBolum;
    }

}
