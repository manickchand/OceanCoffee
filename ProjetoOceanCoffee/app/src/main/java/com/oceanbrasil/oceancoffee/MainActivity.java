package com.oceanbrasil.oceancoffee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;// quantidade de cafes
    String message="";

    //metodo padrao do android que cria a atividade
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //diz qual o layout referente a atividade
        setContentView(R.layout.activity_main);
    }

    public void gerateOrder(View view){

        //nameview agora faz referencia ao edittext etname no layout
        EditText nameView = (EditText) findViewById(R.id.etName);
        //name contem o texto escrito no edittext nameView
        String name = nameView.getText().toString();

        //whippedCreamView agora faz referencia ao CheckBox cbWhippedCream no layout
        CheckBox whippedCreamView = (CheckBox) findViewById(R.id.cbWhippedCream);
        // hasWhippedCream recebe true se whippedCreamView estiver marcado e false caso o contrario
        boolean hasWhippedCream = whippedCreamView.isChecked();

        CheckBox chocolateView = (CheckBox) findViewById(R.id.cbChocolate);
        boolean hasChocolate = chocolateView.isChecked();

        //price recebe o retorno do metodo calculatePrice
        int price = calculatePrice(quantity, 5, hasWhippedCream, hasChocolate);

        //summaryView agora faz referencia ao TextView summaryOrder no layout
        TextView summaryView = (TextView) findViewById(R.id.summaryOrder);

        //texto do summaryView e o retorno de createOrderSummary
        summaryView.setText(messageOrder(quantity,price,hasWhippedCream,hasChocolate,name));
    }

    public void submitOrder(View view){
        Log.i("ola","message: "+message.toString());

        //configura intent
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:mail@gmail.com"));// Only email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Ocean Coffee");
        intent.putExtra(Intent.EXTRA_TEXT, message.toString());
        //executa a intent
        startActivity(intent);
    }

    public void increment(View view){
        //verifica se quantidadde e menor que 100
        if(quantity >= 100){
            displayMessage("Só é permitido copos de 1-100");
            return;
        }
        quantity++;//incrementa quantidade
        displayQuantity();//mostra na tela
    }

    public void decrement(View view){
        //verifica se quantidadde e maior que 1
        if(quantity <= 1){
            displayMessage("Só é permitido copos de 1-100");
            return;
        }
        quantity--;//decrementa
        displayQuantity();//mostra na tela
    }

    private void displayMessage(String message) {
        //mostra uma mensagem caso a quantidade saia do intervalo 1 a 100
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void displayQuantity(){
        //quantityView agora faz referencia ao TextView quantity_text_view no layout
        TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);
        quantityView.setText(quantity + "");//atualiza texto do TextView
    }

    private String messageOrder(int quantity, int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        //mensagem mostrada com o  pedido e no corpo do email
        message = getString(R.string.name) + name;
        message += "\n" + getString(R.string.hasWhippedCream) + hasWhippedCream;
        message += "\n" + getString(R.string.hasChocolate) + hasChocolate;
        message += "\n" + getString(R.string.quantity) + quantity; // Message = message + ""
        message += "\n" + getString(R.string.price) + price;
        message += "\n" + getString(R.string.thank_you);

        return message;
    }

    private int calculatePrice(int quantity, int priceByCup, boolean hasWhippedCream, boolean hasChocolate){

        //adicional de creme ?
        if(hasWhippedCream){
            priceByCup += 1; // priceByCup = priceByCup + 1
        }

        //adicional de chocolate?
        if(hasChocolate){
            priceByCup += 2;
        }

        //retorna preco do pedido
        return quantity * priceByCup;
    }
}
