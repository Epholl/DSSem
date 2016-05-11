package sk.epholl.dissim.sem3.inputanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class InputParser {

    public static final DateFormat LOG_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.CHINA);

    public static void main(String[] arguments) throws IOException, ParseException {
        BufferedReader input = new BufferedReader(new FileReader("src/vstupy.txt"));
        PrintStream streamAmountA = new PrintStream("outputAmountA.txt");
        PrintStream streamAmountB = new PrintStream("outputAmountB.txt");
        PrintStream streamAmountC = new PrintStream("outputAmountC.txt");
        PrintStream streamTimeA = new PrintStream("outputTimeA.txt");
        PrintStream streamTimeB = new PrintStream("outputTimeB.txt");
        PrintStream streamTimeC = new PrintStream("outputTimeC.txt");
        boolean firstLine = true;
        String line;

        long sumA, sumB, sumC, countA, countB, countC;
        sumA = sumB = sumC = countA = countB = countC = 0l;
        long lastTimeA, lastTimeB, lastTimeC;
        lastTimeA = lastTimeB = lastTimeC = -1;

        while ((line = input.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }
            String[] tokens = line.split(";");
            long date = LOG_DATE_TIME_FORMAT.parse(tokens[0]).getTime();

            TimeZone utc = TimeZone.getTimeZone("UTC");
            Calendar datee = Calendar.getInstance(utc);
            DateFormat format = DateFormat.getDateTimeInstance();
            format.setTimeZone(utc);

            String[] dateAndTime = tokens[0].split(" ");
            String[] sDate = dateAndTime[0].split("\\.");
            String[] sTime = dateAndTime[1].split(":");

            datee.set(Integer.parseInt(sDate[2]), Integer.parseInt(sDate[1]) + 1, Integer.parseInt(sDate[0]),
                    Integer.parseInt(sTime[0]), Integer.parseInt(sTime[1]), 00);

            date = datee.getTime().getTime();

            switch (tokens[1]) {
                case "A":
                    sumA += Long.parseLong(tokens[2]);
                    countA++;
                    if (lastTimeA != -1) {
                        streamTimeA.println((date - lastTimeA) / 60000);
                    }
                    lastTimeA = date;
                    streamAmountA.println(tokens[2]);
                    break;
                case "B":
                    sumB += Long.parseLong(tokens[2]);
                    countB++;
                    if (lastTimeB != -1) {
                        streamTimeB.println((date - lastTimeB) / 60000);
                    }
                    lastTimeB = date;
                    streamAmountB.println(tokens[2]);
                    break;
                case "C":
                    sumC += Long.parseLong(tokens[2]);
                    countC++;
                    if (lastTimeC != -1) {
                        streamTimeC.println((date - lastTimeC) / 60000);
                    }
                    lastTimeC = date;
                    streamAmountC.println(tokens[2]);
                    break;
            }
        }

        printInfo(sumA, countA);
        printInfo(sumB, countB);
        printInfo(sumC, countC);
    }

    public static void printInfo(long sum, long count) {
        System.out.println("Total sum: " + sum + ", number of lines: " + count);
        System.out.println("Average: " + String.format("%.3f", ((double) sum) / count));
    }
}