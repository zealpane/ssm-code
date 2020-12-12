package biz.common.facade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biz.demo.pojo.BaiduAudio;

@RestController
@RequestMapping("/common/commonFile")
public class CommonFileController {
	
	/**
	 * 返回语音识别的在线服务token
	 * @return
	 */
	@GetMapping("baiduAudioAccessToken")
	public R getBaiduAudio() {
		String baiduAuString = BaiduAudio.getAccessToken();
		return R.ok(baiduAuString);
	}
}