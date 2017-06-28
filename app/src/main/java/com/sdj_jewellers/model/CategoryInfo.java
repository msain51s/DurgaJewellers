package com.sdj_jewellers.model;

import java.io.Serializable;

/**
 * Created by bhanwar on 18/06/2017.
 */

public class CategoryInfo implements Serializable{
    public int catID;
    public String postTitle;
    public String postDate;
    public String postContent;
    public String postExcerpt;
    public String postStatus;
    public String postName;

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int product_quantity=-1;

    public String getPostModifiedDate() {
        return postModifiedDate;
    }

    public void setPostModifiedDate(String postModifiedDate) {
        this.postModifiedDate = postModifiedDate;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTermTaxonomyID() {
        return termTaxonomyID;
    }

    public void setTermTaxonomyID(String termTaxonomyID) {
        this.termTaxonomyID = termTaxonomyID;
    }

    public String postModifiedDate;
    public String postType;
    public String image;
    public String imagePath;
    public String parent;
    public String termTaxonomyID;

}
