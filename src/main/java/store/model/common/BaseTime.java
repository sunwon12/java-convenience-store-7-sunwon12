package store.model.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BaseTime(LocalDate startDate, LocalDate endDate) {

    public boolean isBetweenDate(LocalDateTime input) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return !input.isBefore(startDateTime) && !input.isAfter(endDateTime);
    }
}
