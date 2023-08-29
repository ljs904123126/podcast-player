package org.liaimei.podcast.player.service;

import org.liaimei.podcast.player.domain.SysEpisodes;
import com.baomidou.mybatisplus.extension.service.IService;
import org.liaimei.podcast.player.entity.EpisodesEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lijiashu
 * @since 2023-08-29
 */
public interface SysEpisodesService extends IService<SysEpisodes> {
    List<EpisodesEntity> getLastEpisode();
}
