package com.spring.insta.util;

import com.spring.insta.dto.PostResponseDto;

import java.util.Comparator;

public class PostDtoComparator implements Comparator<PostResponseDto> {
    @Override
    public int compare(PostResponseDto first, PostResponseDto second) {
        int firstValue = first.getLikeCount();
        int secondValue = second.getLikeCount();

        if (firstValue > secondValue) {
            return -1;
        } else if (secondValue < firstValue) {
            return 1;
        }
        return 0;
    }
}
