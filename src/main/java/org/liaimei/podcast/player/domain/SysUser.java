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
@TableName("sys_user")
public class SysUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("`name`")
    private String name;

    @TableField("`password`")
    private String password;

    @TableField("eudic_token")
    private String eudicToken;

    @TableField("create_time")
    private String createTime;
}
