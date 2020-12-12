package file.demo.service;

import file.demo.entity.CommonFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 泽朋
 * @since 2019-04-03
 */
public interface CommonFileService {

	/**
	 * 存储文件信息
	 * @param f
	 */
	void save(CommonFile f);

}
