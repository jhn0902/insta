package com.spring.insta.util;

import com.spring.insta.model.Review;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ReviewComparator implements Comparator<Review> {
    @Override
    public int compare(Review first, Review second) {
        LocalDateTime firstValue = first.getCreatedDate();
        LocalDateTime secondValue = second.getCreatedDate();

        if (firstValue.isAfter(secondValue)) {
            return -1;
        } else if (secondValue.isBefore(firstValue)) {
            return 1;
        }
        return 0;
    }
}
