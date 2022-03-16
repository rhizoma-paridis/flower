package org.x.flower.domain.flow.model.task;

import org.x.flower.domain.flow.model.Job;

public class Task implements Job {
    @Override
    public boolean run() {
        return false;
    }
}
