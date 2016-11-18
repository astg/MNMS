package com.example.win7.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArizaGiris extends AppCompatActivity {

    ListView lstvi1;
    String neden;
    TextView yan;

    String idBolum, idMak, idGiren, idNeden;

    public static String GIREN_TURU = "giren_turu";
    public static String BOLUM = "bolum";
    public static String SICIL = "sicil";
    public static String MAKINE_ADI = "makine_adi";
    public static String KAYIT_TUR = "kayit_turu";
    public static String KAYIT_TUR_POS = "kayit_turu_position";

    Button btnBaslat, btnDurdur, btnDevam, f1, f2, f3, f4;
    private String grnTur, strbol, sicil, mak, durtur, ndnTurIn = null;
    private Bundle extras = null;

    @Override


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariza_giris2);

        yan = (TextView) findViewById(R.id.textView3);
        f1 = (Button) findViewById(R.id.button6);
        f2 = (Button) findViewById(R.id.button7);
        f3 = (Button) findViewById(R.id.button8);
        f4 = (Button) findViewById(R.id.button9);
        lstvi1 = (ListView) findViewById(R.id.lstNedenGir);
        init();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dbConnect("10.3.0.107", "1433", "astg", "asd.1234");
        dbBolumID("10.3.0.107", "1433", "astg", "asd.1234");
        dbMakID("10.3.0.107", "1433", "astg", "asd.1234");
        dbGirenID("10.3.0.107", "1433", "astg", "asd.1234");
        dbDurusNedenID("10.3.0.107", "1433", "astg", "asd.1234");
        yan.setBackgroundResource(R.color.red);// işlem yapılmazken red yanar doğal olarak
        lstvi1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                        neden = lstvi1.getItemAtPosition(position).toString();
                        btnBaslat.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Seçilen Neden: " + neden, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnBaslat = (Button) findViewById(R.id.btnBaslat);
        btnBaslat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                btnBaslat.setEnabled(false);
                btnDurdur.setEnabled(true);
                Toast.makeText(getApplicationContext(), "İlgili Duruş Kayıt Açılmıştır...", Toast.LENGTH_SHORT).show();
                dbConnect1("10.3.0.107", "1433", "astg", "asd.1234");

                yan.setBackgroundResource(R.color.green);
            }
        });

        btnDurdur = (Button) findViewById(R.id.btnDurdur);
        btnDurdur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                btnDurdur.setEnabled(false);
                dbConnect2("10.3.0.107", "1433", "astg", "asd.1234");
                Toast.makeText(getApplicationContext(), "İşlem Tamamlandı...", Toast.LENGTH_SHORT).show();
                yan.setBackgroundResource(R.color.red);//
            }
        });

        btnBaslat.setEnabled(false);
        btnDurdur.setEnabled(false);

        btnDevam = (Button) findViewById(R.id.btnDevamEden);
        btnDevam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(getApplicationContext(), "İlgili Makine ile İlişkili Proses Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
                /** Intent olusturalim */
                try {
                    /** Bundle olusturup verileri bundle'a ekleyelim */
                    Bundle extras = new Bundle();
                    extras.putString(GIREN_TURU, idGiren);
                    extras.putString(SICIL, sicil);
                    extras.putString(MAKINE_ADI, idMak);
                    btnDurdur.setEnabled(false);
                    yan.setBackgroundResource(R.color.red);

                    Intent intent = new Intent();

                    /** Bundle'i intente ekleyelim */
                    intent.putExtras(extras);

                    /** Yeni sayfayi cagiralim */
                    intent.setClass(getApplicationContext(), DevamEdenProses.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        if (ndnTurIn.equals("0")) {
            f1.setEnabled(false);
        } else if (ndnTurIn.equals("1")) {
            f2.setEnabled(false);
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
            }
            if (resultCode == RESULT_CANCELED) {
                //handle cancel
            }
        }
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
            Statement statement1 = conn.createStatement();

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


            /*final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
             SecureRandom rnd = new SecureRandom();

                for( int i = 0; i < 20; i++ )
                    sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );MANVDJURT1","1433","astg","asd.1234
*/

            String queryString = "INSERT INTO [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari] ([Calisilan_Bolum],[Makina_Adi],[Kayit_Giren_Türü],[Operator_Sicili] ,[Durus_Turu_Nedeni],[Durus_Baslangici]) VALUES ('" + dbBolumID("10.3.0.107", "1433", "astg", "asd.1234") + "','" + dbMakID("10.3.0.107", "1433", "astg", "asd.1234") + "','" + dbGirenID("10.3.0.107", "1433", "astg", "asd.1234") + "','" + sicil + "','" + dbDurusNedenID("10.3.0.107", "1433", "astg", "asd.1234") + "',GETDATE())";

            statement.executeQuery(queryString);


            conn.close();

        } catch (Exception e) {
            Db_list.add("Error");
            e.printStackTrace();
        }
        return Db_list;
    }


    public List<String> dbConnect2(String Host, String Port, String db_userid,
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

            String queryString = "UPDATE [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari] SET [Durus_Bitisi] = GETDATE() WHERE Durus_Bitisi IS NULL AND [Operator_Sicili] ='" + sicil + "' AND [Makina_Adi] ='" + idMak + "' AND [Durus_Turu_Nedeni] ='" + idNeden + "' AND [Kayit_Giren_Türü]='" + idGiren + "'";
            statement.executeQuery(queryString);


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
