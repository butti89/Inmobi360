package lens.inmo360.daos;

import com.couchbase.lite.Document;

import java.util.ArrayList;

import lens.inmo360.helpers.ModelHelper;
import lens.inmo360.managers.CouchBaseManager;
import lens.inmo360.model.Property;

/**
 * Created by Nach on 5/7/2016.
 */
public class PropertiesDAO {
    private static CouchBaseManager cbManager = new CouchBaseManager();

    public static ArrayList<Property> GetAll(){

        ArrayList<Property> res = new ArrayList<>();
        ArrayList<Document> docs = cbManager.getAllDocs();

        for (int i = 0; i < docs.size(); i++) {
            res.add(ModelHelper.modelForDocument(docs.get(i),Property.class));
        }

        return res;
    }

    public static Property getById(String id){
        Document doc = cbManager.getDocument(id);
        Property prop= ModelHelper.modelForDocument(doc, Property.class);
        return prop;
    }

    public static Property getById(Integer id){
        return getById(id.toString());
    }
}
