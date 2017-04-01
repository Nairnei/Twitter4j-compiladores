package com.example.rodrigonairnei.tweet;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import twitter4j.Twitter;

public class MainActivity extends AppCompatActivity {

    private String tweetWords;
    private int nTweets = 100;

    public int getnTweets() {
        return nTweets;
    }

    public void setnTweets(int nTweets) {
        this.nTweets = nTweets;
    }

    public String getTweetWords() {
        return tweetWords;
    }

    public void setTweetWords(String tweetWords) {
        this.tweetWords = tweetWords;
    }

    ArrayList<Tweet> data;

    File diretorio;
    String nomeDiretorio = "ModuloA";
    String diretorioApp;
    File fileExt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout tableFunc = (LinearLayout) findViewById(R.id.TableTweets);
        final LayoutInflater Li = LayoutInflater.from(getApplicationContext());
        Button pesquisar = (Button) findViewById(R.id.ButtonPesquisar);
        final Button enviartxt = (Button) findViewById(R.id.buttonEnviarTXT);
        Button gerarTxt = (Button) findViewById(R.id.buttonTXT);
        final EditText tema = (EditText) findViewById(R.id.editTexTAssunto);
        final EditText quantidade = (EditText) findViewById(R.id.editTextQuantidade);
        enviartxt.setEnabled(false);

        enviartxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);

                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"ronairnei@gmail.com", "sole.dade.lg@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Saida Modulo A");
                email.putExtra(Intent.EXTRA_TEXT, "Saida gerada pelo modulo A - compiladores 2017/1");
                Uri uri = Uri.fromFile(fileExt);
                email.putExtra(Intent.EXTRA_STREAM, uri);
                email.setType("message/rfc822");
                startActivity(email);
            }
        });

        gerarTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                diretorioApp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/" + nomeDiretorio + "/";

                diretorio = new File(diretorioApp);
                diretorio.mkdirs();

                //Quando o File() tem um parâmetro ele cria um diretório.
                //Quando tem dois ele cria um arquivo no diretório onde é informado.
                fileExt = new File(diretorioApp, "saida.txt");

                //Cria o arquivo
                fileExt.getParentFile().mkdirs();

                //Abre o arquivo
                FileOutputStream fosExt = null;
                try {
                    fosExt = new FileOutputStream(fileExt);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //Escreve no arquivo
                try {
                    Iterator i = data.iterator();
                    while (i.hasNext()) {
                        Tweet tmp = (Tweet) i.next();
                        String t = "&&" + tmp.text + "\n";
                        fosExt.write(t.getBytes());

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

//Obrigatoriamente você precisa fechar
                try {
                    fosExt.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                enviartxt.setEnabled(true);
            }

        });


        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tableFunc.removeAllViews();
                if (quantidade.getText().toString().equals("")) {
                    setnTweets(100);
                } else {
                    setnTweets(Integer.parseInt(quantidade.getText().toString()));
                }


                setTweetWords(tema.getText().toString());
                Twitter twitter = TwitterUtils.createTwitter();
                data = TwitterUtils.search(twitter, tweetWords, nTweets);
                Iterator i = data.iterator();
                while (i.hasNext()) {


                    Tweet tweet = (Tweet) i.next();
                    System.out.println(tweet.text);

                    final View as = Li.inflate(R.layout.twwt_item, null);

                    Button buttonItemRemover = (Button) as.findViewById(R.id.buttonItemRemover);

                    TextView titulo = (TextView) as.findViewById(R.id.textViewItemTitulo);
                    TextView descricao = (TextView) as.findViewById(R.id.textViewItemDescricao);
                    TextView adicional = (TextView) as.findViewById(R.id.textViewItemAdicional);

                    titulo.setText(String.valueOf(tweet.user));
                    descricao.setText(String.valueOf(tweet.id));
                    adicional.setText(String.valueOf(tweet.text));

                    buttonItemRemover.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tableFunc.removeView(as);

                        }
                    });

                    tableFunc.addView(as);
                }
            }

        });


    }
}


