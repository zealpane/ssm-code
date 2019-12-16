package lombok.demo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class MappedStatement {

	private String sourceId;
	
	private String namespace;
	private String sql;
	private String resultType;
}