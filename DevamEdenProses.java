package com.example.win7.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DevamEdenProses extends AppCompatActivity {

    private String grnTur, sicil, mak = null;
    final int CONTEXT_MENU_VIEW = 1;
    ListView lstvi2;
    String neden1;
    private Bundle extras = null;
    String idNeden;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devam_eden_proses);
        init();
        lstvi2 = (ListView) findViewById(R.id.lstDevam);
        dbConnect("10.3.0.107", "1433", "astg", "asd.1234");

        lstvi2.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                        neden1 = lstvi2.getItemAtPosition(position).toString();

                        registerForContextMenu(lstvi2);
                        openContextMenu(lstvi2);

                        Toast.makeText(getApplicationContext(), "Seçilen Devam Edilen Proses: " + neden1, Toast.LENGTH_SHORT).show();

                    }
                }
        );
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

            String queryString = "SELECT A.Durus_Turu_Nedeni  FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari] S INNER JOIN [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Durus_Tanimlari] A ON A.Sira_No = S.Durus_Turu_Nedeni WHERE Durus_Bitisi IS NULL AND (A.Sira_No between 1 AND 30) AND [Makina_Adi] ='" + mak + "' AND [Kayit_Giren_Türü]='" + grnTur + "'";
            ResultSet rs = statement.executeQuery(queryString);

            ArrayList<String> data = new ArrayList<String>();// kayit giren türü


            while (rs.next()) {
                String id = rs.getString("Durus_Turu_Nedeni");
                data.add(id);
            }
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            lstvi2.setAdapter(NoCoreAdapter);


            conn.close();

        } catch (Exception e) {
            Db_list.add("Error");
            e.printStackTrace();
        }
        return Db_list;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo) {
        //Context menu
        menu.setHeaderTitle("Devam Eden Proses İşlemleri");
        menu.add(Menu.NONE, CONTEXT_MENU_VIEW, Menu.NONE, "Durdur");
    }

    private void init() {


        /** Bir onceki sayfadan verileri alalim */
        extras = getIntent().getExtras();

        /** Verileri alip atamalari yapalim */
        grnTur = extras.getString(ArizaGiris.GIREN_TURU);

        sicil = extras.getString(ArizaGiris.SICIL);
        mak = extras.getString(ArizaGiris.MAKINE_ADI);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case CONTEXT_MENU_VIEW: {

                dbDurusBitisiGir("10.3.0.107", "1433", "astg", "asd.1234");

                Toast.makeText(getApplicationContext(), "Proses Duruş Sonlandırıldı...", Toast.LENGTH_SHORT).show();

                finish();

            }
            break;
        }

        return super.onContextItemSelected(item);
    }

    public List<String> dbDurusBitisiGir(String Host, String Port, String db_userid,
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
            dbDurusNedenID("10.3.0.107", "1433", "astg", "asd.1234");
            String queryString = "UPDATE [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari] SET [Durus_Bitisi] = GETDATE() WHERE Durus_Bitisi IS NULL  AND [Makina_Adi] ='" + mak + "' AND [Durus_Turu_Nedeni] ='" + idNeden + "' AND [Kayit_Giren_Türü]='" + grnTur + "'";
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


            String queryString = "SELECT [Sira_No] FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Durus_Tanimlari] where [Durus_Turu_Nedeni] ='" + neden1 + "'";
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

}
