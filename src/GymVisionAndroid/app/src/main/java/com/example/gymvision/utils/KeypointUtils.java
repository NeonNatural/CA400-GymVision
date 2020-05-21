package com.example.gymvision.utils;

import java.util.HashMap;
import java.util.Map;

import ai.fritz.vision.poseestimation.Keypoint;

public class KeypointUtils {

    public static Map<String, Keypoint> keypointsToMap(
            Keypoint[] keypoints) {
        HashMap<String, Keypoint> keypointMap = new HashMap<>();

        for (Keypoint keypoint : keypoints) {
            keypointMap.put(keypoint.getName(), keypoint);
        }

        return keypointMap;
    }

}
