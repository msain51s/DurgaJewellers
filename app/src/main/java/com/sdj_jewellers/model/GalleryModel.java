package com.sdj_jewellers.model;

import java.util.ArrayList;

/**
 * Created by bhanwar on 13/06/2017.
 */

public class GalleryModel {
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String imageUrl;
    public String termId;
    public String name;
    public String slug;
    public String taxonomy;

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String productCount;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getSubCatCount() {
        return subCatCount;
    }

    public void setSubCatCount(int subCatCount) {
        this.subCatCount = subCatCount;
    }

    public ArrayList<String> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(ArrayList<String> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public String parent;
    public int subCatCount;
    public ArrayList<String> subCategoryList;
}
