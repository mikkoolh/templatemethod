package org.template;

import java.util.Scanner;

public class KPSGame extends Game{

    private final String kivi="Stemu", paperi="Papru", sakset="Fiskarssit";
    private int kierrokset, käyttäjänValinta = 0, winUser = 0, winComp = 0;
    private boolean finished = false;
    private Scanner scan = new Scanner(System.in);
    @Override
    void initializeGame() {
        System.out.println("\n\n"+kivi+"-"+paperi+"-"+sakset+" alkaa.\nSäännöt:\n- "+ kivi +" voittaa "+ sakset +".\n- "+ sakset +" voittaa "+paperi+"n\n- "+paperi+" voittaa "+kivi+"n.");
        System.out.print("\nKuinka monta kierrosta haluat pelata: ");
        kierrokset = Integer.parseInt(scan.nextLine());
    }

    @Override
    void makePlay(int player) {
        int laskuri = 0;
        while(laskuri < kierrokset){
            usersTurn();
            try {
                evaluateWinner();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            laskuri++;
        }
        if(laskuri == kierrokset){
            finished = true;
        }
    }

    @Override
    boolean endOfGame() {
        return finished;
    }

    @Override
    void printWinner() {
        if (winComp > winUser){
            System.out.println("Pelit voitti tietokone tuloksella " + winComp + " - " + winUser);
        } else if (winComp == winUser){
            System.out.println("Pelit päättyivät tasapeliin tuloksella " + winComp + " - " + winUser);
        } else {
            System.out.println("Voitit pelit tuloksella " + winUser + " - " + winComp);
        }
    }

    private void usersTurn(){
        System.out.print("1 - "+ kivi + "\n2 - "+paperi + "\n3 - "+sakset +"\n\nAnna valintasi: ");
        do {
            käyttäjänValinta = Integer.parseInt(scan.nextLine());
            System.out.println("Valitsit: " + printChoise(käyttäjänValinta) );
        } while (käyttäjänValinta < 0 || käyttäjänValinta > 3);
    }

    private void evaluateWinner() throws InterruptedException {
        int tietokoneenValinta = raffleAndAnimate();
        System.out.print("Computer chose: " + printChoise(tietokoneenValinta));

        System.out.println();
        if (käyttäjänValinta == tietokoneenValinta){
            System.out.println("Tasapeli!\n");
        } else if (käyttäjänValinta == 1 && tietokoneenValinta == 3 || käyttäjänValinta == 2 && tietokoneenValinta == 1 || käyttäjänValinta == 3 && tietokoneenValinta == 2){
            System.out.println("Käyttäjä voitti! :)\n");
            winUser++;
        } else {
            System.out.println("Tietokone voitti! :(\n");
            winComp++;
        }
    }

    private int raffleAndAnimate() throws InterruptedException {
        int i = 1, rounds = 20, delay = 100, result = 0;

        while (i <= rounds){
            result = (int) (1 + Math.random() * 3);

            System.out.print("Arvotaan - "+ printChoise(result));
            Thread.sleep(delay);
            cleanRow(result);
            i++;
        }
        return result;
    }

    public String printChoise(int choise){
        if(choise == 1){
            return kivi;
        } else if(choise == 2){
            return paperi;
        } else if (choise == 3) {
            return sakset;
        }
        return "Virheellinen valinta!";
    }

    public void cleanRow(int valinta) {
        int length = 4;
        if(valinta == 1){
            length += kivi.length();
        } else if(valinta == 2){
            length += paperi.length();
        } else if (valinta == 3) {
            length += sakset.length();
        }

        for(int i = 0; i < length; i++){
            System.out.print("\r");
        }
    }
}
