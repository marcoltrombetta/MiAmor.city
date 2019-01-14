package com.miamor.Obj;

import java.sql.Date;

public class Category {
		private int Id;
	   private String Name;
       private String Description;
       private int CategoryTemplateId;
       private String MetaKeywords;
       private String MetaDescription;
       private String MetaTitle;
       private int PictureId;
       private int PageSize;
       private Boolean AllowCustomersToSelectPageSize;
       private String PageSizeOptions;
       private String PriceRanges;
       private Boolean ShowOnHomePage;
       private Boolean IncludeInMenu;
       private Boolean HasDiscountsApplied;
       private Boolean Published;
       private Boolean Deleted;
       private String  CssClass;
       private int DisplayOrder;
       private String CreatedOnUtc;
       private String UpdatedOnUtc;
       private int ParentCategoryId; 
       private Category ParentCategory;
       
	public Category(String name, String description, int categoryTemplateId, String metaKeywords,
			String metaDescription, String metaTitle, int pictureId, int pageSize,
			Boolean allowCustomersToSelectPageSize, String pageSizeOptions, String priceRanges, Boolean showOnHomePage,
			Boolean includeInMenu, Boolean hasDiscountsApplied, Boolean published, Boolean deleted, String cssClass,
			int displayOrder, String createdOnUtc, String updatedOnUtc, int parentCategoryId, Category parentCategory) {
		super();
		Name = name;
		Description = description;
		CategoryTemplateId = categoryTemplateId;
		MetaKeywords = metaKeywords;
		MetaDescription = metaDescription;
		MetaTitle = metaTitle;
		PictureId = pictureId;
		PageSize = pageSize;
		AllowCustomersToSelectPageSize = allowCustomersToSelectPageSize;
		PageSizeOptions = pageSizeOptions;
		PriceRanges = priceRanges;
		ShowOnHomePage = showOnHomePage;
		IncludeInMenu = includeInMenu;
		HasDiscountsApplied = hasDiscountsApplied;
		Published = published;
		Deleted = deleted;
		CssClass = cssClass;
		DisplayOrder = displayOrder;
		CreatedOnUtc = createdOnUtc;
		UpdatedOnUtc = updatedOnUtc;
		ParentCategoryId = parentCategoryId;
		ParentCategory = parentCategory;
	}

	public Category() {
		super();
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getCategoryTemplateId() {
		return CategoryTemplateId;
	}
	public void setCategoryTemplateId(int categoryTemplateId) {
		CategoryTemplateId = categoryTemplateId;
	}
	public String getMetaKeywords() {
		return MetaKeywords;
	}
	public void setMetaKeywords(String metaKeywords) {
		MetaKeywords = metaKeywords;
	}
	public String getMetaDescription() {
		return MetaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		MetaDescription = metaDescription;
	}
	public String getMetaTitle() {
		return MetaTitle;
	}
	public void setMetaTitle(String metaTitle) {
		MetaTitle = metaTitle;
	}
	public int getPictureId() {
		return PictureId;
	}
	public void setPictureId(int pictureId) {
		PictureId = pictureId;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	public Boolean getAllowCustomersToSelectPageSize() {
		return AllowCustomersToSelectPageSize;
	}
	public void setAllowCustomersToSelectPageSize(Boolean allowCustomersToSelectPageSize) {
		AllowCustomersToSelectPageSize = allowCustomersToSelectPageSize;
	}
	public String getPageSizeOptions() {
		return PageSizeOptions;
	}
	public void setPageSizeOptions(String pageSizeOptions) {
		PageSizeOptions = pageSizeOptions;
	}
	public String getPriceRanges() {
		return PriceRanges;
	}
	public void setPriceRanges(String priceRanges) {
		PriceRanges = priceRanges;
	}
	public Boolean getShowOnHomePage() {
		return ShowOnHomePage;
	}
	public void setShowOnHomePage(Boolean showOnHomePage) {
		ShowOnHomePage = showOnHomePage;
	}
	public Boolean getIncludeInMenu() {
		return IncludeInMenu;
	}
	public void setIncludeInMenu(Boolean includeInMenu) {
		IncludeInMenu = includeInMenu;
	}
	public Boolean getHasDiscountsApplied() {
		return HasDiscountsApplied;
	}
	public void setHasDiscountsApplied(Boolean hasDiscountsApplied) {
		HasDiscountsApplied = hasDiscountsApplied;
	}
	public Boolean getPublished() {
		return Published;
	}
	public void setPublished(Boolean published) {
		Published = published;
	}
	public Boolean getDeleted() {
		return Deleted;
	}
	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
	}
	public String getCssClass() {
		return CssClass;
	}
	public void setCssClass(String cssClass) {
		CssClass = cssClass;
	}
	public int getDisplayOrder() {
		return DisplayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		DisplayOrder = displayOrder;
	}
	public String getCreatedOnUtc() {
		return CreatedOnUtc;
	}
	public void setCreatedOnUtc(String createdOnUtc) {
		CreatedOnUtc = createdOnUtc;
	}
	public String getUpdatedOnUtc() {
		return UpdatedOnUtc;
	}
	public void setUpdatedOnUtc(String updatedOnUtc) {
		UpdatedOnUtc = updatedOnUtc;
	}
	public int getParentCategoryId() {
		return ParentCategoryId;
	}
	public void setParentCategoryId(int parentCategoryId) {
		ParentCategoryId = parentCategoryId;
	}
	public Category getParentCategory() {
		return ParentCategory;
	}
	public void setParentCategory(Category parentCategory) {
		ParentCategory = parentCategory;
	}

}
