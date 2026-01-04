package org.geometryService.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.geometryService.repository.history.HistoryServiceClient;

import org.geometryService.services.plot.CheckerFunction;


@ApplicationScoped
public class CheckerService {
    @Inject
    private CheckerFunction checker;
    
    @Inject
    private HistoryServiceClient historyServiceClient;

    public boolean check(
            final int userId,
            final int groupId,
            final float X,
            final float Y,
            final float R
    ) {
        final boolean isHitted = checker.check(X, Y, R);
        historyServiceClient.savePoint(X, Y, R, isHitted, userId, groupId);
        return true;
    }
}