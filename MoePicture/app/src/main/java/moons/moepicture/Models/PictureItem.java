package moons.moepicture.Models;
/*
 * Created by jskyzero on 17/08/18.
 */

import org.w3c.dom.Element;

import java.io.Serializable;

import moons.moepicture.Services.Spider;

public class PictureItem implements Serializable {
    public enum  UrlType {preview_url, sample_url, jpeg_url};

    public String id;
    public String tags;
    public String preview_url;
    public String sample_url;
    public String jpeg_url;
    public String file_name;
    public String name;
    public UrlType utl_type;

    public PictureItem() {}

    public static PictureItem getItemFromDomElement(Element element){

        PictureItem newPictureItem = new PictureItem();

        newPictureItem.id = element.getAttribute("id");
        newPictureItem.tags = element.getAttribute("tags");
        newPictureItem.preview_url = element.getAttribute("preview_url");
        newPictureItem.sample_url = element.getAttribute("sample_url");
        newPictureItem.jpeg_url = element.getAttribute("jpeg_url");
        newPictureItem.name = Spider.getFileNameFromUrl(newPictureItem.jpeg_url);
        newPictureItem.file_name = newPictureItem.name;

        return newPictureItem;
    }

}
