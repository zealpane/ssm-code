package file.demo.service.impl;

import org.springframework.stereotype.Service;

import file.demo.entity.CommonFile;
import file.demo.service.CommonFileService;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 泽朋
 * @since 2019-04-03
 */
@Slf4j
@Service
public class CommonFileServiceImpl implements CommonFileService {

	@Override
	public void save(CommonFile f) {
		log.info("这里的文件信息可以用来持久化存储");
		log.info(f.toString());
	}

}
