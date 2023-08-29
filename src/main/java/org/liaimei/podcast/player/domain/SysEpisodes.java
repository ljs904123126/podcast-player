package org.liaimei.podcast.player.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lijiashu
 * @since 2023-08-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_episodes")
public class SysEpisodes {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("podcast_id")
    private Integer podcastId;

    @TableField("duration")
    private Integer duration;

    @TableField("`name`")
    private String name;

    @TableField("audio_file")
    private String audioFile;

    @TableField("srt_file")
    private String srtFile;

    @TableField("describe_info")
    private String describeInfo;

    @TableField("create_time")
    private String createTime;
}
