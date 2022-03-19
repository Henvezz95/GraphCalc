package com.example.henvezz.calcolatrice;



public class Funzioni {

    public static String[] convertToRPNlv2 (String[] espressione,int length){
        int index = 0; //indirizzo dell'array sorgente
        int resultIndex=0; //indirizzo del vettore risultato
        int stackCounter=0; //stack counter
        int stop =0;
        String[] stack = new String[length];
        stack[0]="0"; //stack inizializzato
        String[] result = new String[length];

        for(index=0;index<length;index++){     //elemento per elemento l'array sorgente viene valuatato
            if (espressione[index] != "+" &&         //1)nel caso la stringa sia un numero oppure !
                    espressione[index] != "-"  &&
                    espressione[index] !="*" &&
                    espressione[index] !="/" &&
                    espressione[index] !="(" &&
                    espressione[index] !=")" &&
                    espressione[index] !="^" &&
                    espressione[index] !="sin" && espressione[index] !="cos" && espressione[index] !="tan" &&
                    espressione[index] !="asin" && espressione[index] !="acos" && espressione[index] !="atan" &&
                    espressione[index] !="sinh" && espressione[index] !="cosh" && espressione[index] !="tanh" &&
                    espressione[index] !="asinh" && espressione[index] !="acosh" && espressione[index] !="atanh" &&
                    espressione[index] !="√" && espressione[index] !="erf" && espressione[index] !="log" &&
                    espressione[index] !="ln" && espressione[index] !="Si" && espressione[index] !="sinc"&&
                    espressione[index] !="inverf" && espressione[index] !="Abs" && espressione[index] !="sgn"

                    ){
                result[resultIndex]=espressione[index]; //1)essa viene direttamente copiata nel risultato
                resultIndex++;                          //1)l'indicatore del risultato viene incrementato di conseguenza
            }
            else{                                      //2)se invece stiamo analizzando un'operando o una parentesi
                if(espressione[index] == "+" || espressione[index] == "-"){ //2.1)l'opernado è una più o una meno
                    while(stop==0){                                             //2.1)verrà posizionata nel punto giusto dello stack attraverso un ciclo while, la variabile stop indica quando il ciclo si deve concludere
                        if(stack[stackCounter]== "+" || stack[stackCounter]== "-" || stack[stackCounter]=="0"){ //2.1.1) se lo stack presenta una più una meno o è vuoto (quindi c'è un operando di grado uguale o inferiore)
                            if(stack[stackCounter]!="0"){ //2.1.1.1) se non è vuoto abbiamo una più o una meno
                                result[resultIndex]=stack[stackCounter]; //2.1.1.1) quindi il risultato viene incrementato con l'ultimo elemento dello stack
                                resultIndex++; //e quindi resultIndex viene aumentato
                            }

                            stop=1; //2.1.1) in entambi i casi lo stack dovrà contenere l'operando appena studiato, più o meno che sia...questo alla fine del ciclo while
                        }
                        else if(stack[stackCounter] == "*" || stack[stackCounter] == "/" || stack[stackCounter] == "^"){ //2.1.2) se invece l'operando è una * o una / oppure ^ lo stack deve essere accorciato, almeno che l'ultimo elemento dello stack non sia una parentesi aperta
                            result[resultIndex]=stack[stackCounter]; //2.1.2.1) il primo operando dello stack viene copiato nel risultato
                            resultIndex++;//2.1.2.1) l'indirizzo del risultato viene incrementato
                            if(stackCounter>0) stackCounter--; //2.1.2.1)l'operando viene tolto dalla pila dello stack semplicemente abbassando lo stackCounter, verrà poi eventualmente sovrascritto
                            else stop=1; //se invece lo stackCounter è già a zero allora lo stack è vuoto e il ciclo và interrotto quindi stop=1
                        }
                        else if(stack[stackCounter] == "("){ //2.1.2.2 in caso di parentesi aperta
                            stackCounter++;
                            stop=1; //2.1.2.2 l'operando è semplicemente appoggiato sopra lo stack a fine ciclo
                        }
                    }
                    stack[stackCounter]=espressione[index]; //2.1)finito il ciclo l'operando che è stato utilizzato per la valutazione viene appoggiato sopra lo stack
                    stop=0;
                }
                else if (espressione[index] == "*" || espressione[index] == "/"){ //2.2 se invece siamo davanti a una * o una / da valutare
                    while(stop==0){
                        if(stack[stackCounter]== "*" || stack[stackCounter]== "/"){ //2.2.1) se in cima allo stack abbiamo * o / abbiamo lo stesso livello di priorità


                            result[resultIndex]=stack[stackCounter]; //2.2.1) l'operando in cima allo stack viene spostato nel risultato
                            resultIndex++;
                            stack[stackCounter]=espressione[index];
                            stop=1;//2.2.1)in cima allo stack viene posto il nuvo opernado di pari priorità

                        }
                        else if(stack[stackCounter]== "+" || stack[stackCounter]== "-" || stack[stackCounter] == "(" || stack[stackCounter] == "0"){
                            if(stack[stackCounter]!="0") stackCounter++;
                            stack[stackCounter]=espressione[index];
                            stop=1;//2.2.2) se abbiamo + - o ( l'operando è appoggiato in cima allo stack
                        }
                        else if(stack[stackCounter] == "^"){//2.2.3) se invece abbiamo un elevamento a potenza
                            result[resultIndex]=stack[stackCounter];
                            resultIndex++;
                            if(stackCounter>0) stackCounter--;
                            else stop=1;

                        }

                    }
                    stack[stackCounter]=espressione[index];
                    stop=0;
                }
                else if (espressione[index]== "("){ //2.3) se invece dobbiamo posizionare una parentesi aperta
                    if(stack[stackCounter]!="0") { //2.3.1) se lo stack non è vuoto essa è appoggiata in cima incrementando lo stackCounter
                        stackCounter++;
                        stack[stackCounter]=espressione[index];
                    }
                    else {
                        stack[stackCounter]=espressione[index]; //2.3.2) se la posizione attualmente indicata dallo stack counter è vuota la casella è riempita con la parentesi aperta

                    }
                }
                else if (espressione[index] == "^" ||
                        espressione[index] =="sin" || espressione[index] =="cos" || espressione[index] =="tan" ||
                        espressione[index] =="asin" || espressione[index] =="acos" || espressione[index] =="atan" ||
                        espressione[index] =="sinh" || espressione[index] =="cosh" || espressione[index] =="tanh" ||
                        espressione[index] =="asinh" || espressione[index] =="acosh" || espressione[index] =="atanh" ||
                        espressione[index] =="√" || espressione[index] =="erf" || espressione[index] =="log" ||
                        espressione[index] =="ln" || espressione[index] =="Si" || espressione[index] =="sinc"||
                        espressione[index] =="inverf" || espressione[index] =="Abs" || espressione[index] =="sgn"){ //2.4)potenza o operatore unario
                    if(stack[stackCounter]!="0") { //2.4.1) se lo stack non è vuoto ^ è appoggiato in cima incrementando lo stackCounter
                        stackCounter++;
                        stack[stackCounter]=espressione[index];
                    }
                    else {
                        stack[stackCounter]=espressione[index]; //2.3.2) se la posizione attualmente indicata dallo stack counter è vuota la casella è riempita con la parentesi aperta

                    }
                }

                else {//parentesi chiusa
                    while(stack[stackCounter]!= "("){ //2.5) in caso di parentesi chiusa fino a quando non incontro una parentesi aperta sullo stack
                        if( stack[stackCounter]!="0"){ //2.5.1) se la casella attuale non è vuota
                            result[resultIndex]=stack[stackCounter];//2.4.1) l'operando è posto nel risultato
                            resultIndex++;
                        }
                        else break;
                        stack[stackCounter]="0"; //2.5)in ogni caso ora la casella è vuota
                        if(stackCounter!=0)stackCounter--;//2.5)se possibile abbassiamo lo stack counter di 1
                    }
                    stack[stackCounter]="0"; //2.5) quando si conclude il while significa che puntiamo una casella con ( ... quindi la cancelliamo
                    if(stackCounter!=0)stackCounter--; //2.5) e decrementiamo lo stack counter
                    if(stack[stackCounter] =="sin" || stack[stackCounter] =="cos" || stack[stackCounter] =="tan" ||
                            stack[stackCounter] =="asin" || stack[stackCounter] =="acos" || stack[stackCounter] =="atan" ||
                            stack[stackCounter] =="sinh" || stack[stackCounter] =="cosh" || stack[stackCounter] =="tanh" ||
                            stack[stackCounter] =="asinh" || stack[stackCounter] =="acosh" || stack[stackCounter] =="atanh" ||
                            stack[stackCounter] =="√" || stack[stackCounter] =="erf" || stack[stackCounter] =="log" ||
                            stack[stackCounter] =="ln" || stack[stackCounter] =="Si" || stack[stackCounter] =="sinc"||
                            stack[stackCounter] =="inverf" || stack[stackCounter] =="Abs" || stack[stackCounter] =="sgn"){

                        result[resultIndex]=stack[stackCounter];
                        resultIndex++;
                        stack[stackCounter]="0";
                        if(stackCounter>0) stackCounter--;
                    }//Alla chiusura della parentesi viene scaricato dallo stack anche un eventuale operando unario


                }
            }
        }
        while(stackCounter>=0){ //finito di valutare l'array dobbiamo svuotare lo stack
            if(stack[stackCounter]!="0") result[resultIndex++]=stack[stackCounter];
            stackCounter--;
        }
        return result;
    }

