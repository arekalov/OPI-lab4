package com.arekalov.jsfgraph.mbeans;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collection;

import com.arekalov.jsfgraph.db.ResultDAO;
import com.arekalov.jsfgraph.entity.ResultEntity;

public class PointsCounter extends NotificationBroadcasterSupport implements PointsCounterMBean {
    private AtomicInteger totalPoints = new AtomicInteger(0);
    private AtomicInteger missedPoints = new AtomicInteger(0);
    private long sequenceNumber = 1;
    private ResultDAO resultDAO;

    public PointsCounter(ResultDAO resultDAO) {
        this.resultDAO = resultDAO;
        initializeCounts();
    }

    private void initializeCounts() {
        totalPoints.set(0);
        missedPoints.set(0);
        Collection<ResultEntity> results = resultDAO.getAllResults();
        for (ResultEntity result : results) {
            totalPoints.incrementAndGet();
            if (!result.isResult()) {
                missedPoints.incrementAndGet();
            }
        }
    }

    public void resetAndInitializeCounts() {
        totalPoints.set(0);
        missedPoints.set(0);
        initializeCounts();
    }

    @Override
    public int getTotalPoints() {
        return totalPoints.get();
    }

    @Override
    public int getMissedPoints() {
        return missedPoints.get();
    }

    @Override
    public void incrementTotalPoints() {
        totalPoints.incrementAndGet();
    }

    @Override
    public void incrementMissedPoints() {
        missedPoints.incrementAndGet();
    }

    public void notifyPointOutOfArea(double x, double y, double r) {
        Notification n = new Notification(
            "PointOutOfArea",
            this,
            sequenceNumber++,
            System.currentTimeMillis(),
            "Point out of area after radius change: (" + x + ", " + y + ", r=" + r + ")"
        );
        sendNotification(n);
    }

    @Override
    public void resetConsecutiveMisses() {
        // Метод больше не используется, оставлен для совместимости с интерфейсом
    }
}