package com.hhdl;

import java.util.Timer;
import java.util.TimerTask;

public class TimerMain {
    static int elapsed = 0;
    static int jg = 100;

    static class MyTask extends TimerTask
    {
        @Override
        public void run()
        {
            elapsed += 200;
            //这里做你要做的事
            System.out.println("time elapsed " + elapsed);
            System.out.println("time elapsed " + System.currentTimeMillis());
            if (elapsed >= 2000) // 超过2秒，结束
                cancel();
        }
    }

    public static void main(String[] args) throws Exception
    {
        final Timer timer = new Timer();
        timer.schedule(new MyTask(), 0, jg);
    }
}
