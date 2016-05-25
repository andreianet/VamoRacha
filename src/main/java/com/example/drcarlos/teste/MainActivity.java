package com.example.drcarlos.teste;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_LEFT_ICON),R.drawable.icon_beer);
        setContentView(R.layout.activity_main);

        //habilitar o ícone do lado esquerdo
        ///// setFeatureDrawableResource(Window.FEATURE_LEFT_ICON


        //variáveis
        final double conta, racha, total;


        TextView txtConta = (TextView) findViewById(R.id.txtConta);
        TextView txtRacha = (TextView) findViewById(R.id.txtRacha);
        TextView txtTotal = (TextView) findViewById(R.id.txtTotal);
        final EditText edtConta = (EditText) findViewById(R.id.edtValorConta);
        final EditText edtRacha = (EditText) findViewById(R.id.edtRacha);
        final EditText edtTotal = (EditText) findViewById(R.id.edtTotal);

        Button btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                double conta = Double.parseDouble(edtConta.getText().toString().replace(".", "").replace(",", "."));//troca virgula por ponto
                double racha = Double.parseDouble(edtRacha.getText().toString());
                edtTotal.setText(String.valueOf(conta/racha));

                if (racha > 0)
                    edtTotal.setText(String.valueOf(conta/racha));
                else
                    edtTotal.setText("Divisão por zero, não aceita!");

                //FORMATAÇÃO MONETÁRIA --- Excelente Exemplo do Henrique Lacerda: http://www.henriquelacerda.com.br/pt/
                edtTotal.setInputType(InputType.TYPE_CLASS_NUMBER);
                edtTotal.addTextChangedListener(new TextWatcher() {

                    private boolean isUpdating = false;
                    //formatação do din no paises
                    private NumberFormat nf = NumberFormat.getCurrencyInstance();

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            //NÃO UTILIZA
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //evite que o metodo executa varias vezes
                        //se tiver ele entra em Loop

                        if (isUpdating){
                               isUpdating = false;
                               return;
                           }
                        isUpdating = true;
                        String str = s.toString();

                        //verifica se já existe mascara no texto

                        boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
                                            str.indexOf(".")> -1 ||str.indexOf(",") > -1);

                        //verificado se existe máscara
                        if(hasMask){
                            //Retiramos a mascara
                            str = str.replaceAll("R$", "").replaceAll("[,]", "");
                        }
                        try{
                            //transforma editTEXT em monetario
                            str = nf.format(Double.parseDouble(str)/100);
                            edtTotal.setText(str);
                            edtTotal.setSelection(edtTotal.getText().length());
                        }catch(NumberFormatException e){
                                s = "";
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                            //NAO UTILIZA
                    }
                });
            }



        });

        //limpar campos pós conta
        Button btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnLimpar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edtConta.setText("");
                edtRacha.setText("");
                edtTotal.setText("");
            }
        });






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
