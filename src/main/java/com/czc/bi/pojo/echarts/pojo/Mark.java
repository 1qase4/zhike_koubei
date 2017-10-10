package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mark {
	private List<Data> data;

	public List<Data> getData() {
		return data;
	}

	public List<Data> data() {
		if (this.data == null) {
			this.data = new ArrayList<Data>();
		}
		return data;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public class Data {
		private String type;
		private String name;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
