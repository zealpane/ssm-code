package dps.bean.resource;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.FileSystemResource;

//使用FileSystemResource访问资源
public class FsResourceTest 
{
	public static void main(String[] args) throws Exception
	{
		//默认从文件系统的当前路径加载student.xml资源
		FileSystemResource fr = new FileSystemResource("student2.xml");
		//获取该资源的简单信息
		System.out.println(fr.getFilename());
		System.out.println(fr.getDescription());
		//创建Dom4j的解析器
		SAXReader reader = new SAXReader();
		Document doc = reader.read(fr.getFile());
		//获取根元素
		Element el = doc.getRootElement();
		List l = el.elements();
		//遍历根元素的全部子元素
		for (Iterator it1 = l.iterator();it1.hasNext() ; )
		{
			//获取节点
			Element student = (Element)it1.next();
			List ll = student.elements();
			//遍历每个节点的全部子节点
			for (Iterator it2 = ll.iterator();it2.hasNext() ; )
			{
				Element eee = (Element)it2.next();
				System.out.println(eee.getText());
			}
		}
	}
}