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
@TableName("sys_podcasts")
public class SysPodcasts {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("`name`")
    private String name;

    @TableField("rss")
    private String rss;

    @TableField("create_time")
    private String createTime;

    @TableField("last_update_time")
    private String lastUpdateTime;
}
