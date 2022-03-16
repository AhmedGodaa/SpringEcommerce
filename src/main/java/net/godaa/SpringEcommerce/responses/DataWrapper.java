package net.godaa.SpringEcommerce.responses;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

public class DataWrapper<V> {


    @JsonIgnore
    private V objData;
    @JsonIgnore
    private String name;



    DataWrapper(V objData, String name) {
        this.objData = objData;
        this.name = name;
    }

    /**
     * @return the objData
     */
    public V getObjData() {
        return objData;
    }

    /**
     * @param objData the objData to set
     */
    public void setObjData(V objData) {
        this.objData = objData;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @JsonAnyGetter
    public Map<String, V> any() {
        if (StringUtils.isEmpty(name)) {
            name = "data";
        }
        return Collections.singletonMap(name, objData);
    }



}

