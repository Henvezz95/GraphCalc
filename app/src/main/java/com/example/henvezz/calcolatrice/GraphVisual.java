package com.example.henvezz.calcolatrice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphVisual extends AppCompatActivity {

    int samples = 1300;
    double minX = -8.1;
    double maxX = 8.1;
    String[] espressione;
    int deg, length;
    double[] funzione;
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_visual);

        //1.Viene catturato l'intent e inizializzate le variabili necessarie
        Intent intent = getIntent();
        espressione = intent.getStringArrayExtra(GraphMain.EXTRA_FUNCTION);
        deg = intent.getIntExtra(GraphMain.EXTRA_DEG,0);
        length = intent.getIntExtra(GraphMain.EXTRA_LENGTH,0)+1;
        String[] tempEspressione = rendiLaFunzioneLeggibile(espressione,length);

        //2. Vengono generati i valori della funzione
        funzione = Funzioni.creaValoriGrafico(samples, tempEspressione, length, minX, maxX, deg);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        DataPoint[] dati = new DataPoint[samples];

        //3.Viene generata la serie che verrà disegnata dal grafico
        for (int i = 0; i < samples; i++) {

        //3.1 Valori problematici vengono scartati
            if(   Double.isInfinite(funzione[i])==false &&
                    Double.isNaN(funzione[i])==false &&
                    funzione[i]<=1000000000 &&
                    funzione[i]>=-1000000000){
                series.appendData(new DataPoint(minX+i*(maxX-minX)/(samples-1), funzione[i]), true, samples);
            }
        }
      //4. Vengono settate le impostazioni del grafico
        series.setThickness(4);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(minX);
        graph.getViewport().setMaxX(maxX);
        graph.getViewport().setMaxY(series.getHighestValueY());
        graph.getViewport().setMinY(series.getLowestValueY());
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);
        graph.addSeries(series);




    }


    public String[] rendiLaFunzioneLeggibile (String[] espressione, int length){

        for(int i=0;i<length;i++){
            if (espressione[i].equals("+")) espressione[i]="+";
            if (espressione[i].equals("-")) espressione[i]="-";
            if (espressione[i].equals("*")) espressione[i]="*";
            if (espressione[i].equals("/")) espressione[i]="/";
            if (espressione[i].equals("^")) espressione[i]="^";
            if (espressione[i].equals("!")) espressione[i]="!";
            if (espressione[i].equals("(")) espressione[i]="(";
            if (espressione[i].equals(")")) espressione[i]=")";
            if (espressione[i].equals("sin")) espressione[i]="sin";
            if (espressione[i].equals("cos")) espressione[i]="cos";
            if (espressione[i].equals("tan")) espressione[i]="tan";
            if (espressione[i].equals("asin")) espressione[i]="asin";
            if (espressione[i].equals("acos")) espressione[i]="acos";
            if (espressione[i].equals("atan")) espressione[i]="atan";
            if (espressione[i].equals("sinh")) espressione[i]="sinh";
            if (espressione[i].equals("cosh")) espressione[i]="cosh";
            if (espressione[i].equals("tanh")) espressione[i]="tanh";
            if (espressione[i].equals("asinh")) espressione[i]="asinh";
            if (espressione[i].equals("acosh")) espressione[i]="acosh";
            if (espressione[i].equals("atanh")) espressione[i]="atanh";
            if (espressione[i].equals("log")) espressione[i]="log";
            if (espressione[i].equals("ln")) espressione[i]="ln";
            if (espressione[i].equals("Abs")) espressione[i]="Abs";
            if (espressione[i].equals("sgn")) espressione[i]="sgn";
            if (espressione[i].equals("erf")) espressione[i]="erf";
            if (espressione[i].equals("inverf")) espressione[i]="inverf";
            if (espressione[i].equals("√")) espressione[i]="√";
            if (espressione[i].equals("e")) espressione[i]="e";
            if (espressione[i].equals("π")) espressione[i]="π";
            if (espressione[i].equals("x")) espressione[i]="x";
        }
        return espressione;
    }
    public void setGraphBounds (View v){

        GraphView graph = (GraphView) findViewById(R.id.graph);
        EditText editText1 = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText_2);

        //1.controllo che gli estremi siano stati inseriti
        if (editText1.getText().length()==0 || editText1.getText().toString().equals("-") ){
            Toast.makeText(this, "L'estremo inferiore non è stato inserito", Toast.LENGTH_LONG).show();
            return;
        }
        if (editText2.getText().length()==0 || editText2.getText().toString().equals("-") ){
            Toast.makeText(this, "L'estremo superiore non è stato inserito", Toast.LENGTH_LONG).show();
            return;
        }
        //2.Massimo e minimo vengono inizializzati
        minX = Double.parseDouble(editText1.getText().toString() );
        maxX=Double.parseDouble(editText2.getText().toString());

        //3. Se il massimo è minore del minimo vengono scambiati
        if(minX>maxX){
            double temp = minX;
            minX = maxX;
            maxX = temp;
        }
        //4. Vengono creati i valori del grafico
        funzione = Funzioni.creaValoriGrafico(samples, espressione, length, minX, maxX, deg);
        graph.getViewport().setMinX(minX);
        graph.getViewport().setMaxX(maxX);

        //5. Viene creata la serie che contiene i valori
        series = new LineGraphSeries<DataPoint>();

        for (int i = 0; i < samples; i++) {

            //5.1 i valori che danno dei problemi vengono scartati
            if(Double.isInfinite(funzione[i])==false &&
                    Double.isNaN(funzione[i])==false &&
                    funzione[i]<=1000000000 &&
                    funzione[i]>=-1000000000) series.appendData(new DataPoint(minX+i*(maxX-minX)/(samples-1), funzione[i]), true, samples);
        }

        //6. La serie viene inserita nel grafico
        graph.removeAllSeries();
        graph.getViewport().setMaxY(series.getHighestValueY());
        graph.getViewport().setMinY(series.getLowestValueY());
        graph.addSeries(series);



    }
}