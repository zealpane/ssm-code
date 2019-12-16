package lombok.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.Cleanup;

public class CleanupDemo {
	public static void main(String[] args) throws IOException {
		@Cleanup
		InputStream in = new FileInputStream("d://a.txt");
		@Cleanup
		OutputStream out = new FileOutputStream("d://b.txt");
		byte[] b = new byte[1000];
		while (true) {
			int r = in.read(b);
			if (r == -1)
				break;
			out.write(b, 0, r);
		}
	}
}

class NoCleanupDemo {
	public static void main(String[] args) 
			throws IOException {
		InputStream in = 
				new FileInputStream("d://a.txt");
		try {
			OutputStream out = 
					new FileOutputStream("d://a.txt");
			try {
				byte[] b = new byte[10000];
				while (true) {
					int r = in.read(b);
					if (r == -1)
						break;
					out.write(b, 0, r);
				}
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
}
