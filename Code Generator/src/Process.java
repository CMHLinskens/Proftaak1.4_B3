public class Process extends Thread {

    private DataController DC;
    private int codeSize = 6;
    private boolean codeSwitcher = false;

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
        publishLoop();
    }

    public void setUpThread(DataController dc) {
        DC = dc;
    }

    public void publishLoop() {
        while (true) {
            // Give the DC time to make a connection with the mqtt broker
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("process is running");
            Thread generateThread = new Thread(() -> {
                while(true) {
                    if(DC.isGeneratingRandomly())
                    for(Code code : DC.getCodes()){
                        code.setCode(generate(codeSize));
                        codeSwitcher = !codeSwitcher;
                        DC.saveCodes();
                    }
                    try {
                        Thread.sleep(20 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            generateThread.start();
            while(true){
                DC.publishCodes();
                sleep();
            }
        }
    }

    public void sleep() {
        try {
            sleep(5 * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean codeSwitcher() {
        return codeSwitcher;
    }
}
