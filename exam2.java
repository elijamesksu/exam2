/*
 * @author Eli james
 * cis200 exam 2
 * Load to memory ONLY the data from a file that complies with the user selections, that is, ONLY even numbers, odd numbers, or all the numbers
Once the data is loaded, sort the data (code is provided)
Once the data is sorted, writhe it to a different file, the name of the file is provided by the user
Display in the computer monitor the data before and after sorted //Extra 3 points
* I displayed the given data first before the user input on elimination
 *
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class E2_Students {
    static int[][] inputData = new int[5][5];
    static int eliminateNum = -1;

    public static void main(String[] args) {
        String inputFileName;
        String outputFileName;
        Scanner s = new Scanner(System.in);
        System.out.print("Enter File Name to read from: ");
        inputFileName = s.nextLine();
        System.out.print("Enter File Name for output data: ");
        outputFileName = s.nextLine();

        // Adding the provided numbers to the inputData array
        inputData = new int[][]{
                {1, 3, 16, 8, -2},
                {0, -3, 4, 9, 99},
                {7, 8, 0, 0, -4},
                {4, 5, 1, 2, -3},
                {-2, -1, 0, 1, 2}
        };

        displayGivenData(inputData); // Display given data

        do {
            System.out.println("Do you want to eliminate");
            System.out.println("1) Odd numbers ");
            System.out.println("2) Even numbers ");
            System.out.println("3) Keep all numbers ");
            eliminateNum = Integer.parseInt(s.nextLine());
        } while (eliminateNum != 1 && eliminateNum != 2 && eliminateNum != 3);

        displayEliminatedData(inputData, eliminateNum); // Display eliminated data

        try {
            sortData(inputData);
            writeDataToFile(outputFileName);
        } catch (IOException e) {
            System.out.println("An error occurred while reading/writing the file...");
        }
    }

    public static void readFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            Scanner inFile = new Scanner(file);
            int row = 0;
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();
                String[] numbers = line.split(" ");
                int col = 0;
                for (String number : numbers) {
                    if (!number.isBlank()) { // Skip blank entries
                        int num = Integer.parseInt(number);
                        if (validData(num, eliminateNum)) {
                            inputData[row][col] = num;
                            col++;
                        }
                    }
                }
                row++;
            }
            inFile.close();
        } else {
            System.out.println("File does not exist...");
        }
    }

    public static boolean validData(int data, int eliminateWhat) {
        if (eliminateWhat == 1) {
            // Eliminate odd numbers
            return data % 2 == 0;
        } else if (eliminateWhat == 2) {
            // Eliminate even numbers
            return data % 2 != 0;
        }
        // Keep all numbers
        return true;
    }

    public static void displayGivenData(int[][] data) {
        System.out.println("---------------- Given Data!!!! ----------------");
        System.out.println("This is the data table given to you by default..");
        System.out.println("---------------- Look Below!!!! ----------------");
        displayData(data, 3); // Displaying all numbers
    }

    public static void displayEliminatedData(int[][] data, int eliminateWhat) {
        System.out.println("---------------- Eliminated Data ----------------");
        System.out.println("The option you chose has been replaced with zeros");
        System.out.println("---------------- Look below!!!!! ----------------");
        displayData(data, eliminateWhat);
    }

    public static void displayData(int[][] data, int eliminateWhat) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (validData(data[i][j], eliminateWhat)) {
                    System.out.print(data[i][j]);
                } else {
                    System.out.print("0");
                }
                if (j < data[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public static void bubbleSort(int[] data) {
        int i, j;
        int temp;
        for (i = 0; i < data.length - 1; i++) {
            for (j = 0; j < data.length - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

    public static void sortData(int[][] unsortedData) {
        for (int i = 0; i < unsortedData.length; i++) {
            bubbleSort(unsortedData[i]);
        }
        System.out.println("---------------- Sorted Data!!!! ----------------");
        System.out.println("Each row has been sorted from smallest to largest");
        System.out.println("---------------- Look below!!!!! ----------------");
        displayData(unsortedData, eliminateNum);
        System.out.println("Your data has been officially eliminated and sorted..have a good day!");
    }

    public static void writeDataToFile(String fName) throws IOException {
        PrintWriter outfile = new PrintWriter(fName);
        for (int i = 0; i < inputData.length; i++) {
            for (int j = 0; j < inputData[i].length; j++) {
                outfile.print(inputData[i][j]);
                if (j < inputData[i].length - 1) {
                    outfile.print(" ");
                }
            }
            outfile.println();
        }
        outfile.close();
    }
}
