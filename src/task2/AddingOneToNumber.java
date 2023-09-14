package task2;

import java.util.Scanner;

public class AddingOneToNumber {

    static int addOne(int number){
        if(number == 0)
            return 1;
        int[] digits = new int[10];
        int i = 0, result;
        //dividing into digits
        while (number > 0){
            digits[i] = number % 10 + 1;
            number = number/10;
            i++;
        }
        result = digits[i - 1];
        //conversion of digits into a number
        for (int j = i - 2; j >= 0; j--) {
            if(digits[j] == 10)
                result *= 10;
            result = (result * 10) + digits[j];
        }
        return result;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number: ");
        int nmb = scanner.nextInt();
        System.out.println("Result: " + addOne(nmb));
    }
}
