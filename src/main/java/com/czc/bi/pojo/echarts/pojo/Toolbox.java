package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Toolbox {
	private Boolean show;
	private Feature feature;

	public Boolean getShow() {
		return this.show;
	}

	public Toolbox setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public Feature getFeature() {
		return this.feature;
	}
	
	public Feature feature() {
		// 判断空值
		if (feature == null) {
			this.feature = new Feature();
		}
		return this.feature;
	}
	
	// Toolbox的内部类Feature
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public class Feature {
		private Mark mark;
		private DataZoom dataZoom;
		private DataView dataView;
		private MagicType magicType;
		private Restore restore;
		private SaveAsImage saveAsImage;

		public Mark getMark() {
			return this.mark;
		}
		
		public Mark mark() {
			if (this.mark == null) {
				this.mark = new Mark();
			}
			return this.mark;
		}

		public DataView getDataView() {
			return dataView;
		}
		
		public DataView dataView() {
			if (this.dataView == null) {
				this.dataView = new DataView();
			}
			return this.dataView;
		}

		public MagicType getMagicType() {
			return magicType;
		}
		
		public MagicType magicType() {
			if (this.magicType == null) {
				this.magicType = new MagicType();
			}
			return this.magicType;
		}
		
		public Restore getRestore() {
			return restore;
		}
		
		public Restore restore() {
			if (this.restore == null) {
				this.restore = new Restore();
			}
			return this.restore;
		}

		public SaveAsImage getSaveAsImage() {
			return saveAsImage;
		}
		
		public SaveAsImage saveAsImage() {
			if (this.saveAsImage == null) {
				this.saveAsImage = new SaveAsImage();
			}
			return this.saveAsImage;
		}

		public DataZoom dataZoom() {
			if(this.dataZoom == null){
				this.dataZoom = new DataZoom();
			}
			return this.dataZoom;
		}
		
		public DataZoom getDataZoom() {
			return dataZoom;
		}

		public void setDataZoom(DataZoom dataZoom) {
			this.dataZoom = dataZoom;
		}

		// Mark对象
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public class Mark {
			private Boolean show;

			public Boolean getShow() {
				return show;
			}

			public void setShow(Boolean show) {
				this.show = show;
			}

		}

		// SaveAsImage对象
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public class SaveAsImage {
			private Boolean show;

			private String name;

			public String getName() {
				return name;
			}

			public SaveAsImage setName(String name) {
				this.name = name;
				return this;
			}

			public Boolean getShow() {
				return show;
			}

			public SaveAsImage setShow(Boolean show) {
				this.show = show;
				return this;
			}

		}

		// Restore对象
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public class Restore {
			private Boolean show;

			public Boolean getShow() {
				return show;
			}

			public void setShow(Boolean show) {
				this.show = show;
			}

		}
		
		// DataZoom对象
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public class DataZoom{
			private Boolean show;

			public Boolean getShow() {
				return show;
			}

			public void setShow(Boolean show) {
				this.show = show;
			}
 
			
		}
		
		

		// DataView对象
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public class DataView {
			private Boolean show;
			private Boolean readOnly;
			
			public Boolean getShow() {
				return show;
			}

			public DataView setShow(Boolean show) {
				this.show = show;
				return this;
			}

			public Boolean getReadOnly() {
				return readOnly;
			}

			public DataView setReadOnly(Boolean readOnly) {
				this.readOnly = readOnly;
				return this;
			}
		}

		// MagicType对象
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public class MagicType {
			private Boolean show;
			private List<String> type;

			public Boolean getShow() {
				return show;
			}

			public MagicType setShow(Boolean show) {
				this.show = show;
				return this;
			}

			public List<String> getType() {
				return type;
			}

			public MagicType setType(List<String> type) {
				this.type = type;
				return this;
			}
			
			public MagicType addType(String type) {
				if(this.type == null){
					this.type = new ArrayList<String>();
				}
				this.type.add(type);
				return this;
			}
		}
	}
}
