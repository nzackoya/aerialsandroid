package com.aerials.domain;

import android.content.Context;
import com.aerials.network.RequestResponseBean;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "wave")
@Root(name="rss", strict=false)
public class Wave extends RequestResponseBean implements Serializable{

    public Wave() {}
    public Wave(String title) {
        this.title = title;
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "title")
    @Element
    private String title;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "url")
    private String url;

    @DatabaseField(canBeNull = false, foreign = true)
    private Category category;

    @ElementList(name = "item", inline = true)
    private Collection<WaveItem> item = new ArrayList<WaveItem>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url, Context context) {
        this.url = url;
//        Request.getRSS(this, new AerialsRequestListener<Wave>(context) {
//            @Override
//            public void gotResponse(Wave wave) {
//                setName(wave.getName());
//            }
//        });
    }

    public Category getCategory() {
        return category;
    }

    public Collection<WaveItem> getItem() {
        return item;
    }

    public void setItem(Collection<WaveItem> item) {
        this.item = item;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MultiValueMap<String, String> getParameters() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        return parameters;
    }
}
