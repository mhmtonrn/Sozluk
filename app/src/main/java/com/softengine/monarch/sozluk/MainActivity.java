package com.softengine.monarch.sozluk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Model> list=null;
    CustomAdapter adapter;
    Context context;
    LinearLayout yazarlar_layout;

    private EditText mText;
    private Button mSendButtom;
    private ProgressDialog progressDialog;
    private ListView mListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=MainActivity.this;
        mText= (EditText) findViewById(R.id.m_a_text);
        mSendButtom= (Button) findViewById(R.id.m_a_send_button);
        mListe= (ListView) findViewById(R.id.main_listview);


            mSendButtom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(mText.getText())) {

                        new Fetch().execute();

                        mText.setText("");


                    } else {
                        Toast.makeText(MainActivity.this, "Çevirmek istediğin Metini girmen gerek", Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "beklenmeyen hata", Toast.LENGTH_SHORT).show();
                    }




                }
            });



    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private class Fetch extends AsyncTask<Void, Void, Void> {

        String metin=mText.getText().toString().replace('Ç', 'C').replace('İ', 'I').replace('Ş', 'S').replace('Ğ', 'G').replace('Ü', 'U').replace('Ö', 'O').replace('ç', 'c').replace('ö', 'o').replace('ü', 'u').replace('ğ', 'g').replace('ş', 's').replace('ı', 'i');

        String tex1,tex2,tex3,tex4;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle(R.string.warnings);
            progressDialog.setMessage("Kelimeler Çekiliyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try{

                Document doc  = Jsoup.connect("http://tureng.com/tr/turkce-ingilizce/"+metin).get();
                Elements elements = doc.select("table[class=table table-hover table-striped searchResultsTable]");


                Element table = elements.select("tbody").get(0);
                Elements rows = table.select("tr");

                list=new ArrayList<Model>();

                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);

                    Elements cols1 = row.select("td[class=rc0 hidden-xs]");
                    Elements cols2 = row.select("td[class=hidden-xs]");
                    Elements cols3 = row.select("td[class=en tm]");
                    Elements cols4 = row.select("td[class=tr ts]");

                    if (!TextUtils.isEmpty(cols1.text().toString()) && !TextUtils.isEmpty(cols2.text().toString()) && !TextUtils.isEmpty(cols3.text().toString()) && !TextUtils.isEmpty(cols3.text().toString())){

                        list.add(new Model(cols1.text().toString(), cols2.text().toString(), cols4.text().toString(), cols3.text().toString()));
                    }

                }
               // adapter=new CustomAdapter(MainActivity.this,list);




            }catch (Exception e){

                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                yazarlar_layout = (LinearLayout)findViewById(R.id.layout);
                mListe= (ListView) findViewById(R.id.main_listview);
                yazarlar_layout.setVisibility(View.VISIBLE);
                mListe.setAdapter(new CustomAdapter((Activity) context,list));
                progressDialog.dismiss();


            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Aradığınız kelime bulunamadı", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
