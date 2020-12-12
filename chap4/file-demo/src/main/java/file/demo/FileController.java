package file.demo;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.WriterException;

import file.demo.entity.CommonFile;
import file.demo.kit.QrKit;
import file.demo.kit.R;
import file.demo.service.CommonFileService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {

	@Autowired
	CommonFileService commonFileService;
	
	private static final String[] IMGTYPE = { ".jpg", ".icon", ".png",
			".jpeg", ".gif" };
	static String domain = "";

	/**
	 * 文件上传
	 * @param file
	 * @param request
	 * @param fId
	 * @param type
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("/img")
	public R<String> imgUpload(MultipartFile file,
			HttpServletRequest request, Long fId, Integer type)
			throws IllegalStateException, IOException {
		if (file == null) {
			return R.error("上传文件为空");
		}
		if (file.getSize() > 1024 * 1024 * 10) {
			return R.error("上传文件限定10M");
		}
		String fileName = file.getOriginalFilename();
		String fileNameNoSuffix = UUID.randomUUID().toString()
				.replaceAll("-", "");
		// 文件后缀
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		for (String string : IMGTYPE) {
			if (string.equals(suffix.toLowerCase())) {
				String realPath = request.getServletContext()
						.getRealPath("/imgupload");
				// 存储路径，在本地运行时可以从这里看到实际存储的路径
				File fileUploadPath = new File(realPath);
				if (!fileUploadPath.exists()) {
					fileUploadPath.mkdirs();
				}
				File targetFile = new File(fileUploadPath + "/"
						+ fileNameNoSuffix + suffix);
				file.transferTo(targetFile);
				CommonFile f = new CommonFile();
				f.setImgName(fileName);
				f.setImgPath(
						domain + "imgupload/" + targetFile.getName());
				f.setType(type);
				f.setFId(fId);
				commonFileService.save(f);
				return R.ok(
						domain + "imgupload/" + targetFile.getName());
			}
		}
		return R.error("文件类型限定为.jpg,.icon,.png,.jpeg,.gif");
	}

	/**
	 * 文件下载
	 * @param fileName
	 * @return
	 */
	@GetMapping("/downloadFile/{fileName:.*}")
	public ResponseEntity<org.springframework.core.io.Resource> downloadCacheFile(
			@PathVariable("fileName") String fileName) {
		try {
			// 下载文件所在的目录
			String savePath = this.getClass().getResource("/")
					.getPath();
			// 获取要下载的文件名称，并进行utf8编码
			fileName = URLDecoder.decode(fileName, "UTF-8");
			// 获取本地文件系统中的文件资源
			FileSystemResource resource = new FileSystemResource(
					savePath + fileName);

			// 解析文件的 mime 类型
			String mediaTypeStr = URLConnection.getFileNameMap()
					.getContentTypeFor(fileName);
			// 无法判断MIME类型时，作为流类型
			mediaTypeStr = (mediaTypeStr == null)
					? MediaType.APPLICATION_OCTET_STREAM_VALUE
					: mediaTypeStr;
			// 实例化MIME
			MediaType mediaType = MediaType
					.parseMediaType(mediaTypeStr);

			// 构造下载文件所需的响应头信息
			HttpHeaders headers = new HttpHeaders();
			// 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
			String filenames = new String(
					fileName.getBytes(StandardCharsets.UTF_8),
					StandardCharsets.ISO_8859_1);
			headers.setContentDispositionFormData("attachment",
					filenames);
			headers.setContentType(mediaType);

			// 返回资源流
			return ResponseEntity.ok().headers(headers)
					.contentLength(
							resource.getInputStream().available())
					.body(resource);
		} catch (IOException e) {
			log.error("文件访问失败", e);
			return null;
		}
	}

	@GetMapping("/qrCode")
	public void qrCode(HttpServletResponse response, String content)
			throws WriterException, IOException {
		QrKit.createZxing(response, 90, 90, 0, "L", "gif",
				"http://xxxxx.xxxxxx.com/api/lock/ftl/" + content);
	}

}
