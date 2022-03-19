package com.example.henvezz.calcolatrice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GraphMain extends AppCompatActivity {

    private TextView screen;
    private String str = "";
    private int length = 1000;
    private String[] espressione = new String[length];
    private int uguale = 0;
    private int deg = 0;
    private int inv = 0;
    private int hyp = 0;
    private int index = -1;
    private int digitazioneNumero = 0;
    private int parentesiAperta = 0;
    private int parentesiChiusa = 0;
    private int parentesiDaChiudere=0;
    public final static String EXTRA_FUNCTION = "com.example.henvezz.calcolatrice.00";
    public final static String EXTRA_LENGTH = "com.example.henvezz.calcolatrice.01";
    public final static String EXTRA_DEG = "com.example.henvezz.calcolatrice.02";
    public android.view.View.OnLongClickListener longClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            index = -1;
            digitazioneNumero = 0;
            parentesiAperta = 0;
            parentesiChiusa = 0;
            uguale = 0;
            str = "y=";
            screen.setText(str);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_main);
        screen = (TextView) findViewById(R.id.textview);
        screen.setMovementMethod(new ScrollingMovementMethod());
        Button canc = (Button) findViewById(R.id.canc);
        if(savedInstanceState!=null){
            espressione=savedInstanceState.getStringArray("Espressione");
            digitazioneNumero=savedInstanceState.getInt("Digit");
            parentesiAperta=savedInstanceState.getInt("Aperta");
            parentesiChiusa=savedInstanceState.getInt("Chiusa");
            index=savedInstanceState.getInt("Index");
        }
        str = "y=";
        for (int i = 0; i <= index; i++) {
            str = str + espressione[i];
        }
        screen.setText(str);
        canc.setOnLongClickListener(longClick);
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putStringArray("Espressione",espressione);
        savedInstanceState.putInt("Digit",digitazioneNumero);
        savedInstanceState.putInt("Aperta",parentesiAperta);
        savedInstanceState.putInt("Chiusa",parentesiChiusa);
        savedInstanceState.putInt("Index",index);




        super.onSaveInstanceState(savedInstanceState);
    }
    public void startCalculator (View view){
        finish();
    }

    public void immissioneNumeroTastiera_G(View v) {
        Button button = (Button) v;

       //in caso a precedere sia una costante
        if(index>=0) {
            if (espressione[index] == "e" || espressione[index] == "x" || espressione[index] == "π" ||
                    espressione[index] == ")" || espressione[index] == "!") {
                index++;
                espressione[index] = "*";
                digitazioneNumero = 0;
                parentesiChiusa=0;
            }
        }
//in caso il risultato sia ancora su schermo
        if (parentesiChiusa == 0) {
            if (uguale == 1) {
                index--;
                uguale = 0;
                digitazioneNumero = 0;
            }}
        //inserimento numero
        if (digitazioneNumero == 0) {
            index++;
            espressione[index] = button.getText().toString();
            digitazioneNumero = 1;
        } else {
            espressione[index] = espressione[index] + button.getText().toString();
        }
            //visuallizza a schermo
            str = "y=";
            for (int i = 0; i <= index; i++) {
                str = str + espressione[i];
            }
            parentesiAperta = 0;
            screen.setText(str);


    }
    public void immissioneOperandoDaTastiera_G(View v) {
        Button button = (Button) v;

        if (digitazioneNumero == 1 && parentesiAperta == 0) {
            str = button.getText().toString();
            index++;
            if (str.equals("+")) espressione[index] = "+";
            if (str.equals("-")) espressione[index] = "-";
            if (str.equals("*")) espressione[index] = "*";
            if (str.equals("/")) espressione[index] = "/";
            if (str.equals("^")) espressione[index] = "^";
            digitazioneNumero = 0;
            if (str.equals("!")) {
                espressione[index] = "!";
                digitazioneNumero = 1;
            }
            parentesiAperta = 0;
            parentesiChiusa = 0;
        } else if (digitazioneNumero == 0) {
            str = button.getText().toString();
            if (str.equals("-")) {
                index++;
                espressione[index] = "-";
                digitazioneNumero = 1;
            }

        }

        str = "y=";
        for (int i = 0; i <= index; i++) {
            str = str + espressione[i];
        }
        uguale = 0;
        screen.setText(str);


    }
    public void immissioneParentesiAperta_G(View v) {

        //nel caso il risultato sia ancora a schermo
        if (uguale == 1) {
            index--;
            uguale = 0;
            digitazioneNumero = 0;
        }
        //per impedire di avere indice negativo
        if(index==-1){
            index++;
            espressione[index]="";
        }
        //Aggiunta * se necessario
        boolean segueUnNumero=true;
        try{
            Double.parseDouble(espressione[index]);
        }catch(NumberFormatException e){
            segueUnNumero=false;
        }
        if(segueUnNumero==true){
            index++;
            espressione[index]="*";
            digitazioneNumero=0;
        }
        if(espressione[index]=="e" || espressione[index]=="x" || espressione[index]=="π"|| espressione[index]==")" || espressione[index] == "!"){
            index++;
            espressione[index]="*";
            digitazioneNumero=0;
        }

        //immissione parentesi

        if(espressione[index].length()>0){
            index++;
        } //per risolvere il bug della stringa nulla

        espressione[index] = "(";
        parentesiAperta = 1;
        parentesiChiusa = 0;

        str = "y=";
        for (int i = 0; i <= index; i++) {
            str = str + espressione[i];
        }
        screen.setText(str);

    }
    public void immissioneParentesiChiusa_G(View v) {

        if (digitazioneNumero == 1 && uguale == 0 && parentesiAperta == 0) {
            index++;
            espressione[index] = ")";
            parentesiAperta = 0;
            parentesiChiusa = 1;
        }
        str = "y=";
        for (int i = 0; i <= index; i++) {
            str = str + espressione[i];
        }
        screen.setText(str);
    }
    public void immissioneXOCostante_G(View v) {
        Button button = (Button) v;
        if (uguale == 1) {
            index--;
            uguale = 0;
            digitazioneNumero = 0;
        }
        //per impedire di avere indice negativo
        if(index==-1){
            index++;
            espressione[index]="";
        }

        if(index>=0){
            boolean segueUnNumero=true;
            try{
                Double.parseDouble(espressione[index]);
            }catch(NumberFormatException e){
                segueUnNumero=false;
            }
            if(segueUnNumero==true){
                index++;
                espressione[index]="*";
            }
            if(espressione[index]=="e" || espressione[index]=="x" || espressione[index]=="π"||
                    espressione[index]==")" || espressione[index] == "!"){
                index++;
                espressione[index]="*";
            }
        }

        if(espressione[index].length()>0){
            index++;
        }

        str = button.getText().toString();
        if (str.equals("π")) espressione[index] = "π";
        if (str.equals("e")) espressione[index] = "e";
        if (str.equals("x")) espressione[index] = "x";

        uguale=0;
        digitazioneNumero=1;
        parentesiAperta=0;
        str = "y=";
        for (int i = 0; i <= index; i++) {
            str = str + espressione[i];
        }
        screen.setText(str);
    }
    public void immissioneOperandoUnario_G(View v) {
        Button button = (Button) v;
        if (uguale == 1) {
            index--;
            uguale = 0;
            digitazioneNumero = 0;
        }
        if(index==-1){
            index++;
            espressione[index]="";
        }
        //aggiunta *
        boolean segueUnNumero=true;
        try{
            Double.parseDouble(espressione[index]);
        }catch(NumberFormatException e){
            segueUnNumero=false;
        }
        if(segueUnNumero==true){
            index++;
            espressione[index]="*";
            digitazioneNumero=0;
        }
        if(espressione[index]=="e" || espressione[index]=="x" || espressione[index]=="π"| espressione[index]==")"){
            index++;
            espressione[index]="*";
            digitazioneNumero=0;
        }


        str = button.getText().toString();
        if(espressione[index].length()>0){
            index++;
        }
        if (str.equals("sin")) espressione[index] = "sin";
        if (str.equals("cos")) espressione[index] = "cos";
        if (str.equals("tan")) espressione[index] = "tan";
        if (str.equals("erf")) espressione[index] = "erf";
        if (str.equals("asin")) espressione[index] = "asin";
        if (str.equals("acos")) espressione[index] = "acos";
        if (str.equals("atan")) espressione[index] = "atan";
        if (str.equals("inverf")) espressione[index] = "inverf";
        if (str.equals("sinh")) espressione[index] = "sinh";
        if (str.equals("cosh")) espressione[index] = "cosh";
        if (str.equals("tanh")) espressione[index] = "tanh";
        if (str.equals("asinh")) espressione[index] = "asinh";
        if (str.equals("acosh")) espressione[index] = "acosh";
        if (str.equals("atanh")) espressione[index] = "atanh";
        if (str.equals("√")) espressione[index] = "√";
        if (str.equals("abs")) espressione[index] = "Abs";
        if (str.equals("log")) espressione[index] = "log";
        if (str.equals("ln")) espressione[index] = "ln";
        if (str.equals("sgn")) espressione[index] = "sgn";
        if (str.equals("inverf")) espressione[index] = "inverf";
        digitazioneNumero=0;
        immissioneParentesiAperta_G(v);



        str = "y=";
        for (int i = 0; i <= index; i++) {
            str = str + espressione[i];
        }
        screen.setText(str);
    }
    public void cancella_G(View v) {

        Button button = (Button) v;

        button.setOnLongClickListener(longClick);

        if (index >= 0) {
            if (digitazioneNumero == 1) {
                if (espressione[index].length() >= 1)
                    if (espressione[index] == "!") {
                        espressione[index] = "";
                        index--;
                        if (espressione[index] == ")") {
                            parentesiChiusa = 1;
                        }
                    } else
                    if(espressione[index] != "ans") espressione[index] = espressione[index].substring(0, espressione[index].length() - 1);
                    else espressione[index]="";
                else {
                    index--;
                    digitazioneNumero = 0;
                    if (index >= 0) {
                        if (espressione[index] == "(") {
                            parentesiAperta = 1;
                            parentesiChiusa = 0;
                        }
                    }

                }
                str = "y=";
                for (int i = 0; i <= index; i++) {
                    str = str + espressione[i];
                }
                screen.setText(str);
            }
        }
        if (index >= 0) {
            if (digitazioneNumero == 0 || parentesiAperta == 1 || parentesiChiusa == 1) {
                if (espressione[index] != "(") digitazioneNumero = 1;
                espressione[index] = "";
                index--;

                if (index >= 0) {
                    if (espressione[index] == "(") {
                        parentesiAperta = 1;
                        parentesiChiusa = 0;
                    } else if (espressione[index] == ")") {
                        parentesiAperta = 0;
                        parentesiChiusa = 1;
                    } else {
                        parentesiAperta = 0;
                        parentesiChiusa = 0;
                    }
                }
                if(index>=0){
                    if (espressione[index] == "sin" || espressione[index] == "asin" || espressione[index] == "sinh" || espressione[index] == "asinh" ||
                            espressione[index] == "cos" || espressione[index] == "acos" || espressione[index] == "cosh" || espressione[index] == "acosh" ||
                            espressione[index] == "tan" || espressione[index] == "atan" || espressione[index] == "tanh" || espressione[index] == "atanh" ||
                            espressione[index] == "erf" || espressione[index] == "inverf" || espressione[index] == "√" || espressione[index] == "abs" ||
                            espressione[index] == "log" || espressione[index] == "ln" ||  espressione[index] == "Abs" ||  espressione[index] == "sgn") {
                        espressione[index] = "";
                        index--;

                    }
                }
                str = "y=";
                for (int i = 0; i <= index; i++) {
                    str = str + espressione[i];
                }
                screen.setText(str);
            }
        }
        if(index>=0){
            if(espressione[index].length()==0){
                index--;
                digitazioneNumero=0;
            }
        }


        if (uguale == 1) {
            index = -1;
            digitazioneNumero = 0;
            parentesiAperta = 0;
            parentesiChiusa = 0;
            uguale = 0;
            str = "y=";
            screen.setText(str);
        }


    }
    public void grad_G(View view) {
        Button button = (Button) view;

        if (deg == 0) {
            button.setText("DEG");
            deg = 1;
        } else if(deg == 1) {
            button.setText("GRAD");
            deg = 2;
        } else{
            button.setText("RAD");
            deg=0;
        }




    }
    public void inv_G (View view) {
        Button button = (Button) view;

        Button button1 = (Button) findViewById(R.id.sin);
        Button button2 = (Button) findViewById(R.id.cos);
        Button button3 = (Button) findViewById(R.id.tan);
        Button button4 = (Button) findViewById(R.id.erf);
        Button button5 = (Button) findViewById(R.id.valoreAssoluto);
        if (inv == 0) {
            if (hyp == 0) {
                button1.setText("asin");
                button2.setText("acos");
                button3.setText("atan");
            } else {
                button1.setText("asinh");
                button2.setText("acosh");
                button3.setText("atanh");
            }
            button4.setText("inverf");
            button5.setText("sgn");
            inv = 1;
            button.setTextColor(getResources().getColor(R.color.orange, getTheme()));
        } else {
            if (hyp == 0) {
                button1.setText("sin");
                button2.setText("cos");
                button3.setText("tan");
            } else {
                button1.setText("sinh");
                button2.setText("cosh");
                button3.setText("tanh");
            }
            button4.setText("erf");
            button5.setText("abs");
            inv = 0;
            button.setTextColor(getResources().getColor(R.color.white, getTheme()));
        }


    }
    public void hyp_G(View view) {
        Button button = (Button) view;
        Button button1 = (Button) findViewById(R.id.sin);
        Button button2 = (Button) findViewById(R.id.cos);
        Button button3 = (Button) findViewById(R.id.tan);
        if (hyp == 0) {
            if (inv == 0) {
                button1.setText("sinh");
                button2.setText("cosh");
                button3.setText("tanh");
            } else {
                button1.setText("asinh");
                button2.setText("acosh");
                button3.setText("atanh");
            }
            hyp = 1;
            button.setTextColor(getResources().getColor(R.color.orange, getTheme()));
        } else {
            if (inv == 0) {
                button1.setText("sin");
                button2.setText("cos");
                button3.setText("tan");
            } else {
                button1.setText("asin");
                button2.setText("acos");
                button3.setText("atan");
            }
            hyp = 0;
            button.setTextColor(getResources().getColor(R.color.white, getTheme()));
        }


    }

    public void drawGraph (View view){
     //elimino operatori in eccesso (controllo sintassi)
        if(index>=0){
            while(espressione[index].equals("+") || espressione[index].equals("-") || espressione[index].equals("*") ||
                    espressione[index].equals("/") || espressione[index].equals("^")){
                index--;
            }}

// 1.Controllo parentesi
        for(int i=0; i<=index; i++) {


            if (espressione[i] == "(") {
                parentesiDaChiudere++;

            }
            if (espressione[i] == ")") {
                parentesiDaChiudere--;
            }
        }
        for(int i=0; i<parentesiDaChiudere;i++){
            index++;
            espressione[index]=")";
        } //le parentesi sono state chiuse

        //aggiorna schermo
        str = "y=";
        for (int i = 0; i <= index; i++) {
            str = str + espressione[i];
        }
        screen.setText(str);

        String[] tempEspressione = new String[length];

        //2.Sostituzione costanti
        tempEspressione=Funzioni.sostituisciCostanti(0.0,espressione,index+1);
        //3.Controllo sintassi
        if(parentesiDaChiudere>=0){
        try {

            Funzioni.creaValoriGrafico(10,tempEspressione,index+1,-10,10,deg);

            Intent intent = new Intent(this, GraphVisual.class);
            intent.putExtra(EXTRA_FUNCTION, tempEspressione);
            intent.putExtra(EXTRA_LENGTH, index);
            intent.putExtra(EXTRA_DEG, deg);
            startActivity(intent);
        } catch (NumberFormatException e) {
        str = "Syntax error";
        screen.setText(str);
    } catch (ArrayIndexOutOfBoundsException e) {
        str = "Syntax error";
        screen.setText(str);
    }}else{
            str = "Syntax error";
            screen.setText(str);
        }
        parentesiDaChiudere=0;
    }


}
