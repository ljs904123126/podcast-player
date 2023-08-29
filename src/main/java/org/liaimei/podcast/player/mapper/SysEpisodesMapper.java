package org.liaimei.podcast.player.mapper;

import org.liaimei.podcast.player.domain.SysEpisodes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.liaimei.podcast.player.entity.EpisodesEntity;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lijiashu
 * @since 2023-08-29
 */
@Mapper
public interface SysEpisodesMapper extends BaseMapper<SysEpisodes> {


    List<EpisodesEntity> getLastEpisode();
}
