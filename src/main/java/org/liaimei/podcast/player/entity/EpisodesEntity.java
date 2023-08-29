package org.liaimei.podcast.player.entity;

import lombok.Data;

@Data
public class EpisodesEntity {
    private int podcastId;
    private int episodeId;
    private String podcastName;
    private String episodeName;
    private String updateTime;
}
