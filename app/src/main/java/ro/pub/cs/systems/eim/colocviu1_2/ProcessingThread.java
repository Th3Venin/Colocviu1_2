package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Process;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread{
    private Context context = null;
    private boolean isRunning = true;

    private final Random random = new Random();

    private int sum;

    public ProcessingThread(Context context, int sum) {
        this.context = context;

        this.sum = sum;
    }

    @Override
    public void run() {
        Log.d("[Processing Thread]", "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[Processing Thread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.practicaltest01.sum");
        intent.putExtra("ro.pub.cs.systems.eim.practicaltest01.broadcastreceiverextra",
                new Date(System.currentTimeMillis()) + " " + sum);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
