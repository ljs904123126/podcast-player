package org.liaimei.podcast.player;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("org.liaimei.podcast.player.mapper")
public class PodcastPlayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PodcastPlayerApplication.class, args);
	}

}
