package org.x.flower.domain.flow.task;

import org.x.flower.domain.flow.Job;

public class Task implements Job {
    @Override
    public boolean run() {
        return false;
    }
}
