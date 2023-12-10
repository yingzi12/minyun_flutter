package com.xinshijie.gallery.common.vedio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VideoProcessing {

//    public void captureRandomFrame(String videoFilePath, String outputImagePath) throws IOException {
//        // 生成随机时间戳
//        String timestamp = generateRandomTimestamp();
//
//        // 构建 ffmpeg 命令
//        String ffmpegCmd = String.format("ffmpeg -i %s -ss %s -vframes 1 %s", videoFilePath, timestamp, outputImagePath);
//
//        // 执行命令
//        Runtime.getRuntime().exec(ffmpegCmd);
//    }

    public static long getVideoDuration(String videoFilePath) throws IOException, InterruptedException {
        String cmd = "ffprobe -v error -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 " + videoFilePath;
        Process process = Runtime.getRuntime().exec(cmd);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line = reader.readLine();
            if (line != null) {
                return (long) Double.parseDouble(line);
            }
        }
        process.waitFor();
        return 0;
    }

    private String generateRandomTimestamp(long videoDuration) {
        Random random = new Random();
        long randomSeconds = (long) (random.nextDouble() * videoDuration);

        long hours = TimeUnit.SECONDS.toHours(randomSeconds);
        long minutes = TimeUnit.SECONDS.toMinutes(randomSeconds) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.SECONDS.toSeconds(randomSeconds) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void main(String[] args) {
        VideoProcessing videoProcessing = new VideoProcessing();
        try {
            String videoPath = "/path/to/video.mp4";
            long duration = videoProcessing.getVideoDuration(videoPath);
            String timestamp = videoProcessing.generateRandomTimestamp(duration);
            System.out.println("Random Timestamp: " + timestamp);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

