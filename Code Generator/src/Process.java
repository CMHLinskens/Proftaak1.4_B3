import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Process extends Thread {

    private DataController DC;
    private LocalTime dateTime;
    private int codeSize = 6;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generate(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }


    @Override
    public void run() {
        String str = "06:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        dateTime = LocalTime.parse(str, formatter);
        System.out.println("hello");
        System.out.println("process is running");
        Loop();
    }

    public void setUpThread(DataController dc) {
        DC = dc;
    }


    public void Loop() {
        while (true) {
//            while (DC.isGeneratingRandomly()) {
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
//                LocalDateTime RegisteredDateTime = LocalDateTime.parse(DC.getTime(), dtf);
//                LocalDateTime today = LocalDateTime.now();
//                if (today.toLocalDate().isAfter(RegisteredDateTime.toLocalDate()) || today.toLocalDate().equals(RegisteredDateTime.toLocalDate())) {
//                    if (RegisteredDateTime.toLocalTime().isBefore(dateTime) && (today.toLocalTime().isAfter(dateTime) || today.toLocalTime().equals(dateTime))) {
//                        for (Code code : DC.getCodes()) {
//                            code.setCode(generate(CodeSize));
//                            DC.saveCodes();
//                        }
//
//                    } else {
//                        sleep();
//                    }
//                } else {
//                    sleep();
//                }
//            }
            while(DC.isGeneratingRandomly()){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(Code code : DC.getCodes()){
                    code.setCode(generate(codeSize));
                    DC.saveCodes();
                }
                sleep();
            }
        }
    }

    public void sleep() {
        try {
            System.out.println("going to sleep");
            sleep(60 * 1000);

        } catch (Exception e) {
            System.out.println("something went wrong");
            e.printStackTrace();
        }

    }
}
