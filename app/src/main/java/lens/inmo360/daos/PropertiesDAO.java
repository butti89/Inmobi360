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
    private static CouchBaseManager cbManager;

    public static ArrayList<Property> GetAll(){

        ArrayList<Property> res = new ArrayList<>();
        ArrayList<Document> docs = cbManager.GetAllDocs();

        for (int i = 0; i < docs.size(); i++) {
            res.add(ModelHelper.modelForDocument(docs.get(i),Property.class));
        }

        return res;
    }
}
