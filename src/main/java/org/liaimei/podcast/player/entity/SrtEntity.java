package org.liaimei.podcast.player.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SrtEntity {

    private Integer number;
    private Integer start;
    private Integer end;
    private List<List<String>> contents;

    public SrtEntity() {
    }

    public SrtEntity(Integer number, Integer start, Integer end, List<String> contents) {
        this.contents = new ArrayList<>();
        this.contents.add(contents);
        this.number = number;
        this.start = start;
        this.end = end;
    }


}
