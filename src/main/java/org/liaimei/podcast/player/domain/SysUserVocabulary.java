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
@TableName("sys_user_vocabulary")
public class SysUserVocabulary {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("episode_id")
    private Integer episodeId;

    @TableField("vocabulary")
    private String vocabulary;

    @TableField("segment")
    private String segment;

    @TableField("create_time")
    private String createTime;
}
