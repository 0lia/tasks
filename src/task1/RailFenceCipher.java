package task1;

import java.util.*;

public class RailFenceCipher {

    static String encode(String str, int numberOfRails){
        if(str.length() <= 2 || numberOfRails <= 1)
            return str;

        StringBuilder result = new StringBuilder();
        List<List<Character>> rails = new ArrayList<>();
        for (int i = 0; i < numberOfRails; i++) {
            rails.add(new ArrayList<>());
        }
        int i = 0;
        while (i < str.length()) {
            int j = 0;
            //diagonally and down
            while (j < numberOfRails && i < str.length()) {
                rails.get(j).add(str.charAt(i));
                j++; i++;
            }
            j -= 2;
            //diagonally and up
            while (j > 0 && i < str.length()) {
                rails.get(j).add(str.charAt(i));
                j--; i++;
            }
        }
        //join rails
        for (List<Character> rail : rails) {
            for(Character ch : rail){
                result.append(ch);
            }
        }
        return result.toString();
    }

    static String decode(String str, int numberOfRails){
        if(str.length() <= 2 || numberOfRails <= 1)
            return str;

        StringBuilder result = new StringBuilder();
        List<Queue<Character>> rails = new ArrayList<>();

        for (int i = 0; i < numberOfRails; i++) {
            rails.add(new ArrayDeque<>());
        }

        int [] nmbOfCharsInRails = new int[numberOfRails]; //number of chars in each row
        int offset = (numberOfRails - 1) * 2;
        int nmbFull = (str.length() - 1) / offset; //number of full zigzags
        int nmbNotFull = (str.length() - 1) % offset; //number of chars left

        nmbOfCharsInRails[0] = nmbFull + 1;  //first rail
        for (int i = 1; i < numberOfRails - 1; i++) {
            nmbOfCharsInRails[i] = nmbFull * 2;
        }
        nmbOfCharsInRails[numberOfRails - 1] = nmbFull; //last rail

        int i = 0, j;
        for (j = 1; j < numberOfRails && i < nmbNotFull; j++, i++) {
            nmbOfCharsInRails[j]++;
        }
        for(j -= 2; j < numberOfRails && i < nmbNotFull; j--, i++){
            nmbOfCharsInRails[j]++;
        }

        //dividing string into rows
        int pos = 0, l;
        for (int k = 0; k < numberOfRails; k++) {
            for (l = pos; l < nmbOfCharsInRails[k] + pos; l++) {
                rails.get(k).add(str.charAt(l));
            }
            pos = l;
        }

        //getting chars from each row
        while (result.length() < str.length()){
            for (int k = 0; k < numberOfRails && result.length() < str.length(); k++) {
                result.append(rails.get(k).poll());
            }
            for (int k = numberOfRails - 2; k >= 1 && result.length() < str.length(); k--) {
                result.append(rails.get(k).poll());
            }
        }

        return result.toString();
    }


    public static void main(String[] args) {
        String toEncode = "WEAREDISCOVEREDFLEEATONCE";
        String encoded = encode(toEncode, 3);
        System.out.println(encoded);
        System.out.println(decode(encoded, 3));
    }
}