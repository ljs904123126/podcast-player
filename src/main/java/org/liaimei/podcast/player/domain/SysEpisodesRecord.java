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
@TableName("sys_episodes_record")
public class SysEpisodesRecord {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("episode_id")
    private Integer episodeId;

    @TableField("user_id")
    private Integer userId;

    @TableField("finished")
    private Integer finished;

    @TableField("duration")
    private Integer duration;

    @TableField("create_time")
    private String createTime;

    @TableField("last_update_time")
    private String lastUpdateTime;
}
