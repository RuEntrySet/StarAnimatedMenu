package ru.entryset.tools;

import org.bukkit.scheduler.BukkitRunnable;
import ru.entryset.main.Main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Runnable {

    private ScheduledExecutorService scheduled;
    private Integer start;
    private Integer delay;

    public Runnable(){
        scheduled = Executors.newScheduledThreadPool(1);
    }

    public void runTaskTimer(int start, int delay){
        setStart(start);
        setDelay(delay);
        new BukkitRunnable() {
            @Override
            public void run() {
                scheduled.scheduleWithFixedDelay(() -> {
                    task();
                }, 0, 50*getDelay(), TimeUnit.MILLISECONDS);
            }
        }.runTaskLater(Main.getInstance(), getStart());
        Main.runnables.add(this);
    }

    public void runTaskLater(int start){
        setStart(start);
        new BukkitRunnable() {
            @Override
            public void run() {
                task();
                Runnable.this.cancel();
            }
        }.runTaskLater(Main.getInstance(), getStart());
        Main.runnables.add(this);
    }

    public void cancel(){
        disable();
        Main.runnables.remove(this);
    }

    public void disable(){
        getScheduled().shutdownNow();
    }

    private ScheduledExecutorService getScheduled() {
        return scheduled;
    }

    private void setDelay(Integer delay) {
        this.delay = delay;
    }

    private void setStart(Integer start) {
        this.start = start;
    }

    private Integer getDelay() {
        return delay;
    }

    private Integer getStart() {
        return start;
    }

    public abstract void task();

}
