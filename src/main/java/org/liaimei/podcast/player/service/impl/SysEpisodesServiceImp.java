package org.liaimei.podcast.player.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liaimei.podcast.player.domain.SysEpisodes;
import org.liaimei.podcast.player.entity.EpisodesEntity;
import org.liaimei.podcast.player.mapper.SysEpisodesMapper;
import org.liaimei.podcast.player.service.SysEpisodesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lijiashu
 * @since 2023-08-29
 */
@Service
public class SysEpisodesServiceImp extends ServiceImpl<SysEpisodesMapper, SysEpisodes> implements SysEpisodesService {

    @Override
    public List<EpisodesEntity> getLastEpisode() {
        return baseMapper.getLastEpisode();
    }
}
