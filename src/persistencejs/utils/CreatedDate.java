package persistencejs.utils;

import java.util.Date;

import org.slim3.datastore.AttributeListener;

public class CreatedDate implements AttributeListener<Date> {

    public Date prePut(Date value) {
        if (value != null) {
            return value;
        }
        return new Date();
    }  
}
