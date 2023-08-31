package org.liaimei.podcast.player.utils;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.liaimei.podcast.player.entity.SrtEntity;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SrtUtils {

    // read srt to SrtEntity
    public static List<SrtEntity> readSrt(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<SrtEntity> srtEntities = new ArrayList<>();
        try {
            Integer cnum = null;
            Integer start = null;
            Integer end = null;
            String content = null;

            List<String> lines = FileUtils.readLines(file, "UTF-8");
            for (int i = 0; i < lines.size(); i += 4) {
                cnum = Integer.valueOf(strip(lines.get(i)));
                String s = strip(lines.get(i + 1));
                content = strip(lines.get(i + 2));
                String[] split = s.split("-->");
                if (split.length <= 1) {
                    continue;
                }
                start = parseTimeStringToMilliseconds(strip(split[0]));
                end = parseTimeStringToMilliseconds(strip(split[1]));
                String[] words = StringUtils.split(content);
                srtEntities.add(new SrtEntity(cnum, start, end, Arrays.asList(words)));
            }

            if (srtEntities.size() >= 2) {
                if (Objects.equals(srtEntities.get(0).getNumber(), srtEntities.get(1).getNumber())) {
                    List<SrtEntity> mergedSrtEntities = new ArrayList<>();
                    for (int i = 0; i < srtEntities.size(); i += 2) {
                        SrtEntity srtEntity = srtEntities.get(i);
                        srtEntity.getContents().addAll(srtEntities.get(i + 1).getContents());
                        mergedSrtEntities.add(srtEntity);
                    }
                    srtEntities = mergedSrtEntities;
                }
            }
            System.out.println(JSON.toJSONString(srtEntities));
            return srtEntities;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String strip(String str) {

        str = StringUtils.strip(str);

        if (str.startsWith("\uFEFF")) {
            str = str.replace("\uFEFF", "");
        } else if (str.endsWith("\uFEFF")) {
            str = str.replace("\uFEFF", "");
        }
        return str;
    }


    public static int parseTimeStringToMilliseconds(String timeString) {
        String[] parts = timeString.split(":|,");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        int milliseconds = Integer.parseInt(parts[3]);

        int totalMilliseconds = (hours * 3600 + minutes * 60 + seconds) * 1000 + milliseconds;
        return totalMilliseconds;
    }

    public static void main(String[] args) {
        readSrt("D:\\work\\java\\podcast_player\\src\\main\\resources\\static\\ttt.srt");

//        String timeString = "00:01:02,000";
//        int totalMilliseconds = parseTimeStringToMilliseconds(timeString);
//        int totalSeconds = totalMilliseconds / 1000;
//
//        System.out.println("Total seconds: " + totalSeconds);
    }

}