    public static double resolveExpressionlv2(String[] espressione, int length, int deg){

        if (espressione.length == 1) return Double.parseDouble(espressione[0]);

        double result=0;
        double c;
        //Otteniamo la conversione in gradi se necessario
        if(deg==0){
            c=1;
        }else if(deg==1){
            c=Math.PI/180;
        }else{
            c=Math.PI/200;
        }


        espressione=Funzioni.convertToRPNlv2(espressione, length); // la stringa viene convertita


   //risolve problema parentesi inutili in caso di unico numero
       if(espressione[0].length()==0) result=Double.parseDouble(espressione[1]);
        else result=Double.parseDouble(espressione[0]);

        for(int i=0;i<length;i++){ //scansionata elemento per elemento cercndo il prossimo operatore
            if(espressione[i]=="+"){// caso somma
                result=Double.parseDouble(espressione[i-2])+Double.parseDouble(espressione[i-1]);
                espressione[i]=result+"";
                for(int a=i-3;a>=0;a--){
                    espressione[a+2]=espressione[a]; //gli elementi vengono spostati a destra
                }

            }
            if(espressione[i]=="-"){ // caso sottrazione
                result=Double.parseDouble(espressione[i-2])-Double.parseDouble(espressione[i-1]);
                espressione[i]=result+"";
                for(int a=i-3;a>=0;a--){
                    espressione[a+2]=espressione[a];
                }
            }
            if(espressione[i]=="*"){ //caso prodotto
                result=Double.parseDouble(espressione[i-2])*Double.parseDouble(espressione[i-1]);
                espressione[i]=result+"";
                for(int a=i-3;a>=0;a--){
                    espressione[a+2]=espressione[a];
                }
            }
            if(espressione[i]=="/"){ // caso divisione
                result=Double.parseDouble(espressione[i-2])/Double.parseDouble(espressione[i-1]);
                espressione[i]=result+"";
                for(int a=i-3;a>=0;a--){
                    espressione[a+2]=espressione[a];
                }
            }
            if(espressione[i]=="^"){ //caso potenza
                result=Math.pow(Double.parseDouble(espressione[i-2]),Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-3;a>=0;a--){
                    espressione[a+2]=espressione[a];
                }
            }
            if(espressione[i]=="!"){ //caso fatoriale
                result=GammaFunction.fattoriale(Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="sin"){ //caso seno
                result=Math.sin(Double.parseDouble(espressione[i-1])*c);
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="cos"){ //caso coseno
                result=Math.cos(Double.parseDouble(espressione[i-1])*c);
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1] = espressione[a];
                }
            }
            if(espressione[i]=="tan"){ //caso tangente
                result=Math.tan(Double.parseDouble(espressione[i-1])*c);
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="asin"){ //caso arcoseno
                result=Math.asin(Double.parseDouble(espressione[i-1]))/c;
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="acos"){ //caso arcocoseno
                result=Math.acos(Double.parseDouble(espressione[i-1]))/c;
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="atan"){ //caso arcotangente
                result=Math.atan(Double.parseDouble(espressione[i-1]))/c;
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="sinh"){ //caso seno iperbolico
                result=Math.sinh(Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="cosh"){ //caso coseno iperbolico
                result=Math.cosh(Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="tanh"){ //caso tangente iperbolica
                result=Math.tanh(Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }

            if(espressione[i]=="asinh"){ //caso arcoseno iperbolico
                Double b= Double.parseDouble(espressione[i-1]);
                result=Math.log(b + Math.sqrt(b*b + 1.0));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="acosh"){ //caso arcocoseno iperbolico
                Double b= Double.parseDouble(espressione[i-1]);
                result=Math.log(b + Math.sqrt(b*b - 1.0));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="atanh"){ //caso arcotangente iperbolica
                Double b= Double.parseDouble(espressione[i-1]);
                result=0.5*Math.log( (b + 1.0) / (b - 1.0) );
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="erf"){ //caso erf
                result=ErrorFunction.erf((Double.parseDouble(espressione[i-1])));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="inverf"){ //caso erf
                result=ErrorFunction.invErf((Double.parseDouble(espressione[i-1])));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="log"){ //caso logaritmo
                result=Math.log10(Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }

            if(espressione[i]=="ln"){ //caso logaritmo
                result=Math.log(Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="Abs"){ //caso valore assoluto
                result=Math.abs(Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="sgn"){ //caso valore assoluto
                if(Double.parseDouble(espressione[i-1])>0) result=1;
                else if(Double.parseDouble(espressione[i-1])<0) result=-1;
                else if(Double.parseDouble(espressione[i-1])==0) result=0;
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }
            if(espressione[i]=="√"){ //caso radice
                result=Math.sqrt(Double.parseDouble(espressione[i-1]));
                espressione[i]=result+"";
                for(int a=i-2;a>=0;a--){
                    espressione[a+1]=espressione[a];
                }
            }

        }
        return result;
    }

    public static String[] sostituisciX (double valueOfX, String[] espressione,int length){

        String[] newEspressione = new String[length];
        for(int i=0;i<length;i++){
            newEspressione[i]=espressione[i];
        }

        for(int i=0; i<length; i++){


            if(newEspressione[i]=="x"){
                newEspressione[i]=valueOfX+"";
            }
        }
        return newEspressione;
    }

    public static String[] sostituisciCostanti (double ans, String[] espressione,int length){

        String[] newEspressione = new String[length];

        for(int i=0;i<length;i++){
            newEspressione[i]=espressione[i];
        }

        for(int i=0; i<length; i++){


            if(newEspressione[i]=="π"){
                 newEspressione[i] = Math.PI + "";
            }

            if(newEspressione[i]=="e"){
                newEspressione[i] = Math.E + "";
            }
            if(newEspressione[i]=="ans"){
                newEspressione[i] = ans + "";
            }
        }
        return newEspressione;


    }

    public static double[] creaValoriGrafico (int numberOfSamples,String[] espressione,int length,double minX,double maxX,int deg){
        double step=(maxX-minX)/(numberOfSamples-1);
        double[] valori = new double[numberOfSamples];
        String[] espressioneNumerica;

        for(int i=0;i<numberOfSamples;i++){
            espressioneNumerica=sostituisciX(minX+step*i,espressione,length);
            valori[i]=resolveExpressionlv2(espressioneNumerica,length,deg);
        }
        return valori;
    }

}
