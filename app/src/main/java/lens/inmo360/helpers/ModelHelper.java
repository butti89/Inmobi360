package lens.inmo360.helpers;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import lens.inmo360.managers.CouchBaseManager;

/**
 * Created by Nach on 5/5/2016.
 */
public class ModelHelper {

    public static void save(Database database, Object object) {
        ObjectMapper m = new ObjectMapper();
        Map<String, Object> props = m.convertValue(object, Map.class);
        String id = props.get("id").toString();
        CouchBaseManager cbManager = new CouchBaseManager();

        Document document;
        if (id == null) {
            document = database.createDocument();
        } else {
            document = database.getExistingDocument(id);
            if (document == null) {
                document = database.getDocument(id);
            } else {
                props.put("_rev", document.getProperty("_rev"));
            }
        }

        boolean res = cbManager.writeDocument(document,props);
    }

    public static <T> T modelForDocument(Document document, Class<T> aClass) {
        ObjectMapper m = new ObjectMapper();
        Map<String, Object> props = document.getProperties();

        return m.convertValue(props, aClass);
    }
}
