package com.michal.booksylikeapp.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OtherConstants {

    TIME_SLOT_DURATION_IN_MIN(15), DAYS_TO_AUTO_SCHEDULE(14);
    final int number;
}

