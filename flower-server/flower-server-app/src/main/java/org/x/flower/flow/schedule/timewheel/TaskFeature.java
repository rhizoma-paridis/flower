package org.x.flower.flow.schedule.timewheel;

public interface TaskFeature {

    Task getTask();

    boolean cancel();

    boolean isCanceled();

}
